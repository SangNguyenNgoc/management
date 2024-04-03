package com.example.markethibernate.dal.dao;

import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.utils.HibernateUtil;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
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

    public List<PersonEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<PersonEntity> persons = session.createQuery("FROM PersonEntity ", PersonEntity.class).list();
            session.close();
            return persons;
        }
    }

    public PersonEntity findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM PersonEntity p WHERE p.id = :id", PersonEntity.class);
            query.setParameter("id", id);
            var persons = query.getResultList();
            if(!persons.isEmpty()) {
                return (PersonEntity) persons.get(0);
            } else {
                return null;
            }
        }
    }

    public PersonEntity findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM PersonEntity p WHERE p.email = :email", PersonEntity.class);
            query.setParameter("email", email);
            var persons = query.getResultList();
            if(!persons.isEmpty()) {
                return (PersonEntity) persons.get(0);
            } else {
                return null;
            }
        }
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

    public PersonEntity deletePersonById(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            PersonEntity person = session.get(PersonEntity.class, id);
            if (person != null) {
                person.setStatus(false);
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
