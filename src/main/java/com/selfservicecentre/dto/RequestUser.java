package com.selfservicecentre.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestUser {
	
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
