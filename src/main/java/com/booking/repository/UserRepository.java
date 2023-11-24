package com.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.booking.model.Driver;
import com.booking.model.Ride;
import com.booking.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByEmail(String email);
	
	@Query("Select r from Ride r where r.status=COMPLETED AND r.user.id=:userId")
	public List<Ride> getCmpletedRides(@Param("userId")Integer userId);
}
