package com.example.markethibernate.bll.services;

import java.util.logging.Logger;

public class OrderDetailService {

    private static final Logger logger = Logger.getLogger(OrderDetailService.class.getName());

    private static class OrderDetailServiceHolder {
        private static final OrderDetailService INSTANCE = new OrderDetailService();
    }

    private OrderDetailService() {

    }

    public static OrderDetailService getInstance() {
        return OrderDetailService.OrderDetailServiceHolder.INSTANCE;
    }
}
