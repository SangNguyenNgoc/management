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
    @Test
    void deleteDeviceById() {
        if (DeviceDao.getInstance().findById(1000006) == null) {
            System.out.println("Not found");
        } else {
            DeviceDao.getInstance().deleteDeviceById(1000006);
            System.out.println("Deleted");
        }
    }
    @Test
    void add() {
        DeviceEntity device = new DeviceEntity();
        device.setName("Màn hình máy tính Samsung");
        device.setDescription("Màn hình máy ");
        device.setStatus(true);

        try {
            DeviceDao.getInstance().createDevice(device);
            System.out.println("Added");
        } catch (Exception e) {
            System.out.println("Error");
        }

        

    }
    @Test
    void update() {
       DeviceEntity device =  DeviceDao.getInstance().findById(1000005);
       System.out.println("Pre update: " + device.toString());
         device.setName("Màn hình máy tính Acer");
         device.setDescription("Màn hình máy tính");
         DeviceEntity device1 = DeviceDao.getInstance().updateDevice(device);
         System.out.println("After update:" + device1.toString());
    }

}