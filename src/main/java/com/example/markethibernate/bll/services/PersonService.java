package com.example.markethibernate.bll.services;

import com.example.markethibernate.bll.dtos.PersonValidator;
import com.example.markethibernate.bll.mappers.PersonMapper;
import com.example.markethibernate.dal.dao.PersonDao;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.utils.AppUtil;
import com.example.markethibernate.utils.ValidatorUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public PersonEntity addPerson(String name, String email, String password,
                                  String department, String profession, String phoneNumber
    ) {
        PersonValidator personValidator = PersonValidator.builder()
                .name(name)
                .email(email)
                .password(password)
                .department(department)
                .profession(profession)
                .phoneNumber(phoneNumber)
                .build();
        if (!ValidatorUtil.validateObject(personValidator).isBlank()) {
            return null;
        }
        PersonEntity person = personMapper.toEntity(personValidator);
        person.setPassword(createPassword(password));
        person.setStatus(true);
        return PersonDao.getInstance().addPerson(person);
    }

    public PersonEntity updatePerson(String idString, String name, String email, String password,
                                     String department, String profession, String phoneNumber
    ) {
        PersonEntity person = getById(idString);
        if (person == null) {
            return null;
        }
        PersonValidator personValidator = PersonValidator.builder()
                .name(name)
                .email(email)
                .password(password)
                .department(department)
                .profession(profession)
                .phoneNumber(phoneNumber)
                .build();
        if (!ValidatorUtil.validateObject(personValidator).isBlank()) {
            return null;
        }
        if(!person.getEmail().equals(email)) {
            if(!checkEmailTaken(email).isBlank()) {
                return null;
            }
        }
        PersonEntity personUpdate = personMapper.partialUpdate(personValidator, person);
        personUpdate.setPassword(createPassword(password));
        return PersonDao.getInstance().updatePerson(personUpdate);
    }

    public PersonEntity deletePersonById(String idString) {
        PersonEntity person = getById(idString);
        if (person == null) {
            return null;
        }
        return PersonDao.getInstance().deletePersonById(AppUtil.parseId(idString));
    }

    public String checkName(String name) {
        return ValidatorUtil.validateField(
                PersonValidator.builder()
                        .name(name)
                        .build(),
                "name"
        );
    }

    public String checkEmail(String email) {
        return ValidatorUtil.validateField(
                PersonValidator.builder()
                        .email(email)
                        .build(),
                "email");
    }

    public String checkEmailTaken(String email) {
        if(PersonDao.getInstance().findByEmail(email) != null) {
            return "Email đã được sử dụng";
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

    public String checkPassword(String password) {
        return ValidatorUtil.validateField(
                PersonValidator.builder()
                        .password(password)
                        .build(),
                "password");
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

    private static class PersonServiceHolder {
        private static final PersonService INSTANCE = new PersonService();
    }

}
