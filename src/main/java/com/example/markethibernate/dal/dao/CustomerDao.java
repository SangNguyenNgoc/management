package com.example.markethibernate.dal.dao;

import com.example.markethibernate.utils.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.logging.Logger;

public class CustomerDao {

    private final SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(CustomerDao.class.getName());

    private static class CustomerDaoHolder {
        private static final CustomerDao INSTANCE = new CustomerDao();
    }

    private CustomerDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static CustomerDao getInstance() {
        return CustomerDao.CustomerDaoHolder.INSTANCE;
    }
}
