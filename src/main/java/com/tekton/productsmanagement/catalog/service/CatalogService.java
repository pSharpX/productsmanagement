/* (C)2021 */
package com.tekton.productsmanagement.catalog.service;

import java.util.Optional;
import java.util.stream.Collectors;

import com.tekton.productsmanagement.catalog.model.dto.CategoryDto;
import com.tekton.productsmanagement.catalog.model.dto.SupplierDto;
import com.tekton.productsmanagement.catalog.model.endpoint.CategoriesResponse;
import com.tekton.productsmanagement.catalog.model.endpoint.CreateProductRequest;
import com.tekton.productsmanagement.catalog.model.endpoint.ProductDetailResponse;
import com.tekton.productsmanagement.catalog.model.endpoint.SuppliersResponse;
import com.tekton.productsmanagement.catalog.model.entity.Category;
import com.tekton.productsmanagement.catalog.model.entity.Product;
import com.tekton.productsmanagement.catalog.model.entity.Supplier;
import com.tekton.productsmanagement.catalog.repository.CategoryRepository;
import com.tekton.productsmanagement.catalog.repository.ProductRepository;
import com.tekton.productsmanagement.catalog.repository.SupplierRepository;
import com.tekton.productsmanagement.common.exception.ServiceException;
import com.tekton.productsmanagement.common.model.contants.Currency;
import com.tekton.productsmanagement.common.util.ErrorMessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatalogService {

	private final CategoryRepository categoryRepository;

	private final SupplierRepository supplierRepository;

	private final ProductRepository productRepository;

	private final GetProductDetailService productDetailService;

	public void create(CreateProductRequest productRequest) {
		this.productRepository.save(Product
				.builder()
						.name(productRequest.getName())
						.shortName(productRequest.getShortName())
						.description(productRequest.getDescription())
						.currency(Currency.getCurrencyById(productRequest.getCurrency()))
						.unitPrice(productRequest.getUnitPrice())
						.unitsInStock(productRequest.getUnitsInStock())
						.discontinued(productRequest.isDiscontinued())
						.category(this.categoryRepository.findByCode(productRequest.getCategoryCode())
								.orElseThrow(() -> new ServiceException(ErrorMessageUtils.CATEGORY_NOT_FOUND)))
						.supplier(this.supplierRepository.findByCode(productRequest.getSupplierCode())
								.orElseThrow(() -> new ServiceException(ErrorMessageUtils.SUPPLIER_NOT_FOUND)))
				.build());
	}

	public void update(Long productId, CreateProductRequest productRequest) {
		this.productRepository.save(Product
				.builder()
					.id(productId)
					.name(productRequest.getName())
					.shortName(productRequest.getShortName())
					.description(productRequest.getDescription())
					.currency(Currency.getCurrencyById(productRequest.getCurrency()))
					.unitPrice(productRequest.getUnitPrice())
					.unitsInStock(productRequest.getUnitsInStock())
					.discontinued(productRequest.isDiscontinued())
					.category(this.categoryRepository.findByCode(productRequest.getCategoryCode())
							.orElseThrow(() -> new ServiceException(ErrorMessageUtils.CATEGORY_NOT_FOUND)))
					.supplier(this.supplierRepository.findByCode(productRequest.getSupplierCode())
							.orElseThrow(() -> new ServiceException(ErrorMessageUtils.SUPPLIER_NOT_FOUND)))
				.build());
	}

	public ProductDetailResponse findProductById(Long productId){
		return this.productDetailService.getProductDetail(productId);
	}

	@Cacheable("category-list")
	public CategoriesResponse fetchCategories(){
		return CategoriesResponse
				.builder()
				.categories(this.categoryRepository.findAll().stream().map(CategoryDto::new).collect(
						Collectors.toList()))
				.build();
	}

	public Optional<CategoryDto> findCategoryByCode(String code){
		Optional<Category> category = this.categoryRepository.findByCode(code);
		if(category.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(new CategoryDto(category.get()));
	}

	@Cacheable("supplier-list")
	public SuppliersResponse fetchSuppliers(){
		return SuppliersResponse
				.builder()
				.suppliers(this.supplierRepository.findAll().stream().map(SupplierDto::new).collect(Collectors.toList()))
				.build();
	}

	public Optional<SupplierDto> findSupplierByCode(String code){
		Optional<Supplier> supplier = this.supplierRepository.findByCode(code);
		if(supplier.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(new SupplierDto(supplier.get()));
	}

}
