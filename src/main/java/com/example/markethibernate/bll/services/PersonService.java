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
            return null;
        }
        return PersonDao.getInstance().addPerson(person);
    }
    public PersonEntity updatePerson(PersonEntity person) {
        if (person == null) {
            return null;
        }
        return PersonDao.getInstance().updatePerson(person);
    }
    public PersonEntity deletePersonById(int id) {
        if (id == 0) {
            return null;
        }
        return PersonDao.getInstance().deletePersonById(id);
    }

}
