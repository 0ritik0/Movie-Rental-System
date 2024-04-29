package com.wipro.ovcds.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wipro.ovcds.model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
 
	List<Product> findAllByCategory_Id(int id); 
	List<Product> findAllByStoreDetails_Id(int id); 
	@Query("SELECT p FROM Product p WHERE upper(p.name) LIKE %?1%"
			+ " OR p.id LIKE %?1%"
			+ " OR upper(p.category.name) LIKE %?1%"
			+ " OR upper(p.storeDetails.name) LIKE %?1%"
			+ " OR upper(p.description) LIKE %?1%"
            )
    List<Product> search(String keyword);
}
 