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

    public DeviceEntity createDevice(String name, String description, boolean status) {
        if(name.isEmpty()) {
            throw new IllegalArgumentException("Name is empty");
        }
        else if (description.isEmpty()) {
            throw new IllegalArgumentException("Description is empty");
        }
        else if (name.length() > 255) {
            throw new IllegalArgumentException("Name is too long");
        }
        else if (description.length() > 255) {
            throw new IllegalArgumentException("Description is too long");
        }
        else if (name.length() < 5) {
            throw new IllegalArgumentException("Name is too short");
        }
        else if (description.length() < 5) {
            throw new IllegalArgumentException("Description is too short");
        }
        else {
            DeviceEntity device = new DeviceEntity();
            device.setName(name);
            device.setDescription(description);
            device.setStatus(status);

            DeviceDao.getInstance().createDevice(device);
            return device;
        }
    }
    public boolean deleteDeviceById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID: " + id);
        }
        else {
            DeviceEntity device = DeviceDao.getInstance().findById(id);
            if (device == null) {
                throw new IllegalArgumentException("Device not found with ID: " + id);
            }
            else {
                DeviceDao.getInstance().deleteDeviceById(id);
            }
        }
        return true;
    }

    public DeviceEntity updateDevice(Integer id, String name, String description, boolean status) {
        if(name.isEmpty()) {
            throw new IllegalArgumentException("Name is empty");
        }
        else if (description.isEmpty()) {
            throw new IllegalArgumentException("Description is empty");
        }
        else if (name.length() > 255) {
            throw new IllegalArgumentException("Name is too long");
        }
        else if (description.length() > 255) {
            throw new IllegalArgumentException("Description is too long");
        }
        else if (name.length() < 5) {
            throw new IllegalArgumentException("Name is too short");
        }
        else if (description.length() < 5) {
            throw new IllegalArgumentException("Description is too short");
        }
        else {
            DeviceEntity device = DeviceDao.getInstance().findById(id);
            device.setName(name);
            device.setDescription(description);
            device.setStatus(status);

            DeviceDao.getInstance().updateDevice(device);
            return device;
        }
    }



}
