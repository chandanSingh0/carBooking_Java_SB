package com.booking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.config.JwtUtil;
import com.booking.exception.UserException;
import com.booking.model.Ride;
import com.booking.model.User;
import com.booking.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserRepository userRepository;
	@Autowired
	public JwtUtil jwtUtil;

	@Override
	public User getReqUserProfile(String token) throws UserException {
		// TODO Auto-generated method stub
		String email = jwtUtil.getEmailFromJwt(token);

		User user = userRepository.findByEmail(email);

		if (user != null) {
			return user;
		}

		throw new UserException("Invalid Token");
	}

	@Override
	public User findUserByUserId(Integer userId) throws UserException {
		// TODO Auto-generated method stub
		Optional<User> user = userRepository.findById(userId);

		if (user.isPresent()) {
			return user.get();
		}

		throw new UserException("ride is not exist with id ---" + userId);

	}

	@Override
	public List<Ride> completedRide(Integer userId) throws UserException {
		// TODO Auto-generated method stub
		List<Ride> completedRides = userRepository.getCmpletedRides(userId);
		return completedRides;
	}

}
