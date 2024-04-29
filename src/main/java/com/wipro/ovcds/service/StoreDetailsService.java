package com.wipro.ovcds.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.ovcds.model.StoreDetails;
import com.wipro.ovcds.repository.StoreDetailsRepository;



@Service
public class StoreDetailsService {
	
	@Autowired
	StoreDetailsRepository storeDetailsRepository;
	public List<StoreDetails>getAllStore(){
		
		return storeDetailsRepository.findAll();
	}
	public void addStore(StoreDetails storeDetails) {
		
		storeDetailsRepository.save(storeDetails);
	}
	public void removeStoreById(int id) {
		storeDetailsRepository.deleteById(id);
	}
	
	public Optional<StoreDetails> getStoreById(int id) {
		return storeDetailsRepository.findById(id);
	}
}
