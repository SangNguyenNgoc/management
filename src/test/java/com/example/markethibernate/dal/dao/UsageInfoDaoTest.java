package com.example.markethibernate.dal.dao;

import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.UsageInfoEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UsageInfoDaoTest {
    @Test
    void findAll() {
        List<UsageInfoEntity> list = UsageInfoDao.getInstance().findAll();
        list.forEach(item -> System.out.println(item.getId().toString()));
    }
    @Test
    void findById() {
        UsageInfoEntity usageInfo = UsageInfoDao.getInstance().findById(1L);
        System.out.println(usageInfo.toString());
    }
    @Test
    void save() {
        UsageInfoEntity usageInfo = UsageInfoEntity.builder()
                .device(DeviceEntity.builder().id(1L).build())
                .build();
        System.out.println("Pre save: " + usageInfo.toString());
        UsageInfoEntity usageInfo1 = UsageInfoDao.getInstance().save(usageInfo);
        System.out.println("After save:" + usageInfo1.toString());
    }
    @Test
    void update() {
        UsageInfoEntity usageInfo = UsageInfoEntity.builder()
                .id((1L)
                .build();
        System.out.println("Pre update: " + usageInfo.toString());
        UsageInfoEntity usageInfo1 = UsageInfoDao.getInstance().update(usageInfo);
        System.out.println("After update:" + usageInfo1.toString());
    }

    @Test
    void delete() {
        UsageInfoEntity usageInfo = UsageInfoEntity.builder()
                .id(1L)
                .device(DeviceEntity.builder().id(1L).build())
                .build();
        System.out.println("Pre delete: " + usageInfo.toString());
        UsageInfoDao.getInstance().delete(usageInfo);
        System.out.println("After delete:" + usageInfo.toString());
    }

    @Test
    void deleteById() {
        UsageInfoDao.getInstance().deleteById(1L);
    }

}
