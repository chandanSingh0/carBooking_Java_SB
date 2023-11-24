package com.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.booking.model.Driver;
import com.booking.model.Ride;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
	
	public Driver findByEmail(String email);

	@Query("SELECT r FROM Ride r WHERE r.status = REQUESTED AND r.driver.id=:driverId")
	public List<Ride> getAllocatedRides(@Param("driverId")Integer driverId);
	
	@Query("SELECT r FROM Ride r WHERE r.status = COMPLETED AND r.driver.id=:driverId")
	public List<Ride> getCompletedRides(@Param("driverId")Integer driverId);

}
