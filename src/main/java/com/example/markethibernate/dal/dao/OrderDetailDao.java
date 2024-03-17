package com.example.markethibernate.dal.dao;

import com.example.markethibernate.utils.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.logging.Logger;

public class OrderDetailDao {
    private final SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(OrderDetailDao.class.getName());

    private static class OrderDetailDaoHolder {
        private static final OrderDetailDao INSTANCE = new OrderDetailDao();
    }

    private OrderDetailDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static OrderDetailDao getInstance() {
        return OrderDetailDao.OrderDetailDaoHolder.INSTANCE;
    }
}
