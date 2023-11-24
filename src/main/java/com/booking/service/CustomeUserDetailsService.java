package com.booking.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.realm.UserDatabaseRealm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.booking.model.Driver;
import com.booking.model.User;
import com.booking.repository.DriverRepository;
import com.booking.repository.UserRepository;

@Service
public class CustomeUserDetailsService implements UserDetailsService{

	private DriverRepository driverRepository;
	private UserRepository userRepository;
	
	public CustomeUserDetailsService(DriverRepository driverRepository,UserRepository userRepository) {
		// TODO Auto-generated constructor stub
		this.driverRepository = driverRepository;
		this.userRepository = userRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		List<GrantedAuthority> authorities = new ArrayList<>();
		User user = userRepository.findByEmail(username);
		
		if(user!=null) {
			return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
			
		}
		
		Driver driver = driverRepository.findByEmail(username);
		if(driver!=null) {
			return new org.springframework.security.core.userdetails.User(driver.getEmail(),driver.getPassword(),authorities);
			
		}
		
		throw new UsernameNotFoundException("user not found with email:"+ username);
		
		
	}

}
