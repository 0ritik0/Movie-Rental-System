package com.wipro.ovcds.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.ovcds.exception.CartNotFoundException;
import com.wipro.ovcds.model.Cart;
import com.wipro.ovcds.model.Product;
import com.wipro.ovcds.repository.CartRepository;


@Service
public class CartServiceImp implements ICartService  {
   
	@Autowired
	private CartRepository cartRepository;

	@Override
	public boolean createCart(Cart cart) throws CartNotFoundException {
		// TODO Auto-generated method stub
		List<Cart> cList = cartRepository.findByUid(cart.getUid());
		if(cList.isEmpty()){
			cartRepository.save(cart);
			return true;
		}else {
			int count = 0;
			for (Cart c: cList) {
				if (c.getProductObj() == cart.getProductObj()) {
					int quantity = c.getQuantity();
					double price = cart.getPrice()+cart.getPrice()*quantity;
					cartRepository.delete(c);
					cart.setQuantity(quantity+1);
					cart.setPrice(price);
					cartRepository.save(cart);
					return true;
				}
				count++;
			}
			if (count == cList.size()) {
				cartRepository.save(cart);
				return true;
			}
		}
		return false;
		
	}

	@Override
	public boolean updateCart(Long id, Cart cart) throws CartNotFoundException {
		List<Cart> car= cartRepository.findByUid(id);
		if(car!=null) {
			//cartRepository.save(car);
			return true;
		}
		else {
			throw new CartNotFoundException("Cart Not Found!!");
		}
	}

	@Override
	public boolean deleteCart(Cart cart) throws CartNotFoundException {
		// TODO Auto-generated method stub
		List<Cart> car= cartRepository.findByUid(cart.getUid());
		if(car!=null) {
			for (Cart c: car) {
				if (c.getProductObj() == cart.getProductObj()) {
					int quantity = c.getQuantity();
					double price = c.getPrice()-c.getPrice()/quantity;
					if (quantity == 1) {
						cartRepository.delete(c);
					}else {
						cartRepository.delete(c);
						cart.setQuantity(quantity-1);
						cart.setPrice(price);
						cartRepository.save(cart);
					}
					break;
				}
			}
			
			return true;
		}
		else {
			throw new CartNotFoundException("Cart Not Found!!");
		}
	}

	@Override
	public Cart getCart(Long id) throws CartNotFoundException {
		// TODO Auto-generated method stub
		List<Cart> car= cartRepository.findByUid(id);
		if(car!= null) {
			return null;
		}
		else {
			throw new CartNotFoundException("Cart Not Found!!");
		}
	}
	
	@Override
	public List<Cart> getAllCart(Long uid) throws CartNotFoundException {
		// TODO Auto-generated method stub
		if(cartRepository.findByUid(uid).isEmpty()) {
			return new ArrayList<Cart>();
			//throw new CartNotFoundException("Cart Not Found!!");
		}
		return cartRepository.findByUid(uid);
	}
	
	public Double totalPrice(List<Cart> carts) {
		double totalPrice = 0;
		for(int i = 0; i < carts.size(); i++) {
			totalPrice += carts.get(i).getPrice();
		}
		return totalPrice;
	}

}
