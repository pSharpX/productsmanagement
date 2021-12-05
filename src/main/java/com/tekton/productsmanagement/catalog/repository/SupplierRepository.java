/* (C)2021 */
package com.tekton.productsmanagement.catalog.repository;

import java.util.Optional;

import com.tekton.productsmanagement.catalog.model.entity.Supplier;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

	@Cacheable(value = "supplier-detail", key = "#code", unless="#result == null")
	@Query("select s from Supplier s where s.code = :code")
	Optional<Supplier> findByCode(String code);

}
