package com.example.markethibernate.dal.dao;

import com.example.markethibernate.utils.HibernateUtil;
import org.hibernate.SessionFactory;

public class PenalizeDao {

    private final SessionFactory sessionFactory;

    private static class PenalizeDaoHolder {
        private static final PenalizeDao INSTANCE = new PenalizeDao();
    }

    private PenalizeDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static PenalizeDao getInstance() {
        return PenalizeDao.PenalizeDaoHolder.INSTANCE;
    }
}
