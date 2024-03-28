package com.example.markethibernate.bll.services;

import com.example.markethibernate.dal.dao.DeviceDao;
import com.example.markethibernate.dal.entities.DeviceEntity;

public class DeviceService {

    private static class DeviceServiceHolder {
        private static final DeviceService INSTANCE = new DeviceService();
    }

    private DeviceService() {
    }

    public static DeviceService getInstance() {
        return DeviceServiceHolder.INSTANCE;
    }

    public void deleteDevice(DeviceEntity device) {
        if (device != null) {
            DeviceDao.getInstance().deleteDevice(device);
        }

    }

    public void updateDevice(DeviceEntity device) {
        if (device != null) {
            DeviceDao.getInstance().updateDevice(device);
        }

    }
    public void createDevice(DeviceEntity device) {
        if (device != null) {
            DeviceDao.getInstance().save(device);
        }
    }

}
