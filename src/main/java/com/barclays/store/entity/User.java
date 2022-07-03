package com.barclays.store.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5526402482269505087L;
	
	@Id
	@Column(name = "id")
	private String id;

	
	@Column(name = "firstName")
	private String firstName;

	
	@Column(name = "lastName")
	private String lastName;

	
	@Column(name = "email")
	private String email;
	
	
	@Column(name = "password")
	private String password;

	
	@Column(name = "phone")
	private String phone;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


}
