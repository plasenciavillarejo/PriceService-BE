package com.price.be;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.price.be.application.port.in.PriceService;
import com.price.be.domain.model.PriceModel;
import com.price.be.infrastructure.web.controller.PriceController;

import reactor.core.publisher.Mono;

@WebFluxTest(controllers = PriceController.class)
class PriceControllerWebTest {

  private WebTestClient webTestClient;

  @MockitoBean
  private PriceService priceService;

  private static final Long PRODUCT_ID = 35455L;
  private static final Long BRAND_ID = 1L;
  private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @BeforeEach
  void setUp() {
    PriceController controller = new PriceController(priceService);
    webTestClient = WebTestClient.bindToController(controller).build();
  }

  @Test
  void testRequest_1_14_10h() {
    LocalDateTime date = LocalDateTime.parse("2020-06-14 10:00:00", FMT);

    Mockito.when(priceService.getListPriceModel(date, PRODUCT_ID, BRAND_ID))
        .thenReturn(Mono.just(PriceModel.builder().brandId(BRAND_ID).productId(PRODUCT_ID).priceList(1L)
            .price(BigDecimal.valueOf(35.50))
            .startDate(LocalDateTime.parse("2020-06-14 00:00:00", FMT))
            .endDate(LocalDateTime.parse("2020-12-31 23:59:59", FMT))
            .build()));

    webTestClient.get()
        .uri(uriBuilder -> uriBuilder.path("/prices").queryParam("date", date.format(FMT))
            .queryParam("productId", PRODUCT_ID).queryParam("brandId", BRAND_ID).build())
        .exchange().expectStatus().isOk().expectBody().jsonPath("$.productId").isEqualTo(PRODUCT_ID.intValue())
        .jsonPath("$.brandId").isEqualTo(BRAND_ID.intValue()).jsonPath("$.price").isEqualTo(35.50)
        .jsonPath("$.priceList").isEqualTo(1);
  }

  @Test
  void testRequest_2_16_00h() {
    LocalDateTime date = LocalDateTime.parse("2020-06-14 16:00:00", FMT);

    Mockito.when(priceService.getListPriceModel(date, PRODUCT_ID, BRAND_ID))
        .thenReturn(Mono.just(PriceModel.builder().brandId(BRAND_ID).productId(PRODUCT_ID).priceList(2L)
            .price(BigDecimal.valueOf(25.45))
            .startDate(LocalDateTime.parse("2020-06-14 15:00:00", FMT))
            .endDate(LocalDateTime.parse("2020-06-14 18:30:00", FMT)
            ).build()));

    webTestClient.get()
        .uri(uriBuilder -> uriBuilder.path("/prices").queryParam("date", date.format(FMT))
            .queryParam("productId", PRODUCT_ID).queryParam("brandId", BRAND_ID).build())
        .exchange().expectStatus().isOk().expectBody().jsonPath("$.productId").isEqualTo(PRODUCT_ID.intValue())
        .jsonPath("$.brandId").isEqualTo(BRAND_ID.intValue()).jsonPath("$.price").isEqualTo(25.45)
        .jsonPath("$.priceList").isEqualTo(2);
  }

  @Test
  void testRequest_3_21_00h() {
    LocalDateTime date = LocalDateTime.parse("2020-06-14 21:00:00", FMT);

    Mockito.when(priceService.getListPriceModel(date, PRODUCT_ID, BRAND_ID))
        .thenReturn(Mono.just(PriceModel.builder().brandId(BRAND_ID).productId(PRODUCT_ID).priceList(1L)
            .price(BigDecimal.valueOf(35.50))
            .startDate(LocalDateTime.parse("2020-06-14 00:00:00", FMT))
            .endDate(LocalDateTime.parse("2020-12-31 23:59:59", FMT))
            .build()));

    webTestClient.get()
        .uri(uriBuilder -> uriBuilder.path("/prices").queryParam("date", date.format(FMT))
            .queryParam("productId", PRODUCT_ID).queryParam("brandId", BRAND_ID).build())
        .exchange().expectStatus().isOk().expectBody().jsonPath("$.productId").isEqualTo(PRODUCT_ID.intValue())
        .jsonPath("$.brandId").isEqualTo(BRAND_ID.intValue()).jsonPath("$.price").isEqualTo(35.50)
        .jsonPath("$.priceList").isEqualTo(1);
  }
  
  
  @Test
  void testRequest_4_10_00h() {
    LocalDateTime date = LocalDateTime.parse("2020-06-15 10:00:00", FMT);

    Mockito.when(priceService.getListPriceModel(date, PRODUCT_ID, BRAND_ID))
        .thenReturn(Mono.just(PriceModel.builder().brandId(BRAND_ID).productId(PRODUCT_ID).priceList(3L)
            .price(BigDecimal.valueOf(30.5))
            .startDate(LocalDateTime.parse("2020-06-15 00:00:00", FMT))
            .endDate(LocalDateTime.parse("2020-06-15 11:00:00", FMT))
            .build()));

    webTestClient.get()
        .uri(uriBuilder -> uriBuilder.path("/prices").queryParam("date", date.format(FMT))
            .queryParam("productId", PRODUCT_ID).queryParam("brandId", BRAND_ID).build())
        .exchange().expectStatus().isOk().expectBody().jsonPath("$.productId").isEqualTo(PRODUCT_ID.intValue())
        .jsonPath("$.brandId").isEqualTo(BRAND_ID.intValue()).jsonPath("$.price").isEqualTo(30.5)
        .jsonPath("$.priceList").isEqualTo(3);
  }
  
  @Test
  void testRequest_5_21_00h() {
    LocalDateTime date = LocalDateTime.parse("2020-06-16 21:00:00", FMT);

    Mockito.when(priceService.getListPriceModel(date, PRODUCT_ID, BRAND_ID))
        .thenReturn(Mono.just(PriceModel.builder().brandId(BRAND_ID).productId(PRODUCT_ID).priceList(4L)
            .price(BigDecimal.valueOf(38.95))
            .startDate(LocalDateTime.parse("2020-06-15 16:00:00", FMT))
            .endDate(LocalDateTime.parse("2020-12-31 23:59:59", FMT))
            .build()));

    webTestClient.get()
        .uri(uriBuilder -> uriBuilder.path("/prices").queryParam("date", date.format(FMT))
            .queryParam("productId", PRODUCT_ID).queryParam("brandId", BRAND_ID).build())
        .exchange().expectStatus().isOk().expectBody().jsonPath("$.productId").isEqualTo(PRODUCT_ID.intValue())
        .jsonPath("$.brandId").isEqualTo(BRAND_ID.intValue()).jsonPath("$.price").isEqualTo(38.95)
        .jsonPath("$.priceList").isEqualTo(4);
  }
  
}
