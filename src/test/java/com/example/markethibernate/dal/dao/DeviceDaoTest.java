package com.example.markethibernate.dal.dao;

import com.example.markethibernate.dal.entities.DeviceEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeviceDaoTest {

    @Test
    void findAll() {
        List<DeviceEntity> deviceEntities = DeviceDao.getInstance().findAll();
        deviceEntities.forEach(item -> System.out.println(item.toString()));
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
        DeviceEntity device = DeviceEntity.builder()
                .name("Màn hình máy tính")
                .description("Màn hình máy tính")
                .build();
        System.out.println("Pre save: " + device.toString());
        DeviceEntity device1 = DeviceDao.getInstance().save(device);
        System.out.println("After save:" + device1.toString());
    }
}