package com.example.markethibernate.bll.services;

import com.example.markethibernate.bll.dtos.DeviceValidator;
import com.example.markethibernate.dal.dao.DeviceDao;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.utils.AppUtil;
import com.example.markethibernate.utils.ValidatorUtil;

import java.util.List;
import java.util.logging.Logger;

public class DeviceService {

    private static final Logger logger = Logger.getLogger(DeviceService.class.getName());

    private DeviceService() {
    }

    public static DeviceService getInstance() {
        return DeviceServiceHolder.INSTANCE;
    }

    public List<DeviceEntity> getAll() {
        return DeviceDao.getInstance().findAll();
    }

    public DeviceEntity getById(String idString) {
        Long id = AppUtil.parseId(idString);
        if (id == null) {
            return null;
        }
        return DeviceDao.getInstance().findById(id);
    }

    public DeviceEntity createDevice(String name, String description, boolean status) {
        if (!checkName(name).isBlank()) {
            return null;
        }
        if (!checkDescription(description).isBlank()) {
            return null;
        }
        DeviceEntity device = new DeviceEntity();
        device.setName(name);
        device.setDescription(description);
        device.setStatus(status);
        DeviceDao.getInstance().createDevice(device);
        return device;
    }

    public boolean deleteDeviceById(String idString) {
        DeviceEntity device = getById(idString);
        if (device == null) {
            return false;
        }
        return DeviceDao.getInstance().deleteDeviceById(AppUtil.parseId(idString));
    }

    public DeviceEntity updateDevice(String idString, String name, String description) {
        DeviceEntity device = getById(idString);
        if (device == null) {
            return null;
        }
        if (!checkName(name).isBlank()) {
            return null;
        }
        if (!checkDescription(description).isBlank()) {
            return null;
        }
        device.setName(name);
        device.setDescription(description);
        DeviceDao.getInstance().updateDevice(device);
        return device;
    }

    public String checkName(String name) {
        return ValidatorUtil.validateField(
                DeviceValidator.builder()
                        .name(name)
                        .build(),
                "name"
        );
    }

    public String checkDescription(String description) {
        return ValidatorUtil.validateField(
                DeviceValidator.builder()
                        .description(description)
                        .build(),
                "description");
    }


    private static class DeviceServiceHolder {
        private static final DeviceService INSTANCE = new DeviceService();
    }

}
