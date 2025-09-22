package de.rieckpil;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;


class PostClientTest {

  @RegisterExtension
  static WireMockExtension mockServer = WireMockExtension
    .newInstance()
    .options(wireMockConfig().dynamicPort())
    .build();

  private PostClient unit = new PostClient(WebClient.builder().baseUrl(mockServer.baseUrl()).build());

  @Test
  void shouldReturnAllPosts() throws Exception {

    mockServer.stubFor(
      WireMock.get(urlPathEqualTo("/posts"))
        .withQueryParam("limit", WireMock.equalTo("30"))
        .withQueryParam("skip", WireMock.equalTo("0"))
        .willReturn(
          aResponse()
            .withBodyFile("dummyjson/get-all-posts-page-one.json")
            .withHeader("Content-Type", "application/json")
        )
    );

    mockServer.stubFor(
      WireMock.get(urlPathEqualTo("/posts"))
        .withQueryParam("limit", WireMock.equalTo("30"))
        .withQueryParam("skip", WireMock.equalTo("30"))
        .willReturn(
          aResponse()
            .withBodyFile("dummyjson/get-all-posts-page-two.json")
            .withHeader("Content-Type", "application/json")
        )
    );

      mockServer.stubFor(
        WireMock.get(urlPathEqualTo("/posts"))
          .withQueryParam("limit", WireMock.equalTo("30"))
          .withQueryParam("skip", WireMock.equalTo("60"))
          .willReturn(
            aResponse()
              .withBodyFile("dummyjson/get-all-posts-page-final.json")
              .withHeader("Content-Type", "application/json")
          )
    );

    List<Post> result = unit.fetchAllPosts();
    assertThat(result).hasSize(90);


  }
}
