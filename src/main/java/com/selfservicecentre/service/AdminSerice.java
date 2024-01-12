package com.selfservicecentre.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.selfservicecentre.dto.RequestUser;
import com.selfservicecentre.entity.User;
import com.selfservicecentre.userrepo.AdminRepository;

import lombok.RequiredArgsConstructor;

//Admin Service
@Service
@RequiredArgsConstructor
public class AdminSerice {
	
	private final AdminRepository adminRepo;
	
	
	public String addUser(RequestUser requser ) {
		String msg;
//		List<User> allAccountNumbers =userRepo.findAll();
//		String accountNo=accontNumberGenerate();
		
//		 List<String> allaccno = allAccountNumbers.stream()
//	                .map(User::getAccNo) //Modify to match your actual property name
//	                .collect(Collectors.toList());
//		 for (String i:allaccno) {
//			 if (i==accountNo) {
//				 System.out.println("Account number already present");
//			 }
//			 else System.out.println("This is new");
//		 }
//		
		
		
		
		User u=User.builder().accNo(accontNumberGenerate()).fname(requser.getFname())
				.lname(requser.getLname())
				.mobileno(requser.getMobileno())
				.email(requser.getEmail())
				.dob(requser.getDob())
				.plan(requser.getPlan())
				.address(requser.getAddress())
				.city(requser.getCity())
				.state(requser.getState())
				.zip(requser.getZip())
				.service(requser.getService()).build();
		System.out.println("this is at the add user in db");
		System.out.println(u);
		try {
			adminRepo.save(u);
			System.out.println("from the get admin add acoount in mail :"+u);
			sendMail(u.getEmail(),u.getAccNo(),u.getFname());
			msg="created";
		} catch (UnsupportedEncodingException | MessagingException e) {
			msg="error";
			System.out.println(e);
		}
		System.out.println(msg);
		return msg;
		
	}
	
	public String accontNumberGenerate() {
		Random rand=new Random();
		long accno=  (long) (Math.pow(11, 11)+ rand.nextInt(99));
		String accountNumber= Long.toString(accno);
		System.out.println(accountNumber);
		return accountNumber;
	}
	
	public void sendMail(String usermail,String accno,String fname) throws UnsupportedEncodingException, MessagingException {
		System.out.println("Outlook Email Start");

        String smtpHostServer = "smtp.office365.com";
        final String emailID = "prosync80329@outlook.com"; //Outlook email address
        final String password = "Jerish@2002"; // outlook account password
        String toEmail = usermail;
        String subject = "Successful Registration and Account Number";
        String messageBody = "Dear "+fname+",\r\n"
        		+ "\r\n"
        		+ "We are thrilled to inform you that your registration with ProSync has been successfully completed. As a valued member of our community, we would like to provide you with your unique account number for future reference and transactions.\r\n"
        		+ "\r\n"
        		+ "Your Account Number: "+accno+"\r\n"
        		+ "Thank you for choosing ProSync. We look forward to serving you, and we appreciate your trust in us.\r\n"
        		+ "\r\n"
        		+ "Best regards,\r\n"
        		+ "\r\n"
        		+ "ProSync\r\n"
        		+ "9876543212"; // type mail body
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHostServer);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailID, password);
            }
        });

        sendEmail(session, emailID, subject, messageBody,toEmail);
	}
	public static void sendEmail(Session session, String fromEmail, String subject, String body,String toEmail) throws MessagingException, UnsupportedEncodingException{
        
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(fromEmail, "ProSync"));
            msg.setReplyTo(InternetAddress.parse(toEmail, false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);
            System.out.println("Email Sent Successfully!!");
        
        
	}
	
	
	
	

}
