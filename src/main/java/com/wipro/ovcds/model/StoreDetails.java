package com.wipro.ovcds.model;


import javax.persistence.*;

import lombok.Data;



@Entity
@Data

public class StoreDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="storeDetails_id")
	private int id;
	
	private String name;
	private String locality;
	private String place;
	private String state;
	private String contact;
}