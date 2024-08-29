package com.in2it.spykeemployee.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in2it.spykeemployee.entity.Address;
import java.util.List;


@Repository
public interface AddressRepository extends JpaRepository<Address, UUID>{
	
	List<Address> findByArea(String area);
	List<Address> findByCountry(String country);
	List<Address> findByHouseNo(String houseNo);
	List<Address> findByDistrict(String district);
	List<Address> findByState(String state);
	List<Address> findByPinCode(int pinCode);

}
