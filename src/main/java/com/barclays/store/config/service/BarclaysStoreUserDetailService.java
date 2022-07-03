package com.barclays.store.config.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.barclays.store.service.UserService;


@Service
public class BarclaysStoreUserDetailService implements UserDetailsService{
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
	
		com.barclays.store.entity.User user=userService.getUserByEmail(email);
		if(user==null) {
			throw new UsernameNotFoundException("Not a valid User");
		}
		return new User(email, user.getPassword(),new ArrayList<>());
	}

}
