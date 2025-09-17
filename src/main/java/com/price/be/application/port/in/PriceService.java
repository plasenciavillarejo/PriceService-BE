package com.price.be.application.port.in;

import java.time.LocalDateTime;

import com.price.be.domain.model.PriceModel;

import reactor.core.publisher.Mono;

public interface PriceService {

  public Mono<PriceModel> getListPriceModel(LocalDateTime date, Long productId, Long brandId);

}
