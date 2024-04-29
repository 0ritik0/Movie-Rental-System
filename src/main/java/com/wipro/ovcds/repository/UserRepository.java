package com.wipro.ovcds.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.wipro.ovcds.model.User;
 
public interface UserRepository extends CrudRepository<User, Long> {
 
	Optional<User>findUserByUsername(String username);
	
	
    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);
}
