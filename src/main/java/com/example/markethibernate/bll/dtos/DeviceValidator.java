package com.example.markethibernate.bll.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.example.markethibernate.dal.entities.DeviceEntity}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceValidator implements Serializable {

    @NotBlank(message = "tên người dùng không được trống")
    @Size(message = "tên thiết bị chỉ chứa tối đa 50 kí tự", min = 0, max = 50)
    String name;

    @NotBlank(message = "mô tả thiết bị không được trống")
    @Size(message = "tên thiết bị chỉ chứa tối đa 500 kí tự", min = 0, max = 500)
    String description;
}