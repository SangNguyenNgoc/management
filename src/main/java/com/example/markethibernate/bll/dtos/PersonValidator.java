package com.example.markethibernate.bll.dtos;

import com.example.markethibernate.dal.entities.PersonEntity;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link PersonEntity}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonValidator implements Serializable {

    @NotBlank(message = "tên người dùng không được trống")
    @Size(message = "tên người dùng chỉ chứa tối đa 50 kí tự", min = 0, max = 50)
    String name;

    @NotBlank(message = "tên khoa không được để trống")
    @Size(message = "tên khoa chỉ chứa tối đa 50 kí tự", min = 0, max = 50)
    String department;

    @NotBlank(message = "email không được để trống")
    @Email(message = "email sai định dạng")
    String email;

    @NotBlank(message = "mật khẩu không được để trống")
    @Size(message = "mật khẩu phải chứa tối thiểu 8 kí tự và tối đa 20 kí tự", min = 8, max = 20)
    String password;

    @NotBlank(message = "tên ngành không được để trống")
    @Size(message = "tên ngành chỉ chứa tối đa 50 kí tự", min = 0, max = 50)
    String profession;

    @NotBlank(message = "số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9]{10}$", message = "số điện thoại không đúng định dạng")
    String phoneNumber;
}