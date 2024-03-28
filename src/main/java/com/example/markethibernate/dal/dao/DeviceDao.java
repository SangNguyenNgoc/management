package com.example.markethibernate.dal.dao;

import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.utils.HibernateUtil;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;
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
//    Xoa theo dieu kien
    public boolean deleteDevicesByCondition(String condition) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<DeviceEntity> criteriaDelete = builder.createCriteriaDelete(DeviceEntity.class);
            Root<DeviceEntity> root = criteriaDelete.from(DeviceEntity.class);
            criteriaDelete.where(builder.equal(root.get(condition), condition));
            int result = session.createQuery(criteriaDelete).executeUpdate();
            transaction.commit();
            return result > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Failed to delete devices by condition", e);
            throw new RuntimeException("Failed to delete devices by condition", e);
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
    public DeviceEntity createDevice(DeviceEntity device) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(device);
            transaction.commit();
            return device;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Failed to create device", e);
            return null;
        }
    }
    public boolean deleteDevice(DeviceEntity device) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(device);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Failed to delete device", e);
            return false;
        }
    }

//    Xoa theo id
    public boolean deleteDeviceById(int id) {
        DeviceEntity device = findById(id);
        if (device != null) {
            return deleteDevice(device);
        }
        return false;
    }
    public DeviceEntity updateDevice(DeviceEntity device) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(device);
            transaction.commit();
            return device;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Failed to update device", e);
            throw new RuntimeException("Failed to update device", e);
        }
    }
}
