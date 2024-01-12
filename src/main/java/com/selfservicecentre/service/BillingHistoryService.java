package com.selfservicecentre.service;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.selfservicecentre.entity.BillingStat;
import com.selfservicecentre.userrepo.BillInvoice;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillingHistoryService {
	
	private final BillInvoice invoiceRepo;
	

	
	public void addBillInvoice(BillingStat bt) {
		System.out.println("into adding the bill invoice"+bt);
		com.selfservicecentre.entity.BillInvoice billInvoice=com.selfservicecentre.entity.BillInvoice.builder()
				.billNo(generateBillId()).mobileno(bt.getMobileno()).totalAmount(bt.getTotalBillAmount())
				.planAmount(bt.getPlanAmount()) .todayDate(bt.getTodayDate()).previousAmount(bt.getPreviousAmount()).penalty(bt.getPenalt()).build();
		
		invoiceRepo.save(billInvoice);
	}
	
	public String generateBillId() {
			int length=15;
	        StringBuilder sb = new StringBuilder();
	        Random random = new Random();

	        for (int i = 0; i < length; i++) {
	            // Generate a random digit in the range 0-15 (0-F in hexadecimal)
	            int digit = random.nextInt(16);
	            // Convert the digit to a hexadecimal character
	            char hexChar = Character.forDigit(digit, 16);
	            sb.append(hexChar);
	        }

	        return sb.toString();
	    }
	
	public List<com.selfservicecentre.entity.BillInvoice> getBillInvoice(Long mobileno){
		List<com.selfservicecentre.entity.BillInvoice> billinvoice=invoiceRepo.findBymobileno(mobileno);
		System.out.println(billinvoice);
		return billinvoice;
	}
	
	
	
	
}
