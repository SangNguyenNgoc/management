package com.example.markethibernate.bll.services;

import com.example.markethibernate.bll.dtos.CountPerDate;
import com.example.markethibernate.bll.dtos.DeviceBorrowingStatByName;
import com.example.markethibernate.bll.dtos.DeviceBorrowingStatByTime;
import com.example.markethibernate.dal.dao.PenalizeDao;
import com.example.markethibernate.dal.dao.StatisticsDao;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PenalizeEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class StatisticsService {

    private static final Logger logger = Logger.getLogger(StatisticsDao.class.getName());

    private StatisticsService() {
    }

    public static StatisticsService getInstance() {
        return StatisticsService.StatisticsServiceHolder.INSTANCE;
    }

    public List<LocalDate> getDayOfMonth(int month, int year) {
        List<LocalDate> daysOfMonth = new ArrayList<>();
        LocalDate currentDate = LocalDate.of(year, month, 1); // Ngày đầu tiên của tháng
        LocalDate lastDayOfMonth = LocalDate.of(year, month, currentDate.lengthOfMonth()); // Ngày cuối cùng của tháng
        while (!currentDate.isAfter(lastDayOfMonth)) {
            daysOfMonth.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return daysOfMonth;
    }

    public List<CountPerDate> countPersonCheckInMonth(Integer month, Integer year) {
        List<LocalDate> dayOfMonth = getDayOfMonth(month, year);
        List<CountPerDate> checkInCounts = StatisticsDao.getInstance().countPeopleCheckInMonth(month, year);
        return dayOfMonth.stream()
                .map(day -> {
                    Optional<CountPerDate> checkIn = checkInCounts.stream()
                            .filter(checkInCount -> checkInCount.getDateTime().toLocalDate().equals(day))
                            .findFirst();
                    return checkIn.orElse(CountPerDate.builder()
                            .dateTime(day.atStartOfDay())
                            .count(0L)
                            .build());
                })
                .collect(Collectors.toList());
    }

    public List<CountPerDate> countPersonCheckInMonthByDepartment(Integer month, Integer year, String department) {
        List<LocalDate> dayOfMonth = getDayOfMonth(month, year);
        List<CountPerDate> checkInCounts = StatisticsDao.getInstance().countPeopleCheckInMonthByDepartment(month, year, department);
        return dayOfMonth.stream()
                .map(day -> {
                    Optional<CountPerDate> checkIn = checkInCounts.stream()
                            .filter(checkInCount -> checkInCount.getDateTime().toLocalDate().equals(day))
                            .findFirst();
                    return checkIn.orElse(CountPerDate.builder()
                            .dateTime(day.atStartOfDay())
                            .count(0L)
                            .build());
                })
                .collect(Collectors.toList());
    }

    public List<CountPerDate> countPersonCheckInMonthByMajor(Integer month, Integer year, String major) {
        List<LocalDate> dayOfMonth = getDayOfMonth(month, year);
        List<CountPerDate> checkInCounts = StatisticsDao.getInstance().countPeopleCheckInMonthByMajor(month, year, major);
        return dayOfMonth.stream()
                .map(day -> {
                    Optional<CountPerDate> checkIn = checkInCounts.stream()
                            .filter(checkInCount -> checkInCount.getDateTime().toLocalDate().equals(day))
                            .findFirst();
                    return checkIn.orElse(CountPerDate.builder()
                            .dateTime(day.atStartOfDay())
                            .count(0L)
                            .build());
                })
                .collect(Collectors.toList());
    }

    public List<CountPerDate> countPenalizeInMonthNotPresent(Integer month, Integer year) {
        List<LocalDate> dayOfMonth = getDayOfMonth(month, year);
        List<CountPerDate> checkInCounts = StatisticsDao.getInstance().countPenalizeInMonthNotPresent(month, year);
        return dayOfMonth.stream()
                .map(day -> {
                    Optional<CountPerDate> checkIn = checkInCounts.stream()
                            .filter(checkInCount -> checkInCount.getDateTime().toLocalDate().equals(day))
                            .findFirst();
                    return checkIn.orElse(CountPerDate.builder()
                            .dateTime(day.atStartOfDay())
                            .count(0L)
                            .build());
                })
                .collect(Collectors.toList());
    }

    public List<CountPerDate> countPenalizeInMonthPresent(Integer month, Integer year) {
        List<LocalDate> dayOfMonth = getDayOfMonth(month, year);
        List<CountPerDate> checkInCounts = StatisticsDao.getInstance().countPenalizeInMonthPresent(month, year);
        return dayOfMonth.stream()
                .map(day -> {
                    Optional<CountPerDate> checkIn = checkInCounts.stream()
                            .filter(checkInCount -> checkInCount.getDateTime().toLocalDate().equals(day))
                            .findFirst();
                    return checkIn.orElse(CountPerDate.builder()
                            .dateTime(day.atStartOfDay())
                            .count(0L)
                            .build());
                })
                .collect(Collectors.toList());
    }

    public List<DeviceBorrowingStatByTime> countDevicesBorrowedInMonth(Integer month, Integer year) {
        return StatisticsDao.getInstance().countDevicesBorrowedInMonth(month, year);
    }

    public List<DeviceBorrowingStatByTime> countDevicesBorrowedInMonthNotReturn(Integer month, Integer year) {
        return StatisticsDao.getInstance().countDevicesBorrowedInMonthNotReturn(month, year);
    }

    public List<DeviceBorrowingStatByName> countDevicesBorrowedInMonthById(Integer month, Integer year, String idString) {
        DeviceEntity device = DeviceService.getInstance().getById(idString);
        if (device == null) {
            return new ArrayList<>();
        }
        List<LocalDate> dayOfMonth = getDayOfMonth(month, year);
        List<DeviceBorrowingStatByName> data = StatisticsDao.getInstance().countDevicesBorrowedInMonthById(month, year, Long.parseLong(idString));
        return dayOfMonth.stream()
                .map(day -> {
                    Optional<DeviceBorrowingStatByName> result = data.stream()
                            .filter(item -> item.getDate().toLocalDate().equals(day))
                            .findFirst();
                    return result.orElse(DeviceBorrowingStatByName.builder()
                            .deviceId(data.get(0).getDeviceId())
                            .deviceName(data.get(0).getDeviceName())
                            .date(day.atStartOfDay())
                            .timesBorrowed(0L)
                            .build());
                }).collect(Collectors.toList());
    }

    public Integer totalPenalize(Integer year, Integer month) {
        List<PenalizeEntity> penalizeEntities = PenalizeDao.getInstance().findAll();
        return penalizeEntities.stream().filter(item ->
                        item.getPayment() != null
                        && item.getDate().getMonth().getValue() == month
                        && item.getDate().getYear() == year
                )
                .mapToInt(PenalizeEntity::getPayment)
                .sum();
    }

    private static class StatisticsServiceHolder {
        private static final StatisticsService INSTANCE = new StatisticsService();
    }
}
