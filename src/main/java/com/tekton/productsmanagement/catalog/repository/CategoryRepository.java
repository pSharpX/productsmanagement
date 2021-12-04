/* (C)2021 */
package com.tekton.productsmanagement.catalog.repository;

import com.tekton.productsmanagement.catalog.model.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {}
