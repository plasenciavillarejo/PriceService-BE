package com.price.be.infrastructure.persitence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRICES")
public class PriceEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_PRICE")
  private Long idPrice;
  
  @Column(name = "PRODUCT_ID")
  private Long productId;

  @Column(name = "PRICE_LIST")
  private Long priceList;

  @Column(name = "START_DATE")
  private LocalDateTime startDate;

  @Column(name = "END_DATE")
  private LocalDateTime endDate;

  @Column(name = "PRIORITY")
  private Long priority;
  
  @Column(name = "PRICE")
  private BigDecimal price;

  @Column(name = "CURR")
  private String isoCode;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "BRAND_ID", nullable = false)
  private Brands brand;
  
  private static final long serialVersionUID = 542076097368359143L;

}
