package com.booking.service;

import com.booking.exception.DriverException;
import com.booking.exception.RideException;
import com.booking.model.Driver;
import com.booking.model.Ride;
import com.booking.model.User;
import com.booking.request.RideRequest;

public interface RideService {
	
	public Ride requestRide(RideRequest request,User user) throws DriverException;
	
	public Ride createRideRequest(User user, Driver nearestDriver,double pickupLatitude,double pickupLongitude,double destLatitude,
			double destLongitude,String pickupArea,String destinationArea);
	
	public void acceptRide(Integer rideId)throws RideException;
	
	public void declineRide(Integer rideId,Integer driverId) throws RideException;
	
	public void startRide(Integer rideId,int opt)throws RideException;
	
	public void completeRide(Integer rideId)throws RideException;
	
	public void cancelRide(Integer rideId)throws RideException;
	
	public Ride findRideById(Integer rideId)throws RideException;
}
