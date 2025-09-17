package com.price.be.infrastructure.web.convert.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.price.be.domain.model.PriceModel;
import com.price.be.infrastructure.web.response.dto.PriceResponseDto;

@Mapper
public interface PriceResponseDtoConvertMapper {

  public PriceResponseDtoConvertMapper mapper = Mappers.getMapper(PriceResponseDtoConvertMapper.class);
  
  public PriceResponseDto convertPriceModelToResponseDto(PriceModel listPriceModel);
  
}
