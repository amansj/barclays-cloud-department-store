package com.barclays.store.service;

import java.util.UUID;

import javax.transaction.Transactional;

import com.barclays.store.entity.User;

public interface UserService {
	
	@Transactional
	public User getUserByEmail(String email);
	
	@Transactional
	public User getUser(UUID userId);
	
	@Transactional
	public User saveUser(User user);

}
