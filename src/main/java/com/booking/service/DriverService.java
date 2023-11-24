package com.booking.service;

import java.util.List;

import com.booking.exception.DriverException;
import com.booking.model.Driver;
import com.booking.model.Ride;
import com.booking.request.DriverSignupRequest;

public interface DriverService {
		public Driver registerDriver(DriverSignupRequest driverSignupRequest);
		
		public List<Driver> getAvailableDrivers(double pickupLatitude,double pickupLongitude, Ride ride);
		
		public Driver findNearestDriver(List<Driver> availableDrivers,double pickupLatitude,double pickupLongitude);
		
		public Driver getReqDriverProfile(String jwt)throws DriverException;
		
		public Ride getDriversCurrentRide(Integer driverId)throws DriverException;
		
		public List<Ride> getAllocatedRides(Integer driverId);
		
		public Driver findDriverById(Integer driverId)throws DriverException;
		
		//to generate drivers salary according to its completed rides
		public List<Ride> completedRids(Integer driverId)throws DriverException;
}
