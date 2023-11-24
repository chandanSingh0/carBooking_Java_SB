package com.booking.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class Calculates {
	
	private static final int EARTH_RADIUS = 6371;
	
	
	public double calculateDistance(double sourceLat,double sourceLong,double desLat,double desLong) {
		double dLat = Math.toRadians(desLong-sourceLat);
		double dLng = Math.toRadians(desLong-sourceLong);
		
		double a = Math.sin(dLat/2)*Math.sin(dLat/2)+Math.cos(Math.toRadians(sourceLat))*Math.cos(Math.toRadians(desLat))*Math.sin(dLng/2)*Math.sin(dLng/2);
		
		double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		double distanc = EARTH_RADIUS*c;
		
		
		return distanc;
	}
	
	public long calcDuration(LocalDateTime stTime , LocalDateTime enTime) {
		Duration duration = Duration.between(stTime, enTime);
		return duration.getSeconds();
	}
	
	public double calcFare(double dist) {
		double baseFare = 11;
		double totFare = baseFare*dist;
		return totFare;
	}
	
}
