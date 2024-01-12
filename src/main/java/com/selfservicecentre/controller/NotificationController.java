package com.selfservicecentre.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfservicecentre.entity.BillInvoice;
import com.selfservicecentre.entity.Notification;
import com.selfservicecentre.service.AdminSerice;
import com.selfservicecentre.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/notify")
@RequiredArgsConstructor
public class NotificationController {
	
	private final NotificationService ns;
	
	@GetMapping("{mno}")
	public List<Notification> billHistory(@PathVariable String mno){
		System.out.println("****************************************************");
		System.out.println(mno);
		return  ns.getNotification(Long.parseLong(mno));
	}
	
	@DeleteMapping("{id}")
	public void deleteNotification(@PathVariable String id) {
		System.out.println("this is from the delete notification "+id);
		ns.deleteNotify(Integer.parseInt(id));
	}
	
	

	
	
}
