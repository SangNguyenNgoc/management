package com.example.markethibernate.bll.services;

import com.example.markethibernate.dal.dao.PersonDao;
import com.example.markethibernate.dal.entities.PersonEntity;

public class PersonService {

    private static class PersonServiceHolder {
        private static final PersonService INSTANCE = new PersonService();
    }

    private PersonService() {
    }

    public static PersonService getInstance() {
        return PersonServiceHolder.INSTANCE;
    }

    public PersonEntity addPerson(PersonEntity person) {
        if (person == null) {
            throw new IllegalArgumentException("Không được để trống thông tin");
        } else {
            if (person.getName() == null || person.getName().isEmpty()) {
                throw new IllegalArgumentException("Tên không được để trống");
            }
            if (person.getDepartment() == null || person.getDepartment().isEmpty()) {
                throw new IllegalArgumentException("Department không được để trống");
            }
            if (person.getProfession() == null || person.getProfession().isEmpty()) {
                throw new IllegalArgumentException("Profession không được để trống");
            }
            if (person.getPhoneNumber() == null || person.getPhoneNumber().isEmpty()) {
                throw new IllegalArgumentException("Số điện thoại không được để trống");
            }
        }
        return PersonDao.getInstance().addPerson(person);
    }

    public PersonEntity updatePerson(PersonEntity person) {
        if (person == null) {
            throw new IllegalArgumentException("Không được để trống thông tin");
        } else {
            if (person.getName() == null || person.getName().isEmpty()) {
                throw new IllegalArgumentException("Tên không được để trống");
            }
            if (person.getDepartment() == null || person.getDepartment().isEmpty()) {
                throw new IllegalArgumentException("Department không được để trống");
            }
            if (person.getProfession() == null || person.getProfession().isEmpty()) {
                throw new IllegalArgumentException("Profession không được để trống");
            }
            if (person.getPhoneNumber() == null || person.getPhoneNumber().isEmpty()) {
                throw new IllegalArgumentException("Số điện thoại không được để trống");
            }
        }
        return PersonDao.getInstance().updatePerson(person);
    }
    public PersonEntity deletePersonById(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("ID không được để trống");
        }
        return PersonDao.getInstance().deletePersonById(id);
    }

}
