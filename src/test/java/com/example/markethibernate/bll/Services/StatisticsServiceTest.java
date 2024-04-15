package com.example.markethibernate.bll.services;

import com.example.markethibernate.bll.dtos.CheckInCount;
import com.example.markethibernate.bll.dtos.CountPerDate;
import com.example.markethibernate.utils.AppUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsServiceTest {

    @Test
    void getDayOfMonth() {
    }

    @Test
    void countPersonCheckInMonth() {
        List<CountPerDate> result = StatisticsService.getInstance().countPersonCheckInMonth(4, 2024);
        System.out.println(AppUtil.toJson(result));
    }

    @Test
    void totalPenalize() {
        System.out.println(StatisticsService.getInstance().totalPenalize(2024, 4));
    }
}