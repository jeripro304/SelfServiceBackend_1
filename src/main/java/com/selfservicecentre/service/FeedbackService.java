package com.selfservicecentre.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.selfservicecentre.entity.Feedback;
import com.selfservicecentre.userrepo.FeedBackRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {

	private final FeedBackRepo fRepo;
	
	public void setFeedback(Feedback f) {
		fRepo.save(f);
	}
	
	public List<Feedback> getAllFeed(){
		return fRepo.findAll();
	}
}
