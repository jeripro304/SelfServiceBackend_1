package com.selfservicecentre.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer uid;
	private String accNo;
	private String fname;
	private String lname;
	private Long mobileno;
	private String email;
	private Date  dob;
	private String plan;
	private String address;
	private String city;
	private String state;
	private Integer zip;
	private String service;
	

}
