package com.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.exception.DriverException;
import com.booking.model.Driver;
import com.booking.model.Ride;
import com.booking.service.DriverService;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
	@Autowired
	private DriverService driverService;
	
	@GetMapping("/profile")
	public ResponseEntity<Driver> getDriverProfileHandler(@RequestHeader("Authorization")String jwt ) throws DriverException{
		Driver driver = driverService.getReqDriverProfile(jwt);
		return new ResponseEntity<Driver>(driver,HttpStatus.OK);
	}
	
	@GetMapping("/{driverId}/current_ride")
	public ResponseEntity<Ride> getDriverCurrentRideHandler(@PathVariable Integer driverId) throws DriverException{
		Ride ride = driverService.getDriversCurrentRide(driverId);
		return new ResponseEntity<Ride>(ride,HttpStatus.OK);
	}
	
	@GetMapping("/{driverId}/allocated")
	public ResponseEntity<List<Ride>>getAllocatedRidesHandler(@PathVariable Integer driverId){
		List<Ride> rides =driverService.getAllocatedRides(driverId);
		return new ResponseEntity<List<Ride>>(rides,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/rides/completed")
	public ResponseEntity<List<Ride>>getCompletedRidesHandler(@RequestHeader("Authorization") String jwt) throws DriverException{
		Driver driver = driverService.getReqDriverProfile(jwt);
		
		List<Ride> rides = driverService.completedRids(driver.getId());
		
		return new ResponseEntity<List<Ride>>(rides,HttpStatus.ACCEPTED);
	}
	
	
	
	
}
