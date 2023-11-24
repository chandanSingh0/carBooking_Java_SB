package com.booking.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.domain.RideStatus;
import com.booking.exception.DriverException;
import com.booking.exception.RideException;
import com.booking.model.Driver;
import com.booking.model.Ride;
import com.booking.model.User;
import com.booking.repository.DriverRepository;
import com.booking.repository.RideRepository;
import com.booking.request.RideRequest;

@Service
public class RideServiceImpl implements RideService {
	@Autowired
	private DriverService driverService;
	@Autowired
	public RideRepository rideRepository;
	@Autowired
	public Calculates calculates;
	@Autowired
	public DriverRepository driverRepository;

	@Override
	public Ride requestRide(RideRequest request, User user) throws DriverException {
		// TODO Auto-generated method stub
		System.out.println(request.getPickUpLatitude()+" oooooooooooo");
		System.out.println(request.getPickUpLongitude()+" oooooooooooo");
		System.out.println(request.getDestinationLatitude()+" oooooooooooo");
		System.out.println(request.getDestinationLongitude()+" oooooooooooo");
		System.out.println(user.getFullName()+" oooooooooooo");
		
		
		
		
		
		double pickupLat = request.getPickUpLatitude();
		double pickupLong = request.getDestinationLongitude();
		double destLat = request.getDestinationLatitude();
		double desstLong = request.getDestinationLongitude();

		String pickUpAreaString = request.getPickupArea();
		String destAreaString = request.getDestinationArea();

		Ride exitRide = new Ride();

		List<Driver> availDrivers = driverService.getAvailableDrivers(pickupLat, pickupLong, exitRide);
		for(Driver driver:availDrivers) {
			System.out.println("===================");
			System.out.println(driver.getName()+"    driver name for loop 3333");
			System.out.println("===================");
			System.out.println("===================");
			System.out.println("===================");
			}
		Driver nearestDriver = driverService.findNearestDriver(availDrivers, pickupLat, pickupLong);
		
			System.out.println("===================");
			if(nearestDriver!=null)
			System.out.println(nearestDriver.getName()+"    driver name for loop 4444");
			System.out.println("===================");
			System.out.println("===================");
			System.out.println("===================");
			
		if (nearestDriver == null) {
			throw new DriverException("Driver not available");
		}

		System.out.println("duration----beforeRide");

		Ride ride = createRideRequest(user, nearestDriver, pickupLat, pickupLong, destLat, desstLong, pickUpAreaString,
				destAreaString);

		System.out.println("duration----afterRide");

		return ride;
	}

	@Override
	public Ride createRideRequest(User user, Driver nearestDriver, double pickupLatitude, double pickupLongitude,
			double destLatitude, double destLongitude, String pickupArea, String destinationArea) {
		// TODO Auto-generated method stub
			
		System.out.println(nearestDriver.getName()+" driverppppppppppppppp");
		System.out.println(user.getFullName()+" userppppppppppppppp");
		
		
		
		
		Ride ride = new Ride();

		ride.setDriver(nearestDriver);
		ride.setUser(user);
		ride.setPickupLatitude(pickupLatitude);
		ride.setPickupLongitude(pickupLongitude);
		ride.setDestinationLatitude(destLatitude);
		ride.setDestinationLongitude(destLongitude);
		ride.setStatus(RideStatus.REQUESTED);
		ride.setPickupArea(pickupArea);
		ride.setDestinationArea(destinationArea);

		return rideRepository.save(ride);
	}

	@Override
	public void acceptRide(Integer rideId) throws RideException {
		// TODO Auto-generated method stub
		Ride ride = findRideById(rideId);

		ride.setStatus(RideStatus.ACCEPTED);

		Driver driver = new Driver();

		driver.setCurrentRide(ride);

		Random random = new Random();
		int otp = random.nextInt(9000) + 1000;
		ride.setOtp(otp);

		driverRepository.save(driver);

		rideRepository.save(ride);

	}

	@Override
	public void declineRide(Integer rideId, Integer driverId) throws RideException {
		// TODO Auto-generated method stub

		Ride ride = findRideById(rideId);
		System.out.println(ride.getId());

		ride.getDeclinedDrivers().add(driverId);

		List<Driver> availableDrivers = driverService.getAvailableDrivers(ride.getPickupLatitude(),
				ride.getPickupLongitude(), ride);

		Driver nearestDriver = driverService.findNearestDriver(availableDrivers, ride.getPickupLatitude(),
				ride.getPickupLongitude());

		// here we picking up another driver
		ride.setDriver(nearestDriver);

		rideRepository.save(ride);

	}

	@Override
	public void startRide(Integer rideId, int opt) throws RideException {
		// TODO Auto-generated method stub
		Ride ride = findRideById(rideId);

		if (opt != ride.getOtp()) {
			throw new RideException("please provide valid otp");
		}

		ride.setStatus(RideStatus.STARTED);
		ride.setStartTime(LocalDateTime.now());
//		ride.setOtp(opt);
		rideRepository.save(ride);

	}

	@Override
	public void completeRide(Integer rideId) throws RideException {
		// TODO Auto-generated method stub
		Ride ride = findRideById(rideId);

		ride.setStatus(RideStatus.COMPLETED);
		ride.setEndTime(LocalDateTime.now());

		double distance = calculates.calculateDistance(ride.getDestinationLatitude(), ride.getDestinationLongitude(),
				ride.getPickupLatitude(), ride.getPickupLongitude());

		LocalDateTime sTime = ride.getStartTime();
		LocalDateTime eDateTime = ride.getEndTime();

		Duration duration = Duration.between(sTime, eDateTime);

		long miliSec = duration.toMillis();
		System.out.println("duration:---" + miliSec);

		double fare = calculates.calcFare(distance);

		ride.setDistance(Math.round((distance * 100) / 100.0));
		ride.setFare((int) Math.round(miliSec));
		ride.setDuration(miliSec);
		ride.setEndTime(LocalDateTime.now());

		Driver driver = ride.getDriver();
		driver.getRides().add(ride);
		driver.setCurrentRide(null);

		System.out.println(fare+"fareDouble fffffffffffff");
		
		// 80% goes to driver and 20% goes to company
		Integer currentRevenue = driver.getTotalRevenue() != null ? driver.getTotalRevenue() : 0;
		System.out.println(currentRevenue+"driverRevenue ddddddddddddddddd");
		
		Integer driverRevenue = (int) (currentRevenue + Math.round(fare * 0.8));
		driver.setTotalRevenue(driverRevenue);
		
		System.out.println(driverRevenue+"driverRevenue ddddddddddddddddd");

		System.out.println("driverRevenue:--->  " + driverRevenue);

		driverRepository.save(driver);

		rideRepository.save(ride);

	}

	@Override
	public void cancelRide(Integer rideId) throws RideException {
		// TODO Auto-generated method stub
		Ride ride = findRideById(rideId);
		ride.setStatus(RideStatus.CANCELLED);
		rideRepository.save(ride);

	}

	@Override
	public Ride findRideById(Integer rideId) throws RideException {
		// TODO Auto-generated method stub
		Optional<Ride> ride = rideRepository.findById(rideId);

		if (ride.isPresent()) {
			return ride.get();
		}

		throw new RideException("ride is not exist with id ---" + rideId);
	}

}
