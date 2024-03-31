package com.example.markethibernate.bll.services;
import com.example.markethibernate.dal.dao.UsageInfoDao;
import com.example.markethibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.time.LocalDateTime;
import java.util.List;
public class StatisticsService {
    private final SessionFactory sessionFactory;
    private static class StatisticsServiceHolder {
        private static final StatisticsService INSTANCE = new StatisticsService();
    }
    public static StatisticsService getInstance() {
        return StatisticsService.StatisticsServiceHolder.INSTANCE;
    }
    private StatisticsService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }
    public Long countDevicesBorrowedBetween(LocalDateTime startTime, LocalDateTime endTime) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<Long> query = session.createQuery("SELECT COUNT(DISTINCT usage.device.id) " +
                    "FROM UsageInfoEntity usage " +
                    "WHERE usage.borrowTime BETWEEN :startTime AND :endTime OR " +
                    "(usage.borrowTime < :endTime AND usage.returnTime IS NULL)", Long.class);
            query.setParameter("startTime", startTime);
            query.setParameter("endTime", endTime);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Long countDevicesBorrowedBetween2(LocalDateTime startTime, LocalDateTime endTime) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Query<Long> query = session.createQuery("SELECT COUNT(DISTINCT usage.device.id) " +
                    "FROM UsageInfoEntity usage " +
                    "WHERE (usage.borrowTime BETWEEN :startTime AND :endTime) OR " +
                    "(usage.borrowTime < :endTime AND usage.returnTime IS NULL)", Long.class);
            query.setParameter("startTime", startTime);
            query.setParameter("endTime", endTime);
            Long result = query.uniqueResult();
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }
    public Long countDevicesBorrowedAndNotReturned(LocalDateTime currentTime) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT COUNT(DISTINCT usage.device.id) " +
                    "FROM UsageInfoEntity usage " +
                    "WHERE usage.borrowTime <= :currentTime " +
                    "AND (usage.returnTime IS NULL OR usage.returnTime > :currentTime)";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("currentTime", currentTime);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return null;
        }
    }
}
