package com.example.markethibernate.dal.dao;

import com.example.markethibernate.dal.entities.UsageInfoEntity;
import com.example.markethibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

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


    public List<UsageInfoEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<UsageInfoEntity> usageInfos = session.createQuery("FROM UsageInfoEntity ", UsageInfoEntity.class).list();
            session.close();
            return usageInfos;
        }
    }

    public UsageInfoEntity findById(long id) {
        try (Session session = sessionFactory.openSession()) {
            var usageInfo = session.get(UsageInfoEntity.class, id);
            session.close();
            return usageInfo;
        }
    }

    public UsageInfoEntity save(UsageInfoEntity usageInfo) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(usageInfo);
            session.getTransaction().commit();
            session.close();
            return usageInfo;
        }
    }

    public UsageInfoEntity update(UsageInfoEntity usageInfo) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(usageInfo);
            session.getTransaction().commit();
            session.close();
            return usageInfo;
        }
    }
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            var usageInfo = session.get(UsageInfoEntity.class, id);
            session.delete(usageInfo);
            session.getTransaction().commit();
            session.close();
        }
    }


    private Boolean isExistsById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            var usageInfo = session.get(UsageInfoEntity.class, id);
            session.close();
            return usageInfo != null;
        }
    }
}
