package com.selfservicecentre.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.selfservicecentre.entity.BillingStat;
import com.selfservicecentre.entity.ProSync_User;
import com.selfservicecentre.userrepo.BillingStatRepository;
import com.selfservicecentre.userrepo.MessageRepo;
import com.selfservicecentre.userrepo.NotificationRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserBillStatService {
	
	private final  BillingStatRepository userBillRepo; 
	private final BillingHistoryService invoiceService;
	private final NoitificationService ns;
	
	private final MessageRepo mRepo;
	
	public void addBillStat(ProSync_User u) {
		System.out.println("from the billing service"+u);
		Integer gst=gstCalc( getPlanAmount(u.getPlan()));
		
		BillingStat bStat=BillingStat.builder().name(u.getFname())
				.mobileno(u.getMobileno()).acccountno(u.getAccNo())
				.planAmount(getPlanAmount(u.getPlan())).toatalGB(planGb(u.getPlan()))
				.availableGB(planGb(u.getPlan())).purchasedDate(planStartDate()).expiringDays(daysRemainingCalc())
				.expiryDate(planEndDate()).todayDate(planStartDate()).status("paid")
				.totalBillAmount(getPlanAmount(u.getPlan())+gst).penalt(0).previousAmount(0).nextDueDate(dueDate()).gstAmount(gst).monthsUnpaid(0).updateCheck(0).build();
		userBillRepo.save(bStat);
		System.out.println("stating the bill service");
		invoiceService.addBillInvoice(bStat);
	}
	
	public BillingStat getUser(Long mNo) {
		Long mobileno=  mNo;
		return userBillRepo.findBymobileno(mobileno);
	}
	
	public BillingStat getUserDetail(String accno) {
		return userBillRepo.findByacccountno(accno);
	} 
	
	
	public void userUpdate(int id,Date toda,Double gb) {
		Date today=userDateTesting();
		BillingStat userDet=userBillRepo.findById(id).get();
		java.sql.Date sqlDate = new java.sql.Date(today.getTime());
		
		Double percent= calcPercent(userDet.getAvailableGB(),userDet.getToatalGB());
		System.out.println("to updat the notification");
		ns.sendNotification(userDet.getMobileno(), percent,userDet.getUserStatId());
		
		Integer remainingDayCheck=userDet.getExpiringDays();
		System.out.println("remining dats: "+remainingDayCheck);
		System.out.println("this is inuserupdate date :"+today);
		Integer daysRemaining=expiringDaycalc(today, userDet.getExpiryDate());
		BillingStat up=null;
		
		System.out.println(sqlDate+"  this is the SQLDate");
		if(userDet.getStatus().equals("paid")) {
			if (userDet.getExpiringDays()==0) {
				System.out.println("into the expiry date 0");
				Integer gstAmount= gstCalc(userDet.getPlanAmount()) ;
				Integer totalBillAmount=userDet.getPlanAmount()+gstAmount;
				System.out.println("Gst amount on first month "+gstAmount);
				up=BillingStat.builder().userStatId(userDet.getUserStatId()).name(userDet.getName()).mobileno(userDet.getMobileno())
						.acccountno(userDet.getAcccountno()).planAmount(userDet.getPlanAmount()).toatalGB(userDet.getToatalGB())
						.availableGB(userDet.getToatalGB()).purchasedDate(purchasedDateUpdate(userDet.getExpiryDate())).todayDate(sqlDate).expiryDate(expiryDateUpdate(userDet.getExpiryDate()))
						.expiringDays(30).status("unpaid").totalBillAmount(totalBillAmount)
						.penalt(0).previousAmount(0).nextDueDate ( dueDate() ).gstAmount(gstAmount).monthsUnpaid(1).updateCheck(0).build();
				userBillRepo.save(up);
				mRepo.deleteAll();
			
			}
			else {
				userBillRepo.updateUser(today,gb, daysRemaining, id);
			}
		}
		else {   // if status is unpaid 
			Integer penaltyDays=penaltyDays(userDet.getTodayDate(),userDet.getPurchasedDate())+1;
			System.out.println("penalty days : "+penaltyDays);
			if (userDet.getUpdateCheck()==0) {
				
//				userBillRepo.updateUser(today,gb, daysRemaining, id);//storing data used in the db
				if (penaltyDays==12) {
//					Integer totalamount= userDet.getPlanAmount()+userDet.getMonthsUnpaid();
					Integer penalty=userDet.getPenalt()+100;
					Integer BillAmount= (userDet.getTotalBillAmount());
//					Integer gst=gstCalc(BillAmount);
					Integer gst =0;
					Integer totalBillAmount= (int) (BillAmount+ gst)+penalty;
					userBillRepo.updateAtFirst(penalty, 0, totalBillAmount, userDet.getUserStatId(),1);
					userBillRepo.updateUser(today,gb, daysRemaining, id);
					ns.sendnotification1(userDet.getMobileno(),3+userDet.getUserStatId());
//					userBillRepo.updatePenalty(totalamount, userDet.getUserStatId());
				}
				else {
					userBillRepo.updateUser(today,gb, daysRemaining, id);
				}
			}
			else if(penaltyDays==30) {
				Integer penalty=userDet.getPenalt()+100;
				Integer monthsUnpaid=userDet.getMonthsUnpaid()+1;
				Integer previousAmounnt =  (userDet.getTotalBillAmount());  // includding gst
				Integer BillAmount= userDet.getTotalBillAmount()+userDet.getPlanAmount();
				Integer totalBillAmount =  (int) (BillAmount+ gstCalc(BillAmount))+penalty;
				
				up=BillingStat.builder().userStatId(userDet.getUserStatId()).name(userDet.getName()).mobileno(userDet.getMobileno())
						.acccountno(userDet.getAcccountno()).planAmount(userDet.getPlanAmount()).toatalGB(userDet.getToatalGB())
						.availableGB(userDet.getToatalGB()).purchasedDate(purchasedDateUpdate(userDet.getExpiryDate())).todayDate(sqlDate).expiryDate(expiryDateUpdate(userDet.getExpiryDate()))
						.expiringDays(30).status("unpaid").totalBillAmount(totalBillAmount)
						.penalt(penalty).previousAmount(previousAmounnt).nextDueDate(dueDate()).gstAmount( gstCalc(totalBillAmount)).monthsUnpaid(monthsUnpaid).updateCheck(0).build();
				userBillRepo.save(up);
				ns.sendnotification2(userDet.getMobileno(), 4+userDet.getUserStatId());
				mRepo.deleteAll();
			}
			else {
				userBillRepo.updateUser(today,gb, daysRemaining,id);
			}
			
		}
		System.out.println("from the user Update  "+up);
	}
	
//expiring days number calculation for current plan
	public Integer expiringDaycalc(Date today,Date expiriydate) {
	     long diffInMillies = expiriydate.getTime() - today.getTime();
	     long daysBetween = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	     System.out.println("days inbetween "+daysBetween);
	     return (int)daysBetween;
	}
	
//	for adding 30 days to it
	public Date expiryDateUpdate(Date addDate) {
	    System.out.println("into expiry date update");
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(addDate);
	    
	    // Add 30 days
	    calendar.add(Calendar.DAY_OF_MONTH, 30);

	    // Convert to java.sql.Date
	    long timeInMillis = calendar.getTimeInMillis();
	    Date updatedDate = new Date(timeInMillis);

	    System.out.println("Updated Date: " + updatedDate);
	    System.out.println("out of expiry date update");

	    return updatedDate;
	}
	
//	for adding the next bill cycle start date
	public Date  purchasedDateUpdate(Date upDate) {
		 System.out.println("into expiry date update");
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(upDate);
		    
		    // Add 30 days
		    calendar.add(Calendar.DAY_OF_MONTH, 1);

		    // Convert to java.sql.Date
		    long timeInMillis = calendar.getTimeInMillis();
		    Date updatedDate = new Date(timeInMillis);

		    System.out.println("Updated Date: " + updatedDate);
		    System.out.println("out of expiry date update");

		    return updatedDate;
	}
// GST calculation
	public Integer gstCalc(Integer amount) {
		Integer gst= (int) (amount*0.18);
		return gst;
	}
	
	
	
	
//	for the initial addition of the expiring days
	public Integer daysRemainingCalc() {
		 LocalDate today = LocalDate.now();
	     LocalDate purchasedDate = today;
	     LocalDate endDate = today.plusDays(30);
	     long daysBetween = ChronoUnit.DAYS.between(purchasedDate, endDate);
	     System.out.println("Days in between  "+daysBetween);
	     return Math.toIntExact(daysBetween);
	}
	

	
//	setting the plan amount
	public Integer getPlanAmount(String plan) {
		Integer planamount;
		if (plan.equals("Plan 399")) {
			planamount=399;
		}
		else if(plan.equals("Plan 599") ) {
			planamount=599;
		}
		else if(plan.equals("Plan 699") ) {
			planamount=699;
		}
		else {
			planamount=899;
		}
		return planamount;
	}
//	gb calculation according to plan
	public Double planGb(String plan) {
		Double gb = null;
		if (plan.equals("Plan 399")) {
			gb=75.00;
		}
		else if(plan.equals("Plan 599")) {
			gb=120.00;
		}
		else if(plan.equals("Plan 699")) {
			gb=180.00;
		}
		else if (plan.equals("Plan 899") ){
			gb=250.00;
		}
		return gb;
	}
//	date 
	public Date planStartDate() {
		 LocalDate today = LocalDate.now();
	     Date sqlDate = Date.valueOf(today);
	     System.out.println("Today's Date (SQL Date): " + sqlDate);
	     return sqlDate;
	        
	}
//	expiry date
	public Date planEndDate() {
		LocalDate localDate = LocalDate.now();
		LocalDate newDate = localDate.plusDays(30);
        Date newSqlDate = Date.valueOf(newDate);
        System.out.println("Initial Date: " + localDate);
        return newSqlDate;
	}
	
//	penaly duration Calculation
	public Integer penaltyDays(Date today,Date purchasedDate) {
		 long diffInMillies = today.getTime() - purchasedDate.getTime();
		 long daysBetween = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		 System.out.println("total penalty days  "+daysBetween);
		 return (int)daysBetween;
	}
	
//	Adding 15 days to days date
	public Date dueDate() {
		LocalDate localDate = LocalDate.now();
		LocalDate newDate = localDate.plusDays(15);
        Date duedate = Date.valueOf(newDate);
        System.out.println("Due Date: " + duedate);
        return duedate;
		
	}
	
//	setting predefined userDate
	public Date userDateTesting() {
		java.sql.Date sqlDate = null;
		String dateString = "2024-01-18"; // Replace with your SQL-formatted date string
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	    try {
	        java.util.Date utilDate = dateFormat.parse(dateString);
	        sqlDate = new java.sql.Date(utilDate.getTime());
	        System.out.println("Parsed SQL Date for user testing " + sqlDate);
	        } 
	    catch (ParseException e) {
	        	 e.printStackTrace();
	    }
		return sqlDate;
	}
	
	
	
//	payment update
	public String paymentUpdate(Long phno) {
		String msg;
		BillingStat billStat= userBillRepo.findBymobileno(phno);
		invoiceService.addBillInvoice(billStat);
		System.out.println(billStat);
		billStat.setGstAmount(0);
		billStat.setPenalt(0);
		billStat.setMonthsUnpaid(0);
		billStat.setPreviousAmount(0);
		billStat.setTotalBillAmount(0);
		billStat.setStatus("paid");
		userBillRepo.save(billStat);
		return "payment done";
	}
	
//	below methods are to send notification to the user
	public Double calcPercent(Double availableGB,Double totalGb) {
		Double calc=100-( ((availableGB/totalGb)*100));
		return calc;
	}
	

}
