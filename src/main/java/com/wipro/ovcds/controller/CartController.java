package com.wipro.ovcds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.ovcds.exception.CartNotFoundException;
import com.wipro.ovcds.model.Cart;
import com.wipro.ovcds.model.Product;
import com.wipro.ovcds.model.User;
import com.wipro.ovcds.repository.CartRepository;
import com.wipro.ovcds.repository.UserRepository;
import com.wipro.ovcds.service.CartServiceImp;
import com.wipro.ovcds.service.ProductService;



@Controller
@RequestMapping("api/v1")
public class CartController {
    
	@Autowired
	private CartServiceImp cartMasterImpl;
	//@Autowired
	//private ItemService service;
	@Autowired
	private ProductService service;
	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private UserRepository urepo;
	
	@GetMapping({"/cart", })
	public String home() {
		
		return "cart";
		
	}
	
	@PostMapping("/cart")
	public String createCart(@Param("vcdID") Long vcdID) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//System.out.println(auth.getName());
		User user = urepo.getUserByUsername(auth.getName());
		try {
			Cart cart = new Cart();
			cart.setUid(user.getId());
			cart.setProductObj(service.getProductById(vcdID).get());
			cart.setQuantity(1);
			cart.setPrice(service.getProductById(vcdID).get().getPrice());
			
			boolean value = cartMasterImpl.createCart(cart);
			
			if (value == false) return "redirect:/shop";
		} catch (CartNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/shop/viewproduct/"+vcdID;
	}
	
	
	/*@PutMapping("/cart/{id}")
	public ResponseEntity<String> updateCart(@PathVariable int id, @RequestBody Cart cart){
		try {
			if(cartMasterImpl.updateCart(id, cart)) {
				return new ResponseEntity<>("Product Updated!!", HttpStatus.OK);
			}
		} catch (CartNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>("No such id Exists!!", HttpStatus.NOT_FOUND);
	}*/
	
	@RequestMapping("/cart/viewAll/delete/{id}")
	public String deleteCart(@PathVariable Long id) throws CartNotFoundException{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = urepo.getUserByUsername(auth.getName());
		
		Cart cart = new Cart();
		cart.setUid(user.getId());
		cart.setProductObj(service.getProductById(id).get());
		cart.setQuantity(1);
		
		if(cartMasterImpl.deleteCart(cart)) {
			if(!cartRepo.findAll().isEmpty()) {
				return "redirect:/api/v1/cart/viewAll";
			}
		}
		return "cart";
	}
	
	/*@GetMapping("/cart/view/{id}")
	public ResponseEntity<Cart> getCart(@PathVariable int id){
		Cart cart;
		try {
			cart = cartMasterImpl.getCart(id);
			if(cart!=null) {
				return new ResponseEntity<>(cart, HttpStatus.OK);
			}
		} catch (CartNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}*/
	
	@GetMapping("/cart/viewAll")
	public String getAllCart(Model model){
		List<Cart> carts;
		List<Product> items;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = urepo.getUserByUsername(auth.getName());
		try {
			
			carts = cartMasterImpl.getAllCart(user.getId());
			
			if(carts!=null) {
				//items = service.listByID(carts);
				double totalPrice = cartMasterImpl.totalPrice(carts);
				//model.addAttribute("cart", items);
				model.addAttribute("cart", carts);
				model.addAttribute("total", carts.size());
				//model.addAttribute("forQuantity", carts);
				model.addAttribute("totalPrice", totalPrice);
				return "cart";
			}
			
		} catch (CartNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/api/v1/cart/viewAll";
	}
	
	
}
