package com.wipro.ovcds.repository;import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.ovcds.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	//Optional<Cart> findByProduct(Long product);

	List<Cart> findByUid(Long id);
	
}

