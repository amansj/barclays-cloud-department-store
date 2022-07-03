package com.barclays.store.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.store.config.JwtTokenUtil;
import com.barclays.store.dto.ImmutableJwtResponseDto;
import com.barclays.store.dto.JwtRequestDto;
import com.barclays.store.entity.User;
import com.barclays.store.execption.UserException;
import com.barclays.store.service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




@RestController
@RequestMapping("/user")
public class UserController {
	
	private static final Logger LOGGER=LogManager.getLogger(UserController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;


	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(method=RequestMethod.POST, path="/signup")
	public ResponseEntity<?> registerUser(@RequestBody User user){
		
		try {
			user=userService.saveUser(user);
			return ResponseEntity.ok(user);
			
		}catch (UserException e) {
			return ResponseEntity.status(e.getStatus()).body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		
	}
	
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestDto authenticationRequest)
			throws Exception {
		String userName=authenticationRequest.getEmail();
		
		authenticate(userName, authenticationRequest.getPassword());



		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(userName);

		final String token = jwtTokenUtil.generateToken(userDetails);
		User user=userService.getUserByEmail(authenticationRequest.getEmail());
		user.setPassword(null);
		return ResponseEntity.ok(ImmutableJwtResponseDto.builder().token(token).user(user).build());
	 

	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		
	}

	
	
	

}
