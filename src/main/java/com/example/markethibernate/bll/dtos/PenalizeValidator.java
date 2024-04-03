package com.example.markethibernate.bll.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.markethibernate.dal.entities.PenalizeEntity}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PenalizeValidator implements Serializable {

    @NotBlank(message = "hình thức phạt không được để trống")
    @Size(message = "hình thức phạt chỉ được tối đa 50 kí tự",min = 0, max = 100)
    String type;
    @NotNull(message = "số tiền phạt không được để trống")
    Integer payment;
    @NotNull(message = "ngày tháng không đuược để trống")
    LocalDateTime date;
}