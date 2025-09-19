package com.price.be.application.port.service.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.price.be.application.port.in.PriceService;
import com.price.be.application.port.out.PriceRepositoryPort;
import com.price.be.domain.exception.NotFoundException;
import com.price.be.domain.model.PriceModel;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class PriceServiceImpl implements PriceService {

  private final PriceRepositoryPort priceRepositoryPort;

  private static final Logger LOGGER = LoggerFactory.getLogger(PriceServiceImpl.class);

  public PriceServiceImpl(PriceRepositoryPort priceRepositoryPort) {
    this.priceRepositoryPort = priceRepositoryPort;
  }

  @Override
  public Mono<PriceModel> getListPriceModel(LocalDateTime date, Long productId, Long brandId) {
    LOGGER.info("Receiving request with date: {}, productId: {} and brandId: {} ", date, productId, brandId);
    return Mono.justOrEmpty(priceRepositoryPort.getFilterByDateAndProductIdAndBandId(date, productId, brandId).stream()
        .sorted(Comparator.comparing(PriceModel::getPriority).reversed())
        .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
          if (list.size() > 1) {
            LOGGER.warn("Found {} prices for date={}, productID={}, brandID={}. It is ordered by priority.",
                list.size(), date, productId, brandId);
          }
          return list;
        })).stream().findFirst().orElse(null))
        .switchIfEmpty(Mono.error(new NotFoundException(
            "No price was found associated with the entered parameters. Please try again with different values.")))
        .subscribeOn(Schedulers.boundedElastic());

  }

}
