package com.example.markethibernate.bll.services;

import com.example.markethibernate.bll.dtos.EmailChecked;
import com.example.markethibernate.bll.dtos.PersonValidator;
import com.example.markethibernate.bll.mappers.PersonMapper;
import com.example.markethibernate.dal.dao.PersonDao;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.utils.AppUtil;
import com.example.markethibernate.utils.ValidatorUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonService {

    private final PersonMapper personMapper;

    private PersonService() {
        this.personMapper = Mappers.getMapper(PersonMapper.class);
    }

    public static PersonService getInstance() {
        return PersonServiceHolder.INSTANCE;
    }

    public String createPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public Boolean comparePassword(String rawPass, String encoderPass) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPass, encoderPass);
    }

    public List<PersonEntity> getAll() {
        return PersonDao.getInstance().findAll();
    }

    public PersonEntity getById(String idString) {
        Long id = AppUtil.parseId(idString);
        if (id == null) {
            return null;
        }
        return PersonDao.getInstance().findById(id);
    }

    public List<PersonEntity> getByYear(String year) {
        Long y = AppUtil.parseId(year);
        if (y == null) {
            return null;
        }
        return PersonDao.getInstance().findByYear(y);
    }

    public PersonEntity addPerson(String id, String name, String email, String password,
                                  String department, String profession, String phoneNumber
    ) {
        PersonValidator personValidator = PersonValidator.builder()
                .id(id)
                .name(name)
                .email(email)
                .department(department)
                .profession(profession)
                .phoneNumber(phoneNumber)
                .build();
        if (!ValidatorUtil.validateObject(personValidator).isBlank()) {
            return null;
        }
        if(!checkEmailTaken(email, id).isBlank()) {
            return null;
        }
        PersonEntity person = personMapper.toEntity(personValidator);
        person.setPassword(createPassword(password));
        person.setStatus(true);
        return PersonDao.getInstance().addPerson(person);
    }

    public PersonEntity updatePerson(String idString, String name, String email,
                                     String department, String profession, String phoneNumber
    ) {
        PersonEntity person = getById(idString);
        if (person == null) {
            return null;
        }
        PersonValidator personValidator = PersonValidator.builder()
                .id(idString)
                .name(name)
                .email(email)
                .department(department)
                .profession(profession)
                .phoneNumber(phoneNumber)
                .build();
        if (!ValidatorUtil.validateObject(personValidator).isBlank()) {
            return null;
        }
        if(!person.getEmail().equals(email)) {
            if(!checkEmailTaken(email, idString).isBlank()) {
                return null;
            }
        }
        PersonEntity personUpdate = personMapper.partialUpdate(personValidator, person);
        return PersonDao.getInstance().updatePerson(personUpdate);
    }

    public PersonEntity deletePersonById(String idString) {
        PersonEntity person = getById(idString);
        if (person == null) {
            return null;
        }
        return PersonDao.getInstance().deletePersonById(AppUtil.parseId(idString));
    }

    public int deletePersonByYear(String year) {
        Long y = AppUtil.parseId(year);
        if (y == null) {
            return 0;
        }
        return PersonDao.getInstance().deletePersonByYear(y);
    }

    public String checkId(String id) {
        String validate = ValidatorUtil.validateField(
                PersonValidator.builder()
                        .id(id)
                        .build(),
                "id"
        );
        if(validate.isBlank()) {
            validate += checkIdTaken(id);
        }
        return validate;
    }

    public String checkName(String name) {
        return ValidatorUtil.validateField(
                PersonValidator.builder()
                        .name(name)
                        .build(),
                "name"
        );
    }

    public String checkEmail(EmailChecked checked) {
        String validate = ValidatorUtil.validateField(
                PersonValidator.builder()
                        .email(checked.getEmail())
                        .build(),
                "email");
        if(validate.isBlank()) {
            validate += checkEmailTaken(checked.getEmail(), checked.getId());
        }
        return validate;
    }

    public String checkEmailTaken(String email, String id) {
        PersonEntity person = PersonDao.getInstance().findByEmail(email);
        if(person != null && !person.getId().toString().equals(id)) {
            return "Email đã được sử dụng";
        }
        return "";
    }

    public String checkIdTaken(String id) {
        if(getById(id) != null) {
            return "Mã thành viên trùng khớp";
        }
        return "";
    }

    public String checkPhoneNumber(String phoneNumber) {
        return ValidatorUtil.validateField(
                PersonValidator.builder()
                        .phoneNumber(phoneNumber)
                        .build(),
                "phoneNumber");
    }

    public String checkDepartment(String department) {
        return ValidatorUtil.validateField(
                PersonValidator.builder()
                        .department(department)
                        .build(),
                "department");
    }

    public String checkProfession(String profession) {
        return ValidatorUtil.validateField(
                PersonValidator.builder()
                        .profession(profession)
                        .build(),
                "profession");
    }

    public String saveFromExcel(File selectedFile) {
        try (Workbook workbook = WorkbookFactory.create(selectedFile)) {
            Sheet sheet = workbook.getSheetAt(0);
            StringBuilder result = new StringBuilder();
            for (Row row : sheet) {
                if(row.getCell(0) == null) {
                    break;
                }
                if (row.getRowNum() > 0) {
                    PersonEntity person = addPerson(
                            String.valueOf((long) row.getCell(0).getNumericCellValue()),
                            row.getCell(1).getStringCellValue(),
                            row.getCell(6).getStringCellValue(),
                            String.valueOf(row.getCell(5).getNumericCellValue()),
                            row.getCell(2).getStringCellValue(),
                            row.getCell(3).getStringCellValue(),
                            row.getCell(4).getStringCellValue()
                    );
                    if (person == null) {
                        result.append(row.getRowNum()).append(", ");
                    }
                }
            }
            return String.valueOf(result);
        } catch (IOException e) {
            return null;
        }
    }

    private static class PersonServiceHolder {
        private static final PersonService INSTANCE = new PersonService();
    }

}
