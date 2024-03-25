package com.example.markethibernate.bll.mappers;

import com.example.markethibernate.bll.dtos.CreateDeviceDto;
import com.example.markethibernate.dal.entities.DeviceEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface DeviceMapper {
    DeviceEntity toEntity(CreateDeviceDto createDeviceDto);

    CreateDeviceDto toDto(DeviceEntity deviceEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DeviceEntity partialUpdate(CreateDeviceDto createDeviceDto, @MappingTarget DeviceEntity deviceEntity);
}
