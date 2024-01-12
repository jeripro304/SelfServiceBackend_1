package com.selfservicecentre.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.selfservicecentre.dto.RequestUser;
import com.selfservicecentre.entity.Status;
import com.selfservicecentre.service.AdminSerice;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final AdminSerice userservice;
	
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping
	public Status addUser(@RequestBody RequestUser  ru) {
		System.out.println(ru);
		String msg=userservice.addUser(ru);
		return  new Status(msg);
	}
	
  
	

}
