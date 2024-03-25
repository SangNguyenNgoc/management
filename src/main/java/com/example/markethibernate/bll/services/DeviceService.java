package com.example.markethibernate.bll.services;

public class DeviceService {

    private static class DeviceServiceHolder {
        private static final DeviceService INSTANCE = new DeviceService();
    }

    private DeviceService() {
    }

    public static DeviceService getInstance() {
        return DeviceServiceHolder.INSTANCE;
    }
}
