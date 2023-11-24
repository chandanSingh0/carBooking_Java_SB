package com.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.booking.config.JwtUtil;
import com.booking.domain.RideStatus;
import com.booking.domain.UserRole;
import com.booking.exception.DriverException;
import com.booking.model.Driver;
import com.booking.model.Licence;
import com.booking.model.Ride;
import com.booking.model.Vehicle;
import com.booking.repository.DriverRepository;
import com.booking.repository.LicenceRepository;
import com.booking.repository.RideRepository;
import com.booking.repository.VehicleRepository;
import com.booking.request.DriverSignupRequest;


@Service
public class DriverServiceImpl implements DriverService {
	@Autowired
	private DriverRepository driverRepository;
	@Autowired
	private Calculates calculates;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private LicenceRepository licenceRepository;
	@Autowired
	private RideRepository rideRepository;

	@Override
	public Driver registerDriver(DriverSignupRequest driverSignupRequest) {
		// TODO Auto-generated method stub

		Licence licence = driverSignupRequest.getLicence();
		Vehicle vehicle = driverSignupRequest.getVehicle();

		Licence createdLicence = new Licence();

		createdLicence.setLicenceState(licence.getLicenceState());
		createdLicence.setLicenceNumber(licence.getLicenceNumber());
		createdLicence.setLicenceExpirationDate(licence.getLicenceExpirationDate());
		createdLicence.setId(licence.getId());

		Licence savedLicence = licenceRepository.save(createdLicence);

		Vehicle createdVehicle = new Vehicle();

		createdVehicle.setCapacity(vehicle.getCapacity());
		createdVehicle.setColor(vehicle.getColor());
		createdVehicle.setId(vehicle.getId());
		createdVehicle.setLicencePlate(vehicle.getLicencePlate());
		createdVehicle.setMake(vehicle.getMake());
		createdVehicle.setModel(vehicle.getModel());
		createdVehicle.setYear(vehicle.getYear());

		Vehicle savedVehicle = vehicleRepository.save(createdVehicle);

		Driver driver = new Driver();

		String encodedPassword = passwordEncoder.encode(driverSignupRequest.getPassword());

		driver.setEmail(driverSignupRequest.getEmail());
		driver.setName(driverSignupRequest.getName());
		driver.setMobile(driverSignupRequest.getMobile());
		driver.setPassword(encodedPassword);
		driver.setLicence(savedLicence);
		driver.setVehicle(savedVehicle);
		driver.setRole(UserRole.DRIVER);

		driver.setLatitude(driverSignupRequest.getLatitude());
		driver.setLongitude(driverSignupRequest.getLongitude());

		Driver createDriver = driverRepository.save(driver);

		savedLicence.setDriver(createDriver);
		savedVehicle.setDriver(createDriver);

		licenceRepository.save(savedLicence);
		vehicleRepository.save(savedVehicle);

		return createDriver;
	}

	@Override
	public List<Driver> getAvailableDrivers(double pickupLatitude, double pickupLongitude, Ride ride) {
		// TODO Auto-generated method stub
		List<Driver> getAllDrivers = driverRepository.findAll();
		
		for(Driver driver:getAllDrivers) {
			System.out.println("===================");
			System.out.println(driver.getName()+"    driver name for loop 1111");
			System.out.println("===================");
			System.out.println("===================");
			System.out.println("===================");
		}

		List<Driver> availDrivers = new ArrayList<>();

		for (Driver driver : getAllDrivers) {
			if (driver.getCurrentRide() != null && driver.getCurrentRide().getStatus() != RideStatus.COMPLETED) {
				continue;
			}
			// if any of the driver is decline the ride request from user then we have to
			// search for other ride.
			if (ride.getDeclinedDrivers().contains(driver.getId())) {
				System.out.println("its Contains");
				continue;
			}
			double driverLat = driver.getLatitude();
			double driverLng = driver.getLongitude();

			double distance = calculates.calculateDistance(driverLat, driverLng, pickupLatitude, pickupLongitude);

			availDrivers.add(driver);
			

		}
		for(Driver driver:availDrivers) {
		System.out.println("===================");
		System.out.println(driver.getName()+"    driver name for loop 2222");
		System.out.println("===================");
		System.out.println("===================");
		System.out.println("===================");
		}
		return availDrivers;
	}

	@Override
	public Driver findNearestDriver(List<Driver> availableDrivers, double pickupLatitude, double pickupLongitude) {
		// TODO Auto-generated method stub

		double min = Double.MAX_VALUE;
		Driver nearestDriver = null;

		for (Driver driver : availableDrivers) {
			double driverLat = driver.getLatitude();
			double driverLng = driver.getLongitude();

			double dist = calculates.calculateDistance(pickupLatitude, pickupLongitude, driverLat, driverLng);

			if (min > dist) {
				min = dist;
				nearestDriver = driver;
			}
		}

		return nearestDriver;
	}

	@Override
	public Driver getReqDriverProfile(String jwt) throws DriverException {
		// TODO Auto-generated method stub
		String emailString = jwtUtil.getEmailFromJwt(jwt);

		Driver driver = driverRepository.findByEmail(emailString);

		if (driver == null) {
			throw new DriverException("driver not exist with email: " + emailString);
		}

		return driver;
	}

	@Override
	public Ride getDriversCurrentRide(Integer driverId) throws DriverException {
		// TODO Auto-generated method stub
		Driver driver = findDriverById(driverId);
		return driver.getCurrentRide();
	}

	@Override
	public List<Ride> getAllocatedRides(Integer driverId) {
		// TODO Auto-generated method stub
		List<Ride> allocateRides = driverRepository.getAllocatedRides(driverId);

		return allocateRides;
	}

	@Override
	public Driver findDriverById(Integer driverId) throws DriverException {
		// TODO Auto-generated method stub
		Optional<Driver> optional = driverRepository.findById(driverId);
		if (optional.isPresent()) {
			return optional.get();
		}

		throw new DriverException("driver not exist with id: " + driverId);

	}

	@Override
	public List<Ride> completedRids(Integer driverId) throws DriverException {
		// TODO Auto-generated method stub
		List<Ride> allocateRides = driverRepository.getCompletedRides(driverId);

		return allocateRides;
	}
}
