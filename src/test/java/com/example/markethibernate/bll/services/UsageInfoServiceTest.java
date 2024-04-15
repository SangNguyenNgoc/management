package com.example.markethibernate.bll.services;

import com.example.markethibernate.dal.dao.UsageInfoDao;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
import com.example.markethibernate.dal.entities.UsageInfoEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class UsageInfoServiceTest {
    @Test
    void findAll() {
        List<UsageInfoEntity> list = UsageInfoService.getInstance().findAll();
        list.forEach(item -> System.out.println(item.toString()));
    }
    @Test
    void findById() {
        UsageInfoEntity usageInfo = UsageInfoService.getInstance().getById("6");
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
        usageInfo = UsageInfoService.getInstance().save(usageInfo);
        System.out.println("After save:" + usageInfo.toString());
    }
    @Test
    void update() {
        UsageInfoEntity usageInfo = UsageInfoEntity.builder()
                .id(5L)
                .borrowTime(LocalDateTime.now())
                .checkinTime(LocalDateTime.now())
                .returnTime(LocalDateTime.now())
                .person(PersonEntity.builder().id(5L).build())
                .device(DeviceEntity.builder().id(1000001L).build())
                .build();
        System.out.println("Pre update: " + usageInfo.toString());
        usageInfo = UsageInfoService.getInstance().update(usageInfo);
        System.out.println("After update:" + usageInfo.toString());
    }

    @Test
    void deleteById() {
        UsageInfoService.getInstance().deleteById(5L);
    }

    @Test
    void checkIn() {
        UsageInfoEntity result = UsageInfoService.getInstance().checkIn("12");
        System.out.println(result.toString());
    }

    @Test
    void borrowDevice() {
        UsageInfoEntity result = UsageInfoService.getInstance().borrowDevice("10", "1000001");
        System.out.println(result.toString());
    }

    @Test
    void returnDevice() {
        UsageInfoEntity result = UsageInfoService.getInstance().returnDevice("4");
        System.out.println(result.toString());
    }
}
