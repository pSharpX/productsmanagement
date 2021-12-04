/* (C)2021 */
package com.tekton.productsmanagement.catalog.repository;

import com.tekton.productsmanagement.catalog.model.entity.Supplier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {}
