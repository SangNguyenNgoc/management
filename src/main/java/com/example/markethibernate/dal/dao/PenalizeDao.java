package com.example.markethibernate.dal.dao;

import com.example.markethibernate.dal.entities.PenalizeEntity;

import com.example.markethibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PenalizeDao {

    private final SessionFactory sessionFactory;

    private static class PenalizeDaoHolder {
        private static final PenalizeDao INSTANCE = new PenalizeDao();
    }

    private PenalizeDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static PenalizeDao getInstance() {
        return PenalizeDao.PenalizeDaoHolder.INSTANCE;
    }

    public PenalizeEntity addPenalize(PenalizeEntity penalize) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(penalize);
            transaction.commit();
            return penalize;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Failed to add person", e);
            return null;
        }
    }

    public PenalizeEntity updatePenalize(PenalizeEntity penalize) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(penalize);
            transaction.commit();
            return penalize;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Failed to update penalize", e);
            return null;
        }
    }


    public PenalizeEntity deletePenalizeById(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            PenalizeEntity penalize = session.get(PenalizeEntity.class, id);
            if (penalize != null) {
                penalize.setStatus(false);
                session.update(penalize);
            }

            transaction.commit();
            return penalize;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Failed to delete penalize", e);
            return null;
        }
    }




}

