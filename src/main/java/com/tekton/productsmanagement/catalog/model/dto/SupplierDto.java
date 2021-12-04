/* (C)2021 */
package com.tekton.productsmanagement.catalog.model.dto;

import com.tekton.productsmanagement.catalog.model.entity.Supplier;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierDto {

	private String code;

	private String companyName;

	private String address;

	private String city;

	private String country;

	private String phone;

	public SupplierDto(Supplier supplier) {
		this.code = supplier.getCode();
		this.companyName = supplier.getCompanyName();
		this.address = supplier.getAddress();
		this.city = supplier.getCity();
		this.country = supplier.getCountry();
		this.phone = supplier.getPhone();
	}

}
