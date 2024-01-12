package com.selfservicecentre.service;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.selfservicecentre.entity.ProSync_User;
import com.selfservicecentre.entity.User;
import com.selfservicecentre.userrepo.AdminRepository;
import com.selfservicecentre.userrepo.UserRepository;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.text.DecimalFormat;
import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final AdminRepository adminRepo;
	private final UserRepository userRepo;
	private final UserBillStatService billservice;
	private static String otp;
	private static ProSync_User userReg;
	public static final String ACCOUNT_SID = "ACffbe700df2e1cb11a789782a3b4e45fd";
	public static final String AUTH_TOKEN = "fbe1e6b6bd5a36575afc7bea6aab3aa0";
	
	public String isUserExistsInAdminDB(String accno) {
		  User accNo=adminRepo.findByaccNo(accno);
		  
		  System.out.println(accNo);
		  String checkmsg;
		  boolean check=accNo==null;
		  
	      if (!check) {
	        	checkmsg="uservalid";
	        	if (checkInUserDb(accno)) {
	        		checkmsg="User Already Registered";
	        		
	        	}	
	        	else {
	        		otp=(generationOtp(accNo.getMobileno()));
	        		ProSync_User pro= addUsertoProSync(accNo);
	        		userReg=pro;
	        		
	        		System.out.println("otp is generated  "+otp);
//	        		checkmsg="User Registered";
	        	}
	        }
	       else {
	        	checkmsg="notvalid";
	       }
	       System.out.println(checkmsg);
	       return checkmsg;
	}
	
	public String ValidateOtp(String otpGet) {
		String otpStoredVal=otp;
		String msg;
		if (otpStoredVal.equals(otpGet) ) {
			msg="User Registered";
			userRepo.save(userReg);
			billservice.addBillStat(userReg);
			
		}
		else {
			msg="Invalid Otp";
		}
		return msg;
		
	}
	
	//user check in user DB
	 public boolean checkInUserDb(String accNo) {
		List<ProSync_User> registeredUser=userRepo.findByaccNo(accNo);
		boolean checkUserDb;
		if (registeredUser.isEmpty()) checkUserDb=false;
		else checkUserDb=true;
		return checkUserDb;
	 }
//	generation of otp
	 public String generationOtp(Long phno){
		Random rand=new Random();
		long otp=  (long) (Math.pow(10, 5) + rand.nextInt(93340));
		String GeneratedOtp= Long.toString(otp);
		System.out.println("this is the generate otp and mobile numer "+GeneratedOtp);
		SendOtp(GeneratedOtp, phno);
		return GeneratedOtp;
		
	 }
	 
	 public ProSync_User addUsertoProSync(User u){
		  ProSync_User proSync=ProSync_User.builder()
				 .accNo(u.getAccNo()).fname(u.getFname()).lname(u.getLname())
				 .mobileno(u.getMobileno()).email(u.getEmail())
				 .dob(u.getDob()).plan(u.getPlan()).address(u.getAddress())
				 .city(u.getCity()).state(u.getState()).zip(u.getZip())
				 .service(u.getService()).build();
		 return proSync;
	 }
	 
	 public void SendOtp(String otp,Long phno) {
		    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		    Message message = Message.creator(
		      new com.twilio.type.PhoneNumber("+91"+phno.toString()),
		      new com.twilio.type.PhoneNumber("+17163126354"),
		      "Your  One Time Password is  "+otp)
		    .create();
		    System.out.println(message.getSid());
	 }
	 
	 //login service
	 public String checkNumber(String phno) {
		 System.out.println("in login service");
		 String msg;
		 Long mobileno=Long.parseLong(phno);
		 System.out.println(mobileno);
		 ProSync_User validUser=userRepo.findBymobileno(mobileno);
		 System.out.println(validUser);
		 boolean customerCheck=validUser==null;
		 if (!customerCheck) {
			 msg="UserValid";
			 otp=generationOtp(validUser.getMobileno());
		 }
		 else {
			 msg="UserInvalid";
		 }
		 return msg;
	 }
//	 login otp Check Service
	 public String loginCheckOtp(String otpno) {
		 String msg;
		 System.out.println(otp);
		 if (otp.equals(otpno)) {
			 msg="ValidOtp";
		 }
		 else {
			 msg="InvalidOtp";
		 }
		 return msg;
		 
		 
	 }
	 
//	 getting unique user details
	 public ProSync_User getUser(Long mobno){
		 ProSync_User proList=userRepo.findBymobileno(mobno);
		 return proList;
	 }
	 
	 public List<ProSync_User> getAllUserDetails(){
		 return userRepo.findAll();
	 }
	 
	 
	 

}
