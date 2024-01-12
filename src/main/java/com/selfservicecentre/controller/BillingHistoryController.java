package com.selfservicecentre.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfservicecentre.entity.BillInvoice;
import com.selfservicecentre.service.AdminSerice;
import com.selfservicecentre.service.BillingHistoryService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/billhistory")
@RequiredArgsConstructor
public class BillingHistoryController {
	
	private final BillingHistoryService bs;
	
	@GetMapping("{mno}")
	public List<BillInvoice> billHistory(@PathVariable String mno){
		
		System.out.println(mno);
		return  bs.getBillInvoice(Long.parseLong(mno));
	}
 
}
