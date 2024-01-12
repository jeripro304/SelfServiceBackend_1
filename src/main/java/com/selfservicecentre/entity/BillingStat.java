package com.selfservicecentre.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BillingStat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userStatId;
	private String name;
	private Long mobileno; 
	private String acccountno;
	private Integer planAmount;
	private Double toatalGB;
	private Double  availableGB;
	private Date purchasedDate;
	private Date todayDate;
	private Date expiryDate;
	private Integer expiringDays;
	private String status;
	private Integer totalBillAmount;
	private Integer penalt;         //this is to calculate the peanalty amount
	private Integer previousAmount;
	private Date nextDueDate;
	private Integer gstAmount;
	private Integer monthsUnpaid;
	private Integer updateCheck;
	
	
	
	
		

}
