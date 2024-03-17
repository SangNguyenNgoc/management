package com.example.markethibernate.bll.services;

import java.util.logging.Logger;

public class VegetableService {

    private static final Logger logger = Logger.getLogger(OrderService.class.getName());

    private static class VegetableServiceHolder {
        private static final VegetableService INSTANCE = new VegetableService();
    }

    private VegetableService() {

    }

    public static VegetableService getInstance() {
        return VegetableService.VegetableServiceHolder.INSTANCE;
    }
}
