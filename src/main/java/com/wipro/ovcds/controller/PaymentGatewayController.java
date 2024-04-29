package com.wipro.ovcds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wipro.ovcds.dto.PaymentRequest;
import com.wipro.ovcds.exception.CartNotFoundException;
import com.wipro.ovcds.model.Cart;
import com.wipro.ovcds.model.CreditCardDetails;
import com.wipro.ovcds.model.PaymentInfo;
import com.wipro.ovcds.model.Product;
import com.wipro.ovcds.model.ProxyData;
import com.wipro.ovcds.model.ShippingDetails;
import com.wipro.ovcds.model.User;
import com.wipro.ovcds.repository.CreditCardDetailsRepository;
import com.wipro.ovcds.repository.ProxyDataRepository;
import com.wipro.ovcds.repository.ShippingDetailsRepository;
import com.wipro.ovcds.repository.UserRepository;
import com.wipro.ovcds.service.CartServiceImp;
import com.wipro.ovcds.service.PaymentService;
import com.wipro.ovcds.service.ProductService;

@Controller
@EnableTransactionManagement
public class PaymentGatewayController {
	
	@Autowired
	private CreditCardDetailsRepository prepo;
	@Autowired
	private UserRepository urepo;
	@Autowired
	private ProxyDataRepository proxyrepo;
	@Autowired
	private PaymentService service;
	@Autowired
	private ProductService pservice;
	@Autowired
	private CartServiceImp cartMasterImpl;
	@Autowired
	private ShippingDetailsRepository sdrepo;
	
	@GetMapping("/checkoutPage")
	public String checkout(Model model, @Param("amount") double amount) throws CartNotFoundException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = urepo.getUserByUsername(auth.getName());
		ShippingDetails sd = new ShippingDetails();
		//List<Cart> cart = cartMasterImpl.getAllCart(user.getId());
		//List<Product> cartItems = pservice.listByID(cart);
		//model.addAttribute("cart", cartItems);
		model.addAttribute("sd", sd);
		model.addAttribute("total",amount);
		//model.addAttribute("size", cartItems.size());
		return "checkout";
	}
	
	@PostMapping("/checkout")
	public String page(@ModelAttribute("sd") ProxyData sd, Model model) throws CartNotFoundException {
		proxyrepo.save(sd);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = urepo.getUserByUsername(auth.getName());
		List<Cart> cart = cartMasterImpl.getAllCart(user.getId());
		
		PaymentInfo paymentInfo = new PaymentInfo();
		
		model.addAttribute("paymentInfo", paymentInfo);
		model.addAttribute("bill", cartMasterImpl.totalPrice(cart));
		return "payment";
	}
	
	
	
	@RequestMapping("/paymentPage")
	public String checkout(@ModelAttribute("paymentInfo") PaymentInfo paymentInfo, @Param("bill") double bill) throws CartNotFoundException {
		System.out.println(bill);
		if(service.completePayment(paymentInfo, bill).getStatus().equals("SUCCESS")) {
			return "success";
		}
		
		return "cancel";
	}
	
	
}
