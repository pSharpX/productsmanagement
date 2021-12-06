/* (C)2021 */
package com.tekton.productsmanagement.catalog.resource;


import com.tekton.productsmanagement.config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CatalogController.class)
@AutoConfigureDataJpa
@AutoConfigureWebClient
@Import(TestConfig.class)
@Sql(scripts = {"classpath:catalog/data.sql"}, config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@TestPropertySource(locations = {"classpath:application.properties"})
public class CatalogControllerIntegrationTest {

	@Autowired
	private RestTemplate restTemplate;

	private MockRestServiceServer server;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		this.server = MockRestServiceServer.createServer(this.restTemplate);

		ClientHttpRequestFactory requestFactory = (ClientHttpRequestFactory) ReflectionTestUtils.getField(this.restTemplate, "requestFactory");
		this.restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(requestFactory));
	}

	@Test
	public void givenProductId_whenGetProductId_thenReturnProductDetail() throws Exception {
		this.server.expect(requestTo(containsString("/v3/70361acb-b94d-4fd6-ab7e-1da5df560768")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(
						new ClassPathResource("/integration/get_product_detail_stock_ok.json"),
						MediaType.APPLICATION_JSON));

		Long productId = 2L;

		this.mvc.perform(get("/catalog/{id}", productId)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", not(empty())))
				.andExpect(jsonPath("$.short_name", not(empty())))
				.andExpect(jsonPath("$.description", not(empty())))
				.andExpect(jsonPath("$.unit_price", not(empty())))
				.andExpect(jsonPath("$.currency", not(empty())))
				.andExpect(jsonPath("$.units_in_stock", not(empty())))
				.andExpect(jsonPath("$.discontinued", not(empty())))
				.andExpect(jsonPath("$.category_code", not(empty())))
				.andExpect(jsonPath("$.supplier_code", not(empty())));
	}

}
