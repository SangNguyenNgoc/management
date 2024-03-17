package com.example.markethibernate.bll.dtos;

import com.example.markethibernate.dal.entities.Category;
import com.example.markethibernate.dal.entities.Vegetable;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Category}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDto implements Serializable {
    Integer id;
    String name;
    String description;
    List<VegetableDto> vegetables;

    /**
     * DTO for {@link Vegetable}
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class VegetableDto implements Serializable {
        Integer id;
        String name;
        String unit;
        Integer amount;
        String image;
        Float price;
    }
}