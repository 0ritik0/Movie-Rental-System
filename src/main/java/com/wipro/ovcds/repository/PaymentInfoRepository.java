package com.wipro.ovcds.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.ovcds.model.CreditCardDetails;
import com.wipro.ovcds.model.PaymentInfo;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, String>{

}
