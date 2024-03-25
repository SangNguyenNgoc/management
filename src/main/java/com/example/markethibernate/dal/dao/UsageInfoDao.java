package com.example.markethibernate.dal.dao;

import com.example.markethibernate.utils.HibernateUtil;
import org.hibernate.SessionFactory;

public class UsageInfoDao {

    private final SessionFactory sessionFactory;

    private static class UsageInfoDaoHolder {
        private static final UsageInfoDao INSTANCE = new UsageInfoDao();
    }

    private UsageInfoDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static UsageInfoDao getInstance() {
        return UsageInfoDao.UsageInfoDaoHolder.INSTANCE;
    }
}
