package com.example.markethibernate.bll.dtos;

import com.example.markethibernate.dal.entities.DeviceEntity;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link DeviceEntity}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeviceDto implements Serializable {
    private String name;
    private String description;
}