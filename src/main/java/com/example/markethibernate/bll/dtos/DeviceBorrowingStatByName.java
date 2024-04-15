package com.example.markethibernate.bll.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceBorrowingStatByName {
    private Long deviceId;
    private String deviceName;
    private Long timesBorrowed;
    private LocalDateTime date;
}
