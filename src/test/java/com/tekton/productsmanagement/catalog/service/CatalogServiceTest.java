/* (C)2021 */
package com.tekton.productsmanagement.catalog.service;

import java.util.Optional;

import com.tekton.productsmanagement.catalog.model.endpoint.ProductDetailResponse;
import com.tekton.productsmanagement.catalog.model.entity.Category;
import com.tekton.productsmanagement.catalog.model.entity.Product;
import com.tekton.productsmanagement.catalog.model.entity.Supplier;
import com.tekton.productsmanagement.catalog.repository.CategoryRepository;
import com.tekton.productsmanagement.catalog.repository.ProductRepository;
import com.tekton.productsmanagement.catalog.repository.SupplierRepository;
import com.tekton.productsmanagement.integration.client.ThirdPartyApiClient;
import com.tekton.productsmanagement.integration.config.ThirdPartyTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Import({ThirdPartyTestConfig.class})
public class CatalogServiceTest {

	@Autowired
	private RestTemplate restTemplate;

	private MockRestServiceServer server;

	@SpyBean
	private ThirdPartyApiClient client;

	@MockBean
	private CategoryRepository categoryRepository;

	@MockBean
	private SupplierRepository supplierRepository;

	@MockBean
	private ProductRepository productRepository;

	@SpyBean
	private ProductDetailStoreService storeService;

	@SpyBean
	private ProductDetailThirdPartyService thirdPartyService;

	@SpyBean
	private CreateProductDetailService createProductDetailService;

	private GetProductDetailService productDetailService;

	private CatalogService catalogService;

	@BeforeEach
	public void setup() {
		this.server = MockRestServiceServer.createServer(this.restTemplate);

		ClientHttpRequestFactory requestFactory = (ClientHttpRequestFactory) ReflectionTestUtils.getField(this.restTemplate, "requestFactory");
		this.restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(requestFactory));

		this.productDetailService = spy(new GetProductDetailService(this.storeService, this.thirdPartyService, this.createProductDetailService));
		this.catalogService = new CatalogService(this.categoryRepository, this.supplierRepository,
				this.productRepository, this.productDetailService);
	}

	@Test
	public void shouldReturnProductDetail() {
		given(this.productRepository.findById(any())).willReturn(this.mockProduct());
		this.server.expect(requestTo(containsString("/v3/70361acb-b94d-4fd6-ab7e-1da5df560768")))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withSuccess(
						new ClassPathResource("/integration/get_product_detail_stock_ok.json"),
						MediaType.APPLICATION_JSON));

		Long productId = 1L;
		ProductDetailResponse response = this.catalogService.findProductById(productId);

		verify(this.productRepository).findById(any());
		verify(this.storeService).process(any());
		verify(this.thirdPartyService).process(any());
		verify(this.createProductDetailService).process(any());
		verify(this.client).getProductStockDetail(any());

		assertSoftly(softly -> {
			softly.assertThat(response.getName()).isNotNull();
			softly.assertThat(response.getShortName()).isNotNull();
			softly.assertThat(response.getDescription()).isNotNull();
			softly.assertThat(response.getUnitPrice()).isNotNull();
			softly.assertThat(response.getUnitsInStock()).isNotNull();
		});
	}

	private Optional<Product> mockProduct(){
		return Optional.of(Product
				.builder()
						.name("Product 01")
						.shortName("Product 01 ShortName")
						.description(("Product 01 Description"))
						.category(new Category())
						.supplier(new Supplier())
				.build());
	}

}
