package com.selfservicecentre.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfservicecentre.entity.Feedback;
import com.selfservicecentre.service.AdminSerice;
import com.selfservicecentre.service.FeedbackService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {
	private final FeedbackService fs;

	@PostMapping
	public  void addFeedback(@RequestBody Feedback f) {
//		System.out.println(f);
		fs.setFeedback(f);
	}
	
	@GetMapping 
	public List<Feedback> getAllData(){
		return fs.getAllFeed();
	}
}
