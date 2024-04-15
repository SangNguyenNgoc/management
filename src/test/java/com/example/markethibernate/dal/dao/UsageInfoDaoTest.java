package com.example.markethibernate.dal.dao;

import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.dal.entities.UsageInfoEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class UsageInfoDaoTest {
    @Test
    void findAll() {
        List<UsageInfoEntity> list = UsageInfoDao.getInstance().findAll();
        list.forEach(item -> System.out.println(item.toString()));
    }
    @Test
    void findById() {
        UsageInfoEntity usageInfo = UsageInfoDao.getInstance().findById(6L);
        System.out.println(usageInfo);
    }
    @Test
    void save() {
        UsageInfoEntity usageInfo = UsageInfoEntity.builder()
                .borrowTime(LocalDateTime.now())
                .checkinTime(LocalDateTime.now())
                .returnTime(LocalDateTime.now())
                .person(PersonEntity.builder().id(1L).build())
                .device(DeviceEntity.builder().id(1000001L).build())
                .build();
        System.out.println("Pre save: " + usageInfo.toString());
        usageInfo = UsageInfoDao.getInstance().save(usageInfo);
        System.out.println("After save:" + usageInfo.toString());
    }
    @Test
    void update() {
        UsageInfoEntity usageInfo = UsageInfoEntity.builder()
                .id(4L)
                .borrowTime(LocalDateTime.now())
                .checkinTime(LocalDateTime.now())
                .returnTime(LocalDateTime.now())
                .person(PersonEntity.builder().id(1L).build())
                .device(DeviceEntity.builder().id(1000001L).build())
                .build();
        System.out.println("Pre update: " + usageInfo.toString());
        usageInfo = UsageInfoDao.getInstance().update(usageInfo);
        System.out.println("After update:" + usageInfo.toString());
    }

    @Test
    void deleteById() {
        UsageInfoDao.getInstance().deleteById(1L);
    }

}
