package com.price.be.infrastructure.web.controller;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.price.be.application.port.in.PriceService;
import com.price.be.infrastructure.web.convert.mapper.PriceResponseDtoConvertMapper;
import com.price.be.infrastructure.web.response.dto.PriceResponseDto;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value = "/prices")
public class PriceController {

  private final PriceService priceService;
  
  public PriceController(PriceService priceService) {
    this.priceService = priceService;
  }
  
  @GetMapping
  public Mono<ResponseEntity<PriceResponseDto>> getPrice(
      @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date,
      @RequestParam("productId") Long productId, @RequestParam("brandId") Long brandId) {
    return priceService.getListPriceModel(date, productId, brandId)
        .map(response -> new ResponseEntity<>(
        PriceResponseDtoConvertMapper.mapper.convertPriceModelToResponseDto(response), HttpStatus.OK))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

}
