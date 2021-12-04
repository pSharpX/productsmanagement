/* (C)2021 */
package com.tekton.productsmanagement.catalog.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tekton.productsmanagement.common.model.contants.Currency;
import com.tekton.productsmanagement.common.model.converter.CurrencyConverter;
import lombok.Data;

@Data
@Entity
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	private String shortName;

	private String description;

	@Column(nullable = false)
	private BigDecimal unitPrice;

	@Convert(converter = CurrencyConverter.class)
	private Currency currency;

	private Long unitsInStock;

	private boolean discontinued;

	@ManyToOne @JoinColumn private Category category;

	@ManyToOne @JoinColumn private Supplier supplier;
}
