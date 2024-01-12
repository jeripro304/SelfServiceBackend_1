package com.selfservicecentre.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfservicecentre.entity.CallDet;
import com.selfservicecentre.service.MobileService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/call")
@RequiredArgsConstructor
public class CallsController {
	
	private final MobileService ms;
	
	@PostMapping
	public void addCall(@RequestBody CallDet c) {
		System.out.println(c);
		ms.addCall(c);
	}
	
	@GetMapping("{mobileno}")
	public List<CallDet>  callList(@PathVariable String mobileno){
		System.out.println(mobileno +"from the get mapping");
		return ms.getCallList(mobileno);
	}
	

}
