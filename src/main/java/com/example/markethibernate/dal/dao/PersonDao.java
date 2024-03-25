package com.example.markethibernate.dal.dao;

import com.example.markethibernate.utils.HibernateUtil;
import org.hibernate.SessionFactory;

public class PersonDao {

    private final SessionFactory sessionFactory;

    private static class PersonDaoHolder {
        private static final PersonDao INSTANCE = new PersonDao();
    }

    private PersonDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static PersonDao getInstance() {
        return PersonDao.PersonDaoHolder.INSTANCE;
    }
}
