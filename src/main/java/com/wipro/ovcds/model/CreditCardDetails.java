package com.wipro.ovcds.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="creditcarddetails")
public class CreditCardDetails {
	
	@Id
	private int creditCardNumber;
	private Date ValidityFrom;
	private Date ValidityTo;
	private double Balance; 
}
