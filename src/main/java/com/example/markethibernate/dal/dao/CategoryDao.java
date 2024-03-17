package com.example.markethibernate.dal.dao;

import com.example.markethibernate.dal.entities.Category;
import com.example.markethibernate.utils.HibernateUtil;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryDao {

    private final SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(CategoryDao.class.getName());

    private static class CategoryDaoHolder {
        private static final CategoryDao INSTANCE = new CategoryDao();
    }

    private CategoryDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static CategoryDao getInstance() {
        return CategoryDaoHolder.INSTANCE;
    }

    public List<Category> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<Category> categories = session.createQuery("FROM Category", Category.class).list();
            session.close();
            return categories;
        }
    }

    public Category findById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Category c WHERE c.id = :id", Category.class);
            query.setParameter("id", id);
            var categories = query.getResultList();
            if(!categories.isEmpty()) {
                return (Category) categories.get(0);
            } else {
                return null;
            }
        }
    }

    public Category save(Category category) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(category);
            transaction.commit();
            session.close();
            return category;
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            logger.log(Level.SEVERE, "Query failure");
            return null;
        }
    }

}
