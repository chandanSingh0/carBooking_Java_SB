package com.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.controller.DtoMapper.DtoMapper;
import com.booking.dto.RideDto;
import com.booking.exception.DriverException;
import com.booking.exception.RideException;
import com.booking.exception.UserException;
import com.booking.model.Driver;
import com.booking.model.Ride;
import com.booking.model.User;
import com.booking.request.RideRequest;
import com.booking.request.StartRideRequest;
import com.booking.response.MessageResponse;
import com.booking.service.DriverService;
import com.booking.service.RideService;
import com.booking.service.UserService;

@RestController
@RequestMapping("/api/rides")
public class RideController {
	@Autowired
	private RideService rideService;
	@Autowired
	private UserService userService;
	@Autowired
	private DriverService driverService;
	
	@PostMapping("/request")
	public ResponseEntity<RideDto> userRequestRideHandler(@RequestBody RideRequest rideRequest,@RequestHeader("Authorization") String jwt) throws UserException, DriverException{
		User user = userService.getReqUserProfile(jwt);
		System.out.println(user.getFullName()+"---------------");
		Ride ride = rideService.requestRide(rideRequest, user);
		System.out.println(ride+"---------------");
		RideDto rideDto = DtoMapper.toRideDto(ride);
		
		return new ResponseEntity<RideDto>(rideDto,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{rideId}/accept")
	public ResponseEntity<MessageResponse> acceptRideHandler(@PathVariable Integer rideId) throws RideException{
		rideService.acceptRide(rideId);
		MessageResponse messageResponse = new MessageResponse("Ride Accepted by the driveer");
		
		return new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.ACCEPTED);	
	}
	
	@PutMapping("/{rideId}/decline")
	public ResponseEntity<MessageResponse> declineRideHandler(@RequestHeader String jwt,@PathVariable Integer rideId) throws DriverException, RideException{
		Driver  driver = driverService.getReqDriverProfile(jwt);
		rideService.declineRide(rideId, driver.getId());
		MessageResponse messageResponse = new MessageResponse("Ride Decline By Driver");
		return new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{rideId}/start")
	public ResponseEntity<MessageResponse> startRideHandler(@RequestBody StartRideRequest rideRequest,@PathVariable Integer rideId) throws DriverException, RideException{
//		Driver  driver = driverService.getReqDriverProfile(jwt);
		rideService.startRide(rideId, rideRequest.getOtp());
		MessageResponse messageResponse = new MessageResponse("Ride is Started");
		return new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{rideId}/complete")
	public ResponseEntity<MessageResponse> completeRideHandler(@PathVariable Integer rideId) throws DriverException, RideException{
//		Driver  driver = driverService.getReqDriverProfile(jwt);
		System.out.println("comp controllerrrrr");
		rideService.completeRide(rideId);
		MessageResponse messageResponse = new MessageResponse("Ride is Completed thank you for booking");
		return new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{rideId}")
	public ResponseEntity<RideDto> findRideByIdHandler(@PathVariable Integer rideId,@RequestHeader("Authorization") String jwt) throws UserException, RideException{
		User user = userService.getReqUserProfile(jwt);
		Ride ride = rideService.findRideById(rideId);
		RideDto rideDto = DtoMapper.toRideDto(ride);
		return new ResponseEntity<RideDto>(rideDto,HttpStatus.ACCEPTED);
	}
		
}
