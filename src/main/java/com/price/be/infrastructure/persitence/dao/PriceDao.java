package com.price.be.infrastructure.persitence.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.price.be.infrastructure.persitence.entity.PriceEntity;

public interface PriceDao extends JpaRepository<PriceEntity, Long> {

  @Query("from PriceEntity pr"
      + " left join fetch pr.brand br"
      + " where (:date between pr.startDate and pr.endDate)"
      + " and pr.productId = :productId "
      + " and br.idBrand = :brandId"
      + " order by priority desc, pr.startDate desc")
  public List<PriceEntity> getFilterByDateAndProductIdAndBandId(@Param("date") LocalDateTime dateRequest,
      @Param("productId") Long productId, @Param("brandId") Long brandId );
  
}
