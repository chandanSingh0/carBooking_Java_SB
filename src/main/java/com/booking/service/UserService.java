package com.booking.service;

import java.util.List;

import com.booking.exception.UserException;
import com.booking.model.Ride;
import com.booking.model.User;

public interface UserService {
	
//	public User createUser(User user)throws UserException;
	
	public User getReqUserProfile(String token)throws UserException;
	
	public User findUserByUserId(Integer userId)throws UserException;
	
//	public User findUserByEmail(String email)throws UserException;
	
//	public User findUserByToken(String token)throws UserException;
	
	public List<Ride> completedRide(Integer userId) throws UserException;
}
