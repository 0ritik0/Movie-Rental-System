package com.wipro.ovcds.dto;

import com.wipro.ovcds.model.ShippingDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentAcknowledgement {
	private String status;
	private double amount;
	private String receiptNo;
	private ShippingDetails sd;
}
