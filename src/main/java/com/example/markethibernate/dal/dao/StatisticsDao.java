package com.example.markethibernate.dal.dao;

import com.example.markethibernate.bll.dtos.CountPerDate;
import com.example.markethibernate.bll.dtos.DeviceBorrowingStatByName;
import com.example.markethibernate.bll.dtos.DeviceBorrowingStatByTime;
import com.example.markethibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatisticsDao {
    private static final Logger logger = Logger.getLogger(StatisticsDao.class.getName());
    private final SessionFactory sessionFactory;

    private StatisticsDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static StatisticsDao getInstance() {
        return StatisticsDaoHolder.INSTANCE;
    }

    public List<CountPerDate> countPeopleCheckInMonth(Integer month, Integer year) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<CountPerDate> query = session.createQuery(
                    "SELECT NEW com.example.markethibernate.bll.dtos.CountPerDate(u.checkinTime, COUNT(u.id)) " +
                            "FROM UsageInfoEntity u " +
                            "WHERE MONTH (u.checkinTime) = :month " +
                            "AND YEAR (u.checkinTime) = :year " +
                            "GROUP BY DATE(u.checkinTime) " +
                            "ORDER BY u.checkinTime DESC", CountPerDate.class);
            query.setParameter("month", month);
            query.setParameter("year", year);
            return query.list();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Query failure", e);
            return null;
        }
    }

    public List<CountPerDate> countPenalizeInMonthPresent(Integer month, Integer year) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<CountPerDate> query = session.createQuery(
                    "SELECT NEW com.example.markethibernate.bll.dtos.CountPerDate(p.date, COUNT(p.id)) " +
                            "FROM PenalizeEntity p " +
                            "WHERE MONTH (p.date) = :month " +
                            "AND YEAR (p.date) = :year " +
                            "AND p.status = false " +
                            "GROUP BY DATE(p.date) " +
                            "ORDER BY p.date DESC", CountPerDate.class);
            query.setParameter("month", month);
            query.setParameter("year", year);
            return query.list();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Query failure", e);
            return null;
        }
    }

    public List<CountPerDate> countPenalizeInMonthNotPresent(Integer month, Integer year) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<CountPerDate> query = session.createQuery(
                    "SELECT NEW com.example.markethibernate.bll.dtos.CountPerDate(p.date, COUNT(p.id)) " +
                            "FROM PenalizeEntity p " +
                            "WHERE MONTH (p.date) = :month " +
                            "AND YEAR (p.date) = :year " +
                            "AND p.status = true " +
                            "GROUP BY DATE(p.date) " +
                            "ORDER BY p.date DESC", CountPerDate.class);
            query.setParameter("month", month);
            query.setParameter("year", year);
            return query.list();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Query failure", e);
            return null;
        }
    }

    public List<DeviceBorrowingStatByTime> countDevicesBorrowedInMonth(Integer month, Integer year) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<DeviceBorrowingStatByTime> query = session.createQuery(
                    "SELECT new com.example.markethibernate.bll.dtos.DeviceBorrowingStatByTime(u.device.id, u.device.name, COUNT (MONTH (u.borrowTime))) " +
                            "FROM UsageInfoEntity u " +
                            "WHERE MONTH (u.borrowTime) = :month " +
                            "AND YEAR (u.borrowTime) = :year " +
                            "AND u.returnTime is not null " +
                            "GROUP BY u.device.id " +
                            "ORDER BY u.device.id", DeviceBorrowingStatByTime.class);
            query.setParameter("month", month);
            query.setParameter("year", year);
            return query.list();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Query failure", e);
            return null;
        }
    }

    public List<DeviceBorrowingStatByTime> countDevicesBorrowedInMonthNotReturn(Integer month, Integer year) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<DeviceBorrowingStatByTime> query = session.createQuery(
                    "SELECT new com.example.markethibernate.bll.dtos.DeviceBorrowingStatByTime(u.device.id, u.device.name, COUNT (MONTH (u.borrowTime))) " +
                            "FROM UsageInfoEntity u " +
                            "WHERE MONTH (u.borrowTime) = :month " +
                            "AND YEAR (u.borrowTime) = :year " +
                            "AND u.returnTime = null " +
                            "GROUP BY u.device.id " +
                            "ORDER BY u.device.id", DeviceBorrowingStatByTime.class);
            query.setParameter("month", month);
            query.setParameter("year", year);
            return query.list();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Query failure", e);
            return null;
        }
    }


    public List<DeviceBorrowingStatByName> countDevicesBorrowedInMonthById(Integer month, Integer year, Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<DeviceBorrowingStatByName> query = session.createQuery(
                    """
                            select new com.example.markethibernate.bll.dtos.DeviceBorrowingStatByName(u.device.id, u.device.name, count (month (u.borrowTime)), u.borrowTime)
                            from UsageInfoEntity u
                            where month (u.borrowTime) = :month
                            and year (u.borrowTime) = :year
                            and u.device.id = :id
                            group by day (u.borrowTime)
                            order by u.borrowTime
                            """, DeviceBorrowingStatByName.class);
            query.setParameter("month", month);
            query.setParameter("year", year);
            query.setParameter("id", id);
            return query.list();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Query failure", e);
            return null;
        }
    }




    private static class StatisticsDaoHolder {
        private static final StatisticsDao INSTANCE = new StatisticsDao();
    }
}
