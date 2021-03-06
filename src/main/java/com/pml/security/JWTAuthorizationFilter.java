package com.pml.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	private JWTUtil jwtUtil;
	private UserDetailsService userDetailsService;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}
	
	/**
	 * Create an authentication filter to request.
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param chain FilterChain
	 * @return void
	 */
	@Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
		// Get the authentication header
		String header = req.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer ")) {
			// Obtain 7-digit token authentication (Bearer )
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7));
			if (auth != null) 
				SecurityContextHolder.getContext().setAuthentication(auth);
		}
		chain.doFilter(req, res);
	}

	/**
	 * Returns the object UsernamePasswordAuthenticationToken from token parameter. Containing the personalized user and their authorities.
	 * @param token String
	 * @return UsernamePasswordAuthenticationToken
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if (jwtUtil.tokenIsValid(token)) {
			String username = jwtUtil.getUsername(token);
			UserDetails user = userDetailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}
	
	

}
