package com.example.markethibernate.bll.mappers;

import com.example.markethibernate.bll.dtos.CategoryResponseDto;
import com.example.markethibernate.bll.dtos.CreateCategoryDto;
import com.example.markethibernate.dal.entities.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {

    Category toEntity(CreateCategoryDto createCategory);

    CategoryResponseDto toResponse(Category category);
}
