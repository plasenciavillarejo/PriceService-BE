package com.price.be.infrastructure.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceControllerWebTestIntegration {

  @LocalServerPort
  int port;

  private static final Long PRODUCT_ID = 35455L;
  private static final Long BRAND_ID = 1L;
  
  private WebTestClient webTestClient;

  @BeforeEach
  void setUp() {
      webTestClient = WebTestClient.bindToServer()
          .baseUrl("http://localhost:" + port+ "/back")
          .build();
  }

  @Test
  void testRequest_1_14_10h() {
      webTestClient.get()
          .uri(uriBuilder -> uriBuilder
              .path("/prices")
              .queryParam("date", "2020-06-14 10:00:00")
              .queryParam("productId", 35455)
              .queryParam("brandId", 1)
              .build())
          .exchange()
          .expectStatus().isOk()
          .expectBody().jsonPath("$.productId").isEqualTo(PRODUCT_ID.intValue())
          .jsonPath("$.brandId").isEqualTo(BRAND_ID.intValue()).jsonPath("$.price").isEqualTo(35.50)
          .jsonPath("$.priceList").isEqualTo(1);;
  }

  @Test
  void testRequest_2_16_00h() {
      webTestClient.get()
          .uri(uriBuilder -> uriBuilder
              .path("/prices")
              .queryParam("date", "2020-06-14 16:00:00")
              .queryParam("productId", 35455)
              .queryParam("brandId", 1)
              .build())
          .exchange()
          .expectStatus().isOk()
          .expectBody()
          .jsonPath("$.productId").isEqualTo(PRODUCT_ID.intValue())
          .jsonPath("$.brandId").isEqualTo(BRAND_ID.intValue()).jsonPath("$.price").isEqualTo(25.45)
          .jsonPath("$.priceList").isEqualTo(2);
  }

  @Test
  void testRequest_3_21_00h() {
      webTestClient.get()
          .uri(uriBuilder -> uriBuilder
              .path("/prices")
              .queryParam("date", "2020-06-14 21:00:00")
              .queryParam("productId", 35455)
              .queryParam("brandId", 1)
              .build())
          .exchange()
          .expectStatus().isOk()
          .expectBody()
          .jsonPath("$.productId").isEqualTo(PRODUCT_ID.intValue())
          .jsonPath("$.brandId").isEqualTo(BRAND_ID.intValue()).jsonPath("$.price").isEqualTo(35.50)
          .jsonPath("$.priceList").isEqualTo(1);
  }

  @Test
  void testRequest_4_15_10h() {
      webTestClient.get()
          .uri(uriBuilder -> uriBuilder
              .path("/prices")
              .queryParam("date", "2020-06-15 10:00:00")
              .queryParam("productId", 35455)
              .queryParam("brandId", 1)
              .build())
          .exchange()
          .expectStatus().isOk()
          .expectBody()
          .jsonPath("$.productId").isEqualTo(PRODUCT_ID.intValue())
          .jsonPath("$.brandId").isEqualTo(BRAND_ID.intValue()).jsonPath("$.price").isEqualTo(30.5)
          .jsonPath("$.priceList").isEqualTo(3);
  }

  @Test
  void testRequest_5_16_21h() {
      webTestClient.get()
          .uri(uriBuilder -> uriBuilder
              .path("/prices")
              .queryParam("date", "2020-06-16 21:00:00")
              .queryParam("productId", 35455)
              .queryParam("brandId", 1)
              .build())
          .exchange()
          .expectStatus().isOk()
          .expectBody()
          .jsonPath("$.productId").isEqualTo(PRODUCT_ID.intValue())
          .jsonPath("$.brandId").isEqualTo(BRAND_ID.intValue()).jsonPath("$.price").isEqualTo(38.95)
          .jsonPath("$.priceList").isEqualTo(4);
  }
}
