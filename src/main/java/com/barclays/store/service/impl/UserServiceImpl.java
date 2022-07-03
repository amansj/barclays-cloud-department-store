package com.barclays.store.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.barclays.store.commons.PasswordEncrypt;
import com.barclays.store.entity.User;
import com.barclays.store.execption.UserException;
import com.barclays.store.repository.UserRepository;
import com.barclays.store.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User getUserByEmail(String email) {
		
		return userRepository.getUserByEmail(email);
	}

	@Override
	public User getUser(UUID userId) throws UserException{
		Optional<User> userOpt=userRepository.findById(userId.toString());
		if(userOpt.isPresent()) {
			User user=userOpt.get();
			return user;
		}
		throw new UserException(HttpStatus.NOT_FOUND, "User not found");
	}

	@Override
	public User saveUser(User user) {
		if(userRepository.getUserByEmail(user.getEmail())!=null) {
			throw new UserException(HttpStatus.CONFLICT,"User already registered");
		}
		user.setId(UUID.randomUUID().toString());
		user.setPassword(PasswordEncrypt.hashPassword(user.getPassword()));
		user=userRepository.save(user);
		user.setPassword(null);
		
		return user;
	}

}
