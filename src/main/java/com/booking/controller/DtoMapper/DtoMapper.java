package com.booking.controller.DtoMapper;

import org.springframework.stereotype.Service;

import com.booking.dto.DriverDto;
import com.booking.dto.RideDto;
import com.booking.dto.UserDto;
import com.booking.model.Driver;
import com.booking.model.Ride;
import com.booking.model.User;

@Service
public class DtoMapper {
	
	public static DriverDto toDriverDto(Driver driver) {
		DriverDto driverDto = new DriverDto();
		
		System.out.println(driver.getName()+"  inside dto driver");
		
		driverDto.setEmail(driver.getEmail());
		driverDto.setId(driver.getId());
		driverDto.setLatitude(driver.getLatitude());
		driverDto.setLongitude(driver.getLongitude());
		driverDto.setMaobile(driver.getMobile());
		driverDto.setName(driver.getName());
		driverDto.setRating(driver.getRating());
		driverDto.setRole(driver.getRole());
		driverDto.setVehicle(driver.getVehicle());
		
		
		System.out.println(driverDto.getName()+"  inside dto driver  DTOOOO");
		return driverDto;
	}
	
	public static UserDto toUserDto(User user) {
		UserDto userDto = new UserDto();
		
		userDto.setEmail(user.getEmail());
		userDto.setMaobile(user.getMobile());
		userDto.setId(user.getIdInteger());
		userDto.setName(user.getFullName());
		
		return userDto;
	}
	
	public static RideDto toRideDto(Ride ride) {
		DriverDto driverDto = toDriverDto(ride.getDriver());
		UserDto userDto = toUserDto(ride.getUser());
		
		RideDto rideDto = new RideDto();
		
		rideDto.setDestinationLatitude(ride.getDestinationLatitude());
		rideDto.setDestinationLongitude(ride.getDestinationLongitude());
		rideDto.setDistance(ride.getDistance());
		rideDto.setDriver(driverDto);
		rideDto.setDuration(ride.getDuration());
		rideDto.setEndTime(ride.getEndTime());
		rideDto.setFare(ride.getFare());
		rideDto.setId(ride.getId());
		rideDto.setPickupLatitude(ride.getPickupLatitude());
		rideDto.setPickupLongitude(ride.getPickupLongitude());
		rideDto.setStartTime(ride.getStartTime());
		rideDto.setStatus(ride.getStatus());
		rideDto.setUser(userDto);
		rideDto.setPickupArea(ride.getPickupArea());
		rideDto.setDestinationArea(ride.getDestinationArea());
//		rideDto.setPaymentDetails(ride.getPaymentDetails());
		rideDto.setOtp(ride.getOtp());
		
		return rideDto;
		
	}
	
}
