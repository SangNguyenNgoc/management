package com.example.markethibernate.dal;

import com.example.markethibernate.bll.dtos.CreateCategoryDto;
import com.example.markethibernate.bll.mappers.CategoryMapper;
import com.example.markethibernate.dal.dao.CategoryDao;
import com.example.markethibernate.dal.entities.Category;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

class CategoryDalTest {

    @Test
    void findAll() {
        List<Category> categories = CategoryDao.getInstance().findAll();
        categories.forEach(item -> System.out.println(item.toString()));
    }

    @Test
    void findById() {
        Category category = CategoryDao.getInstance().findById(1);
        System.out.println(category.toString());
    }

    @Test
    void create() {
        CreateCategoryDto createCategoryDto = CreateCategoryDto.builder()
                .name("Herbs")
                .description("Herbs are plants used for flavoring, medicine, or fragrance.")
                .build();

        CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
        Category category = categoryMapper.toEntity(createCategoryDto);

        System.out.println(category);
        Category result = CategoryDao.getInstance().save(category);
        System.out.println(result);
    }
}