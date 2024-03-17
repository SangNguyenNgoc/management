package com.example.markethibernate.bll.mappers;

import com.example.markethibernate.bll.dtos.CategoryResponseDto;
import com.example.markethibernate.dal.dao.CategoryDao;
import com.example.markethibernate.dal.entities.Category;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CategoryMapperTest {

    @Test
    void toEntity() {
    }

    @Test
    void toResponse() {
        Category category = CategoryDao.getInstance().findById(1);
        CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
        CategoryResponseDto categoryResponseDto = categoryMapper.toResponse(category);
        System.out.println(categoryResponseDto.toString());
    }
}