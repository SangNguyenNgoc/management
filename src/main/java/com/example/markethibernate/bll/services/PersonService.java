package com.example.markethibernate.bll.services;

public class PersonService {

    private static class PersonServiceHolder {
        private static final PersonService INSTANCE = new PersonService();
    }

    private PersonService() {
    }

    public static PersonService getInstance() {
        return PersonServiceHolder.INSTANCE;
    }
}
