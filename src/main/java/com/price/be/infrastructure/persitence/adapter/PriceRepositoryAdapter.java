package com.price.be.infrastructure.persitence.adapter;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.price.be.application.port.out.PriceRepositoryPort;
import com.price.be.domain.model.PriceModel;
import com.price.be.infrastructure.persitence.convert.mapper.PriceConvertMapper;
import com.price.be.infrastructure.persitence.dao.PriceDao;

@Service
public class PriceRepositoryAdapter implements PriceRepositoryPort {

  private final PriceDao priceDao;

  public PriceRepositoryAdapter(PriceDao priceDao) {
    this.priceDao = priceDao;
  }

  @Override
  @Transactional(readOnly = true)
  public List<PriceModel> getFilterByDateAndProductIdAndBandId(LocalDateTime date, Long productId,
      Long brandId) {
    return PriceConvertMapper.mapper.convertListPriceEntityToListModel(
        priceDao.getFilterByDateAndProductIdAndBandId(date, productId, brandId));
  }

}
