package com.example.markethibernate.dal.dao;

import com.example.markethibernate.dal.entities.UsageInfoEntity;
import com.example.markethibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

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
            List<UsageInfoEntity> usageInfos = session.createQuery("FROM  UsageInfoEntity", UsageInfoEntity.class).list();
            session.close();
            return usageInfos;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public UsageInfoEntity findById(long id) {
        try (Session session = sessionFactory.openSession()) {
            var usageInfo = session.get(UsageInfoEntity.class, id);
            session.close();
            return usageInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public UsageInfoEntity save(UsageInfoEntity usageInfo) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(usageInfo);
            transaction.commit();
            return usageInfo;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    public UsageInfoEntity update(UsageInfoEntity usageInfo) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(usageInfo);
            transaction.commit();
            session.close();
            return usageInfo;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }
    public Boolean deleteById(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            var usageInfo = session.get(UsageInfoEntity.class, id);
            session.delete(usageInfo);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }


    public Boolean isExistsById(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            var usageInfo = session.get(UsageInfoEntity.class, id);
            transaction.commit();
            session.close();
            return usageInfo != null;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
}
