package com.selfservicecentre.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.selfservicecentre.entity.BillingStat;
import com.selfservicecentre.entity.Notification;
import com.selfservicecentre.userrepo.NotificationRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoitificationService {

	private final NotificationRepo nRepo;
	
	public void sendNotification(Long mobileno,Double percent,Integer userid) {
		Notification notify;
		String date=todaysDate();
		System.out.println("*******************this is the percent"+percent);
		if (percent>85 && percent <92) {
			try {
				notify=Notification.builder().nid(1+userid).mobileno(mobileno).todayDate(date).notification("Your data is only 90% left").build();
				nRepo.save(notify);
			}
			catch(Exception e) {
				System.out.println("Aldready updated");
			}
			
		}
		else if(percent>98) {
			try {
				notify=Notification.builder().nid(2).mobileno(mobileno).todayDate(date).notification("Your data is 100% completed").build();
				nRepo.save(notify);
			}
			catch(Exception e) {
				System.out.println("Aldready updated");
			}
			
		}
		
	}
	
	public void sendnotification1(Long mobileno,Integer id) {
		Notification notify;
		String date=todaysDate();
		try {
			notify=Notification.builder().nid(id).mobileno(mobileno).todayDate(date).notification("You have not paid till your due date hence penaly amount is added,Please pay it soon")
					.build();
			nRepo.save(notify);
		}
		catch (Exception e) { 
			System.out.println("Aldready exists");
		}
	}
	
	public void sendnotification2(Long mobileno,Integer id) {
		Notification notify;
		String date=todaysDate();
		try {
			notify=Notification.builder().nid(id).mobileno(mobileno).todayDate(date).notification("You have not paid till your next bill Cycle. penalty and next bill amount is added,Please pay it soon")
					.build();
			nRepo.save(notify);
		}
		catch (Exception e) { 
			System.out.println("Aldready exists");
		}
	}
	
	

	
//	todays date in string
	public String todaysDate() {
        LocalDate today = LocalDate.now();
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
        String formattedDate = today.format(dateFormatter);
        System.out.println("Today's date: for notification " + formattedDate);
		return formattedDate;
	}
}
