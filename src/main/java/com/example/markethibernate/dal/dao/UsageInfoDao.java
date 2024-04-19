package com.example.markethibernate.dal.dao;

import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.dal.entities.UsageInfoEntity;
import com.example.markethibernate.utils.HibernateUtil;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsageInfoDao {

    private final SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(PersonDao.class.getName());

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
            List<UsageInfoEntity> usageInfos = session.createQuery(
                    "FROM  UsageInfoEntity u join fetch u.person join fetch u.device",
                    UsageInfoEntity.class).list();
            session.close();
            return usageInfos;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Query failure", ex);
            return new ArrayList<>();
        }
    }

    public List<UsageInfoEntity> findAllCheckIn() {
        try (Session session = sessionFactory.openSession()) {
            List<UsageInfoEntity> usageInfos = session.createQuery(
                    "FROM  UsageInfoEntity u join fetch u.person where u.device = null order by u.checkinTime desc ",
                    UsageInfoEntity.class).list();
            session.close();
            return usageInfos;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Query failure", ex);
            return new ArrayList<>();
        }
    }

    public Boolean checkDeviceIsUsing(Long deviceId) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(
                    "FROM  UsageInfoEntity u join fetch u.device where u.device.id = :id and u.returnTime = null ",
                    UsageInfoEntity.class);
            var resultList = query.getResultList();
            return resultList.isEmpty();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Query failure", ex);
            return false;
        }
    }

    public UsageInfoEntity findById(long id) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT u FROM UsageInfoEntity u JOIN FETCH u.person JOIN FETCH u.device WHERE u.id = :id";
            Query query = session.createQuery(hql, UsageInfoEntity.class);
            query.setParameter("id", id);
            List<UsageInfoEntity> usageInfoList = query.getResultList();
            if (!usageInfoList.isEmpty()) {
                return usageInfoList.get(0);
            } else {
                return null;
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Query failure", ex);
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
            logger.log(Level.SEVERE, "Query failure", e);
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
            logger.log(Level.SEVERE, "Query failure", e);
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
            logger.log(Level.SEVERE, "Query failure", e);
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
            logger.log(Level.SEVERE, "Query failure", e);
            return false;
        }
    }
}
