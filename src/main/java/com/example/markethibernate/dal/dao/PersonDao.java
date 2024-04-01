package com.example.markethibernate.dal.dao;

import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.logging.Level;
import java.util.logging.Logger;
public class PersonDao {

    private final SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(PersonDao.class.getName());

    private static class PersonDaoHolder {
        private static final PersonDao INSTANCE = new PersonDao();
    }

    private PersonDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static PersonDao getInstance() {
        return PersonDao.PersonDaoHolder.INSTANCE;
    }

    public PersonEntity addPerson(PersonEntity person) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(person);
            transaction.commit();
            return person;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Failed to add person", e);
            return null;
        }
    }

    public PersonEntity updatePerson(PersonEntity person) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(person);
            transaction.commit();
            return person;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Failed to update person", e);
            return null;
        }
    }

    public PersonEntity deletePersonById(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            PersonEntity person = session.get(PersonEntity.class, id);
            if (person != null) {
                person.setStatus(false); //Chuyển status về 0
                session.update(person);
            }

            transaction.commit();
            return person;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Failed to delete person", e);
            return null;
        }
    }
}
