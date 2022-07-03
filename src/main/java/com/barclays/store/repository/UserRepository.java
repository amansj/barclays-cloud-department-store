package com.barclays.store.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.barclays.store.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	
	@Query("select user from User user where user.email=?1 ")
	public User getUserByEmail(String email);


}
