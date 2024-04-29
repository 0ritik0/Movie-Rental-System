package com.wipro.ovcds.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.ovcds.dto.PaymentAcknowledgement;
import com.wipro.ovcds.dto.PaymentRequest;
import com.wipro.ovcds.exception.InsufficientAmountException;
import com.wipro.ovcds.model.CreditCardDetails;
import com.wipro.ovcds.model.PaymentInfo;
import com.wipro.ovcds.model.ProxyData;
import com.wipro.ovcds.model.ShippingDetails;
import com.wipro.ovcds.model.User;
import com.wipro.ovcds.repository.CreditCardDetailsRepository;
import com.wipro.ovcds.repository.PaymentInfoRepository;
import com.wipro.ovcds.repository.ProxyDataRepository;
import com.wipro.ovcds.repository.ShippingDetailsRepository;
import com.wipro.ovcds.repository.UserRepository;

@Service
public class PaymentService {
	
	@Autowired
	private CreditCardDetailsRepository cdrepo;
	@Autowired 
	private PaymentInfoRepository prepo;
	@Autowired
	private UserRepository urepo;
	@Autowired
	private ShippingDetailsRepository sdrepo;
	@Autowired
	private ProxyDataRepository proxyrepo;
	
	public CreditCardDetails cd(int creditcardnumber) {
		return cdrepo.getById(creditcardnumber);
	}
	
	public boolean validateCreditLimit(int creditcardnumber, double paidAmount) {
        
		if (paidAmount > cd(creditcardnumber).getBalance()) {
            throw new InsufficientAmountException("insufficient fund..!");
        } else {
        	CreditCardDetails cd;
        	cd = cdrepo.findById(creditcardnumber).get();
        	cd.setBalance(cd.getBalance()-paidAmount);
			cdrepo.save(cd);
            return true;
        }
    }
	
	@Transactional
	public PaymentAcknowledgement completePayment(PaymentInfo paymentInfo, double bill) {
		
		ProxyData proxy = proxyrepo.findAll().get(0);
		ShippingDetails sd = new ShippingDetails();
		
		sd.setAdditionalInfo(proxy.getAdditionalInfo());
		sd.setAddressLine1(proxy.getAddressLine1());
		sd.setAddressLine2(proxy.getAddressLine2());
		sd.setEmail(proxy.getEmail());
		sd.setFirstName(proxy.getFirstName());
		sd.setLastName(proxy.getLastName());
		sd.setPhone(proxy.getPhone());
		sd.setPostCode(proxy.getPostCode());
		sd.setTown(proxy.getTown());
		
		sd = sdrepo.save(sd);
		proxyrepo.deleteAll();
		
		//PaymentInfo paymentInfo = request.getPaymentInfo();
		
		validateCreditLimit(paymentInfo.getCreditCardNo(), bill);
		
		paymentInfo.setShippingId(sd.getId());;
		paymentInfo.setAmount(bill);
		
		prepo.save(paymentInfo);
		
		return new PaymentAcknowledgement("SUCCESS", bill, UUID.randomUUID().toString().split("-")[0], sd);
		
	}
}
