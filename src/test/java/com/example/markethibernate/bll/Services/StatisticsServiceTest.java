package com.example.markethibernate.bll.Services;

import com.example.markethibernate.bll.services.StatisticsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StatisticsServiceTest {

    @Test
    void CountDevicesBorrowedBetween(){
        Long count = StatisticsService.getInstance().countDevicesBorrowedBetween(LocalDateTime.of(2024, 1, 1, 0, 0), LocalDateTime.now());

        Assertions.assertEquals(count, 3);
    }
}
