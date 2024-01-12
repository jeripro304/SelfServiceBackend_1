package com.selfservicecentre.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.selfservicecentre.entity.BillingStat;
import com.selfservicecentre.entity.Status;
import com.selfservicecentre.service.AdminSerice;
import com.selfservicecentre.service.UserBillStatService;
import com.selfservicecentre.userrepo.BillingStatRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/billstat")
@RequiredArgsConstructor
public class BillStatusController {
	
	private final UserBillStatService  billService;

	@CrossOrigin(origins="http://localhost:4200")
	@GetMapping("{mno}")
	public BillingStat getUserAcoount(@PathVariable String mno) {
		System.out.println(mno);
		
		return billService.getUser(Long.parseLong(mno));
	}
	
	@CrossOrigin(origins="http://localhost:4200")
	@PutMapping("/updateGb")
	public void updateGb(@RequestBody BillingStat bs) {
		System.out.println(bs.getTodayDate());
		billService.userUpdate(bs.getUserStatId(),bs.getTodayDate(),bs.getAvailableGB());
		System.out.println(bs);
		
	}
	
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping("/payment")
	public Status payment(@RequestBody String mobileno) {
		System.out.println("from the payment "+mobileno);
		String msg= billService.paymentUpdate(Long.parseLong(mobileno));
		return new Status("payment done");
	}
	
	

	
	
	
}
