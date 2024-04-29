package com.wipro.ovcds.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.ovcds.model.Cart;
import com.wipro.ovcds.model.Product;
import com.wipro.ovcds.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	public List<Product> getAllProduct(){
		
		return productRepository.findAll();
	}
	public void addProduct(Product product) {
		productRepository.save(product); 
	}
	
	public void removeProductById(long id)
	{
		productRepository.deleteById(id);
	}
	public Optional<Product> getProductById(long id){
		return productRepository.findById(id);
	}
	public List<Product> getAllProductsByCategoryId(int id){
		return productRepository.findAllByCategory_Id(id);
		
	}
	public List<Product> getAllProductsByStoreDetailsId(int id){
		return productRepository.findAllByStoreDetails_Id(id);
		
	}
	/*public List<Product> listByID(List<Cart> cart){
		List<Product> items = new ArrayList<>();
		for(int i = 0; i < cart.size(); i++) {
			items.add(productRepository.findById(cart.get(i).getProductObj()).get());
		}
		return items;
	}*/
	public List<Product> listAll(String keyword){
		if (keyword!=null) {
			return productRepository.search(keyword.toUpperCase());
		}
		return productRepository.findAll();
	}
}


