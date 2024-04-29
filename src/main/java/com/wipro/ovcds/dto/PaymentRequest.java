package com.wipro.ovcds.dto;

import com.wipro.ovcds.model.PaymentInfo;
import com.wipro.ovcds.model.ShippingDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
	private ShippingDetails sd;
	private PaymentInfo paymentInfo;
}
