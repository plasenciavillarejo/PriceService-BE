package com.price.be.infrastructure.web.response.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceResponseDto implements Serializable {

  private Long idPrice;
  
  private Long productId;
  
  private Long priceList;
  
  private LocalDateTime startDate;
  
  private LocalDateTime endDate;

  private BigDecimal price;
  
  private Long brandId;
  
  private static final long serialVersionUID = -5813427935089123976L;
  
}
