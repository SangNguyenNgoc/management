package com.example.markethibernate.dal.dao;

import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.utils.HibernateUtil;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeviceDao {

    private final SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(DeviceDao.class.getName());

    private static class DeviceDaoHolder {
        private static final DeviceDao INSTANCE = new DeviceDao();
    }

    private DeviceDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static DeviceDao getInstance() {
        return DeviceDaoHolder.INSTANCE;
    }

    public List<DeviceEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<DeviceEntity> devices = session.createQuery("FROM DeviceEntity ", DeviceEntity.class).list();
            session.close();
            return devices;
        }
    }

    public DeviceEntity findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM DeviceEntity d WHERE d.id = :id", DeviceEntity.class);
            query.setParameter("id", id);
            var devices = query.getResultList();
            if(!devices.isEmpty()) {
                return (DeviceEntity) devices.get(0);
            } else {
                return null;
            }
        }
    }

    public DeviceEntity save(DeviceEntity device) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(device);
            transaction.commit();
            session.close();
            return device;
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Query failure");
            return null;
        }
    }
}
