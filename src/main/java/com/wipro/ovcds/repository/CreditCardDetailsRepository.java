package com.wipro.ovcds.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.ovcds.model.CreditCardDetails;

public interface CreditCardDetailsRepository extends JpaRepository<CreditCardDetails, Integer>{
	
	
	CreditCardDetails findByCreditCardNumber(int creditcardno);

}
