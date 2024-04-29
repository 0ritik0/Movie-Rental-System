package com.wipro.ovcds.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table (name = "ShippingDetails")
public class ShippingDetails {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull(message="Name may not be null")
	private String FirstName;
	private String LastName;
	@NotNull(message="Address may not be null")
	private String AddressLine1;
	private String AddressLine2;
	@NotNull(message="PostCode may not be null")
	private int PostCode;
	private String Town;
	private String Phone;
	private String Email;
	private String AdditionalInfo;
}
