package com.booking.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {
		
	SecretKey key = Keys.hmacShaKeyFor(JwtSecurityContext.JWT_KEY.getBytes());
	
	
	public String generateToken(Authentication authentication) {
		String jwt = Jwts.builder().setIssuer("coding")
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+864000000))
				.claim("email", authentication.getName())
				.signWith(key)
				.compact();
		return jwt;
				
	}
	
	public String getEmailFromJwt(String jwt) {
		jwt = jwt.substring(7);
		
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		
		String emailString=String.valueOf(claims.get("email"));
		return emailString;
	}
	
	
	
}
