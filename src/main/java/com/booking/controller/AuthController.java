package com.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.config.JwtUtil;
import com.booking.domain.UserRole;
import com.booking.exception.UserException;
import com.booking.model.Driver;
import com.booking.model.User;
import com.booking.repository.DriverRepository;
import com.booking.repository.UserRepository;
import com.booking.request.DriverSignupRequest;
import com.booking.request.LoginRequest;
import com.booking.request.SignupRequest;
import com.booking.response.JwtResponse;
import com.booking.service.CustomeUserDetailsService;
import com.booking.service.DriverService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private UserRepository userRepository;
	private DriverRepository driverRepository;
	private PasswordEncoder passwordEncoder;
	private JwtUtil jwtUtil;
	private CustomeUserDetailsService customeUserDetailsService;
	private DriverService driverService;
	

	public AuthController(UserRepository userRepository,DriverRepository driverRepository,
			PasswordEncoder passwordEncoder,JwtUtil jwtUtil,CustomeUserDetailsService customeUserDetailsService,DriverService driverService) {
		// TODO Auto-generated constructor stub
		this.userRepository=userRepository;
		this.driverRepository=driverRepository;
		this.passwordEncoder=passwordEncoder;
		this.jwtUtil=jwtUtil;
		this.customeUserDetailsService=customeUserDetailsService;
		this.driverService=driverService;
	}
	
	@PostMapping("/user/signup")
	public ResponseEntity<JwtResponse> signupHandler(@RequestBody SignupRequest req) throws UserException{
		
		String emailString = req.getEmail();
		String fullnameString = req.getFullName();
		String mobileString = req.getMobile();
		String passwordString = req.getpassword();
		
	   User user = userRepository.findByEmail(emailString);
	   
	   if(user!=null) {
		   throw new UserException("User already Exist with email"+ emailString);
	   }
	   
	   String encodedPassword = passwordEncoder.encode(passwordString);
	   
	   User createdUser = new User();
	   createdUser.setEmail(emailString);
	   createdUser.setPassword(encodedPassword);
	   createdUser.setFullName(fullnameString);
	   createdUser.setMobile(mobileString);
	   createdUser.setRole(UserRole.USER);
	   
	   User saveUser = userRepository.save(createdUser);
	   
	   Authentication authentication = new UsernamePasswordAuthenticationToken(saveUser.getEmail(), saveUser.getPassword());
	   
	   SecurityContextHolder.getContext().setAuthentication(authentication);
	   
	   String jwt = jwtUtil.generateToken(authentication);
	   
	   JwtResponse jwtResponse = new JwtResponse();
	   
	   jwtResponse.setJwt(jwt);
	   jwtResponse.setAuthenticated(true);
	   jwtResponse.setError(false);
	   jwtResponse.setErrorDetails(null);
	   jwtResponse.setType(UserRole.USER);
	   jwtResponse.setMessage("Account Created Succcessfully :" +saveUser.getFullName() );
	   
	   return new ResponseEntity<JwtResponse>(jwtResponse,HttpStatus.OK);
	 
	}
	
	
	@PostMapping("/driver/signup")
	public ResponseEntity<JwtResponse> driverSignupHandler(@RequestBody DriverSignupRequest driverSignupRequest) throws UserException{
		Driver driver = driverRepository.findByEmail(driverSignupRequest.getEmail());
		JwtResponse jwtResponse = new JwtResponse();
		if(driver!=null) {
			 jwtResponse.setAuthenticated(true);
			 jwtResponse.setErrorDetails("email already used with another account");
			   jwtResponse.setError(true);
			   return new ResponseEntity<JwtResponse>(jwtResponse,HttpStatus.BAD_REQUEST);
		}
		 Driver createdDriver = driverService.registerDriver(driverSignupRequest);
		 
		   
		   Authentication authentication = new UsernamePasswordAuthenticationToken(createdDriver.getEmail(), createdDriver.getPassword());
		   
		   SecurityContextHolder.getContext().setAuthentication(authentication);
		   
		   String jwt = jwtUtil.generateToken(authentication);
		   
		   
		   
		   jwtResponse.setJwt(jwt);
		   jwtResponse.setAuthenticated(true);
		   jwtResponse.setError(false);
		   jwtResponse.setErrorDetails(null);
		   jwtResponse.setType(UserRole.DRIVER);
		   jwtResponse.setMessage("Account Created Succcessfully :" +createdDriver.getName() );
		   
		   return new ResponseEntity<JwtResponse>(jwtResponse,HttpStatus.OK);
		
	}
		
	
	/**
	 * login method same for both driver ad user
	 * 
	 */
	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> signin(@RequestBody LoginRequest req){
		String username = req.getEmail();
		String password = req.getPassword();
		
		Authentication authentication = authenticate(password,username);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtil.generateToken(authentication);
		
		JwtResponse jwtResponse = new JwtResponse();
		   
		   jwtResponse.setJwt(jwt);
		   jwtResponse.setAuthenticated(true);
		   jwtResponse.setError(false);
		   jwtResponse.setErrorDetails(null);
		   jwtResponse.setType(UserRole.USER);
		   jwtResponse.setMessage("Account Login Succcessfully :"  );
		   
		   return new ResponseEntity<JwtResponse>(jwtResponse,HttpStatus.OK);
		
		
	}
	
	private Authentication authenticate(String password,String username) {
		
		UserDetails userDetails = customeUserDetailsService.loadUserByUsername(username);
		
		if(userDetails==null) {
			throw new BadCredentialsException("invalid username password from authenticate method");
		}
		//comparing password through the matches method of passwordEncoder
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("invalid username or password");
		}
		
		return new UsernamePasswordAuthenticationToken(username,null, userDetails.getAuthorities());
		
	}
	
}
