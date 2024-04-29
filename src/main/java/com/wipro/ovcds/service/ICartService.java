package com.wipro.ovcds.service;

import java.util.List;

import com.wipro.ovcds.exception.CartNotFoundException;
import com.wipro.ovcds.model.Cart;

public interface ICartService {
    
	public boolean createCart(Cart cart) throws CartNotFoundException;

	public boolean updateCart(Long id, Cart cart) throws CartNotFoundException;

	public boolean deleteCart(Cart cart) throws CartNotFoundException;

	public Cart getCart(Long id) throws CartNotFoundException;

	public List<Cart> getAllCart(Long uid) throws CartNotFoundException;

}

