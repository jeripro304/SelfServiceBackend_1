package com.selfservicecentre.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfservicecentre.entity.Message;
import com.selfservicecentre.entity.Status;
import com.selfservicecentre.service.AdminSerice;
import com.selfservicecentre.service.MessageService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/msg")
@RequiredArgsConstructor
public class MessageController {
	
	private final MessageService mService;
	
	@PostMapping
	public Status addMsg(@RequestBody Message m) {
		System.out.println(m);
		
		return new Status(mService.updateMsg(m));
	}
	
	@GetMapping("{mno}")
	public List<Message> getMsg(@PathVariable String mno){
		return mService.getMessage(Long.parseLong(mno));
	}

}
