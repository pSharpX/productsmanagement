/* (C)2021 */
package com.tekton.productsmanagement.catalog.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Category implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String shortDescription;

	private String description;

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<Product> products;
}
