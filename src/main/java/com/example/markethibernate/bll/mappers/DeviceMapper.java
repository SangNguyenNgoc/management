package com.example.markethibernate.bll.mappers;

import com.example.markethibernate.bll.dtos.DeviceValidator;
import com.example.markethibernate.dal.entities.DeviceEntity;
import com.example.markethibernate.dal.entities.PenalizeEntity;
import org.mapstruct.Mapper;

@Mapper
public interface DeviceMapper {

    DeviceEntity toEntity(DeviceValidator deviceValidator);



}
