package com.booking.config;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class JwtTokenValidationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		
		String jwt = request.getHeader(JwtSecurityContext.JWT_HEADER);
		
		if(jwt!=null) {
			try {
				
				jwt = jwt.substring(7);
				SecretKey key = Keys.hmacShaKeyFor(JwtSecurityContext.JWT_KEY.getBytes());
				
				Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
				String username = String.valueOf(claims.get("email"));
				String authoritie = (String)claims.get("authorities");
				List<GrantedAuthority> authsList = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritie);
				
				Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,authsList);
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			} catch (Exception e) {
				// TODO: handle exception
				throw new BadCredentialsException("invalid token redcieved");
			}
			
		}
		filterChain.doFilter(request, response);
	
		
	}
	
	
//	public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
//		Set<String> authoritiesSet = new HashSet<>();
//		for(GrantedAuthority authority:collection) {
//			authoritiesSet.add(authority.getAuthority());
//		}
//		return String.join(",", authoritiesSet);
//	
//		
//	}

}
