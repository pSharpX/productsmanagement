/* (C)2021 */
package com.tekton.productsmanagement.integration.service;

import com.tekton.productsmanagement.integration.client.ThirdPartyApiClient;
import com.tekton.productsmanagement.integration.config.ThirdPartyApiClientProperties;
import com.tekton.productsmanagement.integration.config.ThirdPartyTestConfig;
import com.tekton.productsmanagement.integration.exception.ThirdPartyApiException;
import com.tekton.productsmanagement.integration.model.endpoint.ProductStockRequest;
import com.tekton.productsmanagement.integration.model.endpoint.ProductStockResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Import({ThirdPartyTestConfig.class})
public class ThirdPartyApiClientTest {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ThirdPartyApiClientProperties properties;

	private MockRestServiceServer server;

	private ThirdPartyApiClient client;

	@BeforeEach
	public void setup() {
		this.client = Mockito.spy(new ThirdPartyApiClient(this.properties, this.restTemplate));
		this.server = MockRestServiceServer.createServer(this.restTemplate);

		ClientHttpRequestFactory requestFactory = (ClientHttpRequestFactory) ReflectionTestUtils.getField(this.restTemplate, "requestFactory");
		this.restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(requestFactory));
	}

	@Test
	public void shouldReturnProductStockResponse() {
		this.server.expect(requestTo(containsString("/v3/70361acb-b94d-4fd6-ab7e-1da5df560768")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(
						new ClassPathResource("/integration/get_product_detail_stock_ok.json"),
						MediaType.APPLICATION_JSON));

		ProductStockRequest request = new ProductStockRequest(2L);
		ProductStockResponse response = this.client.getProductStockDetail(request);

		assertSoftly(softly -> {
			softly.assertThat(response.getUnitPrice()).isNotNull();
			softly.assertThat(response.getUnitPrice()).isNotNull();
			softly.assertThat(response.isDiscontinued()).isFalse();
		});
	}

	@Test
	public void shouldThrowException_whenThirdPartyServerError() {
		this.server.expect(requestTo(containsString("/v3/70361acb-b94d-4fd6-ab7e-1da5df560768")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withServerError().body(new ClassPathResource("/integration/get_product_detail_stock_ok.json"))
								.contentType(MediaType.APPLICATION_JSON));

		ProductStockRequest request = new ProductStockRequest(2L);

		assertThrows(ThirdPartyApiException.class, () -> this.client.getProductStockDetail(request));
	}

	@Test
	public void shouldThrowException_whenThirdPartyBadRequest() {
		this.server.expect(requestTo(containsString("/v3/70361acb-b94d-4fd6-ab7e-1da5df560768")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withBadRequest().body(new ClassPathResource("/integration/get_product_detail_stock_400.json"))
						.contentType(MediaType.APPLICATION_JSON));

		ProductStockRequest request = new ProductStockRequest();

		ThirdPartyApiException exception = assertThrows(ThirdPartyApiException.class, () -> this.client.getProductStockDetail(request));
		assertSoftly(softly -> {
			softly.assertThat(exception.getMessage()).isNotNull();
		});
	}

}
