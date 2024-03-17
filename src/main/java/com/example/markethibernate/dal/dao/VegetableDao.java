package com.example.markethibernate.dal.dao;

import com.example.markethibernate.utils.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.logging.Logger;

public class VegetableDao {

    private final SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(VegetableDao.class.getName());

    private static class VegetableDaoHolder {
        private static final VegetableDao INSTANCE = new VegetableDao();
    }

    private VegetableDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static VegetableDao getInstance() {
        return VegetableDao.VegetableDaoHolder.INSTANCE;
    }
}
