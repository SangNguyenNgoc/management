package com.example.markethibernate.bll.services;

import com.example.markethibernate.dal.dao.VegetableDao;

import java.util.logging.Logger;

public class CustomerService {

    private static final Logger logger = Logger.getLogger(CustomerService.class.getName());

    private static class CustomerServiceHolder {
        private static final CustomerService INSTANCE = new CustomerService();
    }

    private CustomerService() {

    }

    public static CustomerService getInstance() {
        return CustomerService.CustomerServiceHolder.INSTANCE;
    }
}
