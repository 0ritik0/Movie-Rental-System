package com.wipro.ovcds.dto;

import lombok.Data;


@Data
public class ProductDTO {
	
	private Long id;
	private String name;
	private int categoryId;
	private int storeDetailsId;
	private double price;
	private int quantity; 
	private double rating;
	private String description;
	private String imageName;

}
