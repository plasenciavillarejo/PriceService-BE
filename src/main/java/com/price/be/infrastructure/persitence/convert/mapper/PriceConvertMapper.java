package com.price.be.infrastructure.persitence.convert.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.price.be.domain.model.PriceModel;
import com.price.be.infrastructure.persitence.entity.PriceEntity;

@Mapper
public interface PriceConvertMapper {

  public PriceConvertMapper mapper = Mappers.getMapper(PriceConvertMapper.class);

  @Mapping(target = "brandId", source = "brand.idBrand")
  public PriceModel convertPriceEntityToModel(PriceEntity priceEntity);

  public List<PriceModel> convertListPriceEntityToListModel(List<PriceEntity> listPriceEntity);
  
}
