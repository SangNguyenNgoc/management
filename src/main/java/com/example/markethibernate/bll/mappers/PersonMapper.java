package com.example.markethibernate.bll.mappers;

import com.example.markethibernate.bll.dtos.PersonValidator;
import com.example.markethibernate.dal.entities.PenalizeEntity;
import com.example.markethibernate.dal.entities.PersonEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface PersonMapper {
    PersonEntity toEntity(PersonValidator personValidator);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PersonEntity partialUpdate(PersonValidator personValidator, @MappingTarget PersonEntity personEntity);
}
