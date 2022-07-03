package com.barclays.store.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.barclays.store.commons.PasswordEncrypt;
import com.barclays.store.entity.User;
import com.barclays.store.execption.UserException;
import com.barclays.store.repository.UserRepository;
import com.barclays.store.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;
	
	private  User entityUser;

	private static UUID userId = UUID.randomUUID();
	
	@BeforeEach
	public void setUp() {
		
		entityUser = new User();
		entityUser.setId(userId.toString());
		entityUser.setFirstName("Aman");
		entityUser.setLastName("Singh");
		entityUser.setEmail("amansingh@test.com");
		entityUser.setPhone("9474992781");
		entityUser.setPassword(PasswordEncrypt.hashPassword("shah@123"));


	}
	
	@Test
	public void testSaveUserifUserNotExist() {
		User user=new User();
		user.setFirstName("Aman");
		user.setLastName("Singh");
		user.setEmail("amansingh@test.com");
		user.setPhone("9474992781");
		user.setPassword(PasswordEncrypt.hashPassword("shah@123"));
		
		when(userRepository.getUserByEmail(user.getEmail())).thenReturn(null);
		
		when(userRepository.save(ArgumentMatchers.any())).thenReturn(entityUser);
	
	   
		User responseUser=userService.saveUser(user);
		assertNotNull(responseUser);
		assertNull(responseUser.getPassword());
		
		
		
	}
	
	@Test
	public void testSaveUserifUserExist() {
		User user=new User();
		user.setFirstName("Aman");
		user.setLastName("Singh");
		user.setEmail("amansingh@test.com");
		user.setPhone("9474992781");
		user.setPassword(PasswordEncrypt.hashPassword("shah@123"));
		
		when(userRepository.getUserByEmail(user.getEmail())).thenReturn(entityUser);
		
	
		UserException exception = assertThrows(UserException.class, ()->userService.saveUser(user));
		assertEquals(exception.getStatus(), HttpStatus.CONFLICT);
		
		
		
	}
	
	@Test
	public void testGetUserIfUserExist() {
		when(userRepository.findById(userId.toString())).thenReturn(Optional.of(entityUser));
		
		User user=userService.getUser(userId);
		
		assertNotNull(user);
	}
	
	@Test
	public void testGetUserIfUserNotExist() {
		when(userRepository.findById(userId.toString())).thenReturn(Optional.empty());
		
		UserException exception = assertThrows(UserException.class, ()->userService.getUser(userId));
		
		assertEquals(exception.getStatus(), HttpStatus.NOT_FOUND);
	}
	
	

}
