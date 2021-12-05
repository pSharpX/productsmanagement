/* (C)2021 */
package com.tekton.productsmanagement.catalog.repository;

import java.util.Optional;

import com.tekton.productsmanagement.catalog.model.entity.Category;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Cacheable(value = "category-detail", key = "#code", unless="#result == null")
	@Query("select c from Category c where c.code = :code")
	Optional<Category> findByCode(String code);

}
