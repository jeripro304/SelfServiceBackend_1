package com.selfservicecentre.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.selfservicecentre.entity.Notification;
import com.selfservicecentre.userrepo.NotificationRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final NotificationRepo nRepo;
	
	public List<Notification> getNotification(Long mobileno){
		List<Notification> nList= nRepo.findBymobileno(mobileno);
		System.out.println(nList);
		return nList;
	}
	
	public void deleteNotify(Integer id) {
		nRepo.deleteById(id);
	}
}
