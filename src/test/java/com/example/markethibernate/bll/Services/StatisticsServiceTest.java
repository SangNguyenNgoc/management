package com.example.markethibernate.bll.Services;

import com.example.markethibernate.bll.services.StatisticsService;
import com.example.markethibernate.dal.entities.PenalizeEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StatisticsServiceTest {

    private StatisticsService instance = StatisticsService.getInstance();

    //- Số lượng thành viên vào khu học tập theo thời gian
    @Test
    void countPeopleCheckInBetweenTest(){
        Long count = instance.countPeopleCheckInBetween(LocalDateTime.of(2024, 1, 1, 0, 0), LocalDateTime.now());
        Assertions.assertEquals(count, 3);
    }
    // - Thống kê các thiết bị được mượn theo thời gian(kể cả trả rồi và chưa trả)
    @Test
    void countDevicesBorrowedBetweenTest() {
        Long count = instance.countDevicesBorrowedBetween(LocalDateTime.of(2024, 1, 1, 0, 0), LocalDateTime.now());
        Assertions.assertEquals(count, 3);
    }

    // - Thống kê các thiết bị đang được mượn theo thời gian(mượn nhưng chưa trả)
    @Test
    void countDevicesBorrowedAndNotReturnedTest() {
        Long count = instance.countDevicesBorrowedAndNotReturned(LocalDateTime.of(2024,1,1,0,0), LocalDateTime.now());
        Assertions.assertEquals(count, 0);
    }

    // - Thống kê các xử lý vi phạm theo đã được xử lý hay chưa được xử lý, tính tổng tiền bồi thường
    @Test
    void sumPenaltiesByStatusTest() {

        Object[] test = instance.sumPenaltiesByStatus(true);
        for (Object obj:
             test) {
            System.out.println(test);
        }
    }

    @Test
    void getPenaltiesByStatus() {
        List<PenalizeEntity> listPenaltiesStatusFalse = instance.getPenaltiesByStatus(false);
        Assertions.assertEquals(listPenaltiesStatusFalse.size(), 3);

        List<PenalizeEntity> listPenaltiesStatusTrue = instance.getPenaltiesByStatus(true);
        Assertions.assertEquals(listPenaltiesStatusTrue.size(), 0);


    }
}
