package com.example.markethibernate.dal.dao;

import com.example.markethibernate.bll.dtos.CountPerDate;
import com.example.markethibernate.bll.dtos.DeviceBorrowingStatByName;
import com.example.markethibernate.bll.dtos.DeviceBorrowingStatByTime;
import com.example.markethibernate.utils.AppUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

class StatisticsDaoTest {

    @Test
    void countPeopleCheckInMonth() {
        List<CountPerDate> checkInCounts = StatisticsDao.getInstance().countPeopleCheckInMonth(4,2024);
        System.out.println(AppUtil.toJson(checkInCounts));
    }

    @Test
    void countDevicesBorrowedInMonth() {
        List<DeviceBorrowingStatByTime> result = StatisticsDao.getInstance().countDevicesBorrowedInMonth(4,2024);
        System.out.println(AppUtil.toJson(result));
    }

    @Test
    void countDevicesBorrowedInMonthById() {
        List<DeviceBorrowingStatByName> result = StatisticsDao.getInstance().countDevicesBorrowedInMonthById(4,2024, 7000001L);
        System.out.println(AppUtil.toJson(result));
    }
}