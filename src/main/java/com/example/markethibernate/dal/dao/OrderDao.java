package com.example.markethibernate.dal.dao;

import com.example.markethibernate.utils.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.logging.Logger;

public class OrderDao {
    private final SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(OrderDao.class.getName());

    private static class OrderDaoHolder {
        private static final OrderDao INSTANCE = new OrderDao();
    }

    private OrderDao() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static OrderDao getInstance() {
        return OrderDao.OrderDaoHolder.INSTANCE;
    }

}
