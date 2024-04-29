package com.wipro.ovcds.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.ovcds.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
