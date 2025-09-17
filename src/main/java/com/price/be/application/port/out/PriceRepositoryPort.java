package com.price.be.application.port.out;

import java.time.LocalDateTime;
import java.util.List;

import com.price.be.domain.model.PriceModel;

public interface PriceRepositoryPort {

  public List<PriceModel> getFilterByDateAndProductIdAndBandId(LocalDateTime date, Long productId, Long brandId);

}
