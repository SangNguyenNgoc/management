package com.example.markethibernate.dal.dao;

import com.example.markethibernate.dal.entities.PenalizeEntity;
import com.example.markethibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatisticsDao {
    private final SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(StatisticsDao.class.getName());

    private StatisticsDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static StatisticsDao getInstance() {
        return StatisticsDaoHolder.INSTANCE;
    }

    public Long countPeopleCheckInBetween(LocalDateTime startTime, LocalDateTime endTime) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Long> query = session.createQuery("SELECT COUNT(DISTINCT usage.person.id) " +
                    "FROM UsageInfoEntity usage " +
                    "WHERE usage.borrowTime BETWEEN :startTime AND :endTime OR " +
                    "(usage.borrowTime < :endTime AND usage.returnTime IS NULL)", Long.class);
            query.setParameter("startTime", startTime);
            query.setParameter("endTime", endTime);
            return query.uniqueResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Query failure", e);
            return null;
        }

    }

    public Long countDevicesBorrowedBetween(LocalDateTime startTime, LocalDateTime endTime) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Long> query = session.createQuery("SELECT COUNT(DISTINCT usage.device.id) " +
                    "FROM UsageInfoEntity usage " +
                    "WHERE (usage.borrowTime BETWEEN :startTime AND :endTime) OR " +
                    "(usage.borrowTime < :endTime AND usage.returnTime IS NULL)", Long.class);
            query.setParameter("startTime", startTime);
            query.setParameter("endTime", endTime);
            Long result = query.uniqueResult();
            return result;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Query failure", e);
            return null;
        }
    }

    public Long countDevicesBorrowedAndNotReturned(LocalDateTime startTime, LocalDateTime endTime) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT COUNT(DISTINCT usage.device.id) " +
                    "FROM UsageInfoEntity usage " +
                    "WHERE usage.borrowTime BETWEEN :startTime AND :endTime " +
                    "AND (usage.returnTime IS NULL OR usage.returnTime > :endTime)" +
                    "AND usage.returnTime IS NULL";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("startTime", startTime);
            query.setParameter("endTime", endTime);
            return query.uniqueResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Query failure", e);
            return null;
        }
    }

    public Object[] sumPenaltiesByStatus(boolean processed) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT COUNT(p), COALESCE(SUM(p.payment), 0) " +
                    "FROM PenalizeEntity p " +
                    "WHERE p.status = :processed";
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("processed", processed);
            return query.uniqueResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Query failure", e);
            return null;
        }
    }

    public List<PenalizeEntity> getPenaltiesByStatus(boolean processed) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM PenalizeEntity p WHERE p.status = :processed";
            Query<PenalizeEntity> query = session.createQuery(hql, PenalizeEntity.class);
            query.setParameter("processed", processed);
            return query.list();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Query failure", e);
            return new ArrayList<>();
        }
    }

    private static class StatisticsDaoHolder {
        private static final StatisticsDao INSTANCE = new StatisticsDao();
    }
}
