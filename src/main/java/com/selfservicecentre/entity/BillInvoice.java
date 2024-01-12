package com.selfservicecentre.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
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
public class BillInvoice {
	
	@Id
	private String billNo;
	private Integer planAmount;
	private Long mobileno;
	private Date todayDate;
	private Integer totalAmount;
	private Integer previousAmount;
	private Integer penalty;
	

}
