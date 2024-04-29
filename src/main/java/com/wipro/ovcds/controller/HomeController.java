package com.wipro.ovcds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wipro.ovcds.model.Product;
import com.wipro.ovcds.service.CategoryService;
import com.wipro.ovcds.service.ProductService;
import com.wipro.ovcds.service.StoreDetailsService;

@Controller
public class HomeController {

	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@Autowired
	StoreDetailsService storeService;
	
	@GetMapping({"/", "home"})
	public String home(Model model) {
		
		return "index";
		
	}
	
	@GetMapping("/shop")
	public String shop(Model model, @Param("keyword") String keyword) {
		List<Product> products = productService.listAll(keyword);
        model.addAttribute("keyword", keyword);
		model.addAttribute("categories" , categoryService.getAllCategory());
		model.addAttribute("products", products);
		model.addAttribute("sdetails", productService.getAllProduct());
		return "shop";
		
	}
	
	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model,@PathVariable int id) {
		model.addAttribute("categories" , categoryService.getAllCategory());
		model.addAttribute("sdetails" , storeService.getAllStore());
		model.addAttribute("products", productService.getAllProductsByCategoryId(id));
		return "shop";
		
	}
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model,@PathVariable int id) {
		model.addAttribute("product", productService.getProductById(id).get());
		return "viewProduct";
		
	}
}













