package com.selfservicecentre.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfservicecentre.entity.ProSync_User;
import com.selfservicecentre.entity.Status;
import com.selfservicecentre.service.AdminSerice;
import com.selfservicecentre.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/checkuser")
	  public Status checkUser(@RequestBody String checkvaliduser) {
	        System.out.println("Received checkvaliduser: " + checkvaliduser);
	        String check=userService.isUserExistsInAdminDB(checkvaliduser);
	        System.out.println("User is "+check);
	        return new Status(check);
	        
	    }
//	getting otp value from the user entered   this is Registeration Process
	@PostMapping("/sendotp")
	public Status checkOtp(@RequestBody String otp) {
		System.out.println(otp);
		String msg=userService.ValidateOtp((otp));
		return new Status(msg);
	}
	
//	this is login process
	@PostMapping("/loginphno")
	public Status getLoginPhNo(@RequestBody String phno) {
		String msg= userService.checkNumber(phno);
		return new Status(msg);
	}
	
	@PostMapping("/loginotpcheck")
	public Status checkLoginOtp(@RequestBody String otp) {
		System.out.println(otp);
		String msg=userService.loginCheckOtp(otp);
		return new Status(msg);
	}
	
	@GetMapping("{mno}")
	public ProSync_User getProSyncUser(@PathVariable String mno) {
		ProSync_User us=userService.getUser(Long.parseLong(mno));
		return  us;
	}
	
	@GetMapping("/getalluser")
	public List<ProSync_User> getAllUser(){
		return userService.getAllUserDetails();
		
	}

}
