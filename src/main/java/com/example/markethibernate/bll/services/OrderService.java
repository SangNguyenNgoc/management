package com.example.markethibernate.bll.services;

import java.util.logging.Logger;

public class OrderService {

    private static final Logger logger = Logger.getLogger(OrderService.class.getName());

    private static class OrderServiceHolder {
        private static final OrderService INSTANCE = new OrderService();
    }

    private OrderService() {

    }

    public static OrderService getInstance() {
        return OrderService.OrderServiceHolder.INSTANCE;
    }
}
