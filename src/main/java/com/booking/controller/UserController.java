package com.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.exception.UserException;
import com.booking.model.Ride;
import com.booking.model.User;
import com.booking.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> findUserByIdHandler(@PathVariable Integer userId) throws UserException{
		User user = userService.findUserByUserId(userId);
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<User>findUserByProfileHandler(@RequestHeader("Authorization")String jwt) throws UserException{
		User user = userService.getReqUserProfile(jwt);
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/rides/completed")
	public ResponseEntity<List<Ride>> getCompletedRidesHandler(@RequestHeader("Authorization")String jwt) throws UserException{
		User user = userService.getReqUserProfile(jwt);
		List<Ride> rides = userService.completedRide(user.getIdInteger());
		return new ResponseEntity<List<Ride>>(rides,HttpStatus.ACCEPTED);
	}

}
