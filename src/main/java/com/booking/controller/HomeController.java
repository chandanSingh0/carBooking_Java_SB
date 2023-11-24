package com.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.response.MessageResponse;

@RestController
public class HomeController {

	@GetMapping("/")
	public static ResponseEntity<MessageResponse>homeController(){
		MessageResponse messageResponse = new MessageResponse("Welcome! to Cab Booking Backend Application");
		return new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.OK);
	}
	
}
