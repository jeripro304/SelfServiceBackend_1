package com.selfservicecentre.userrepo;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.selfservicecentre.entity.BillingStat;

import jakarta.transaction.Transactional;

public interface BillingStatRepository extends JpaRepository<BillingStat, Integer> {
	
	BillingStat findBymobileno(Long mobileno);
	
	BillingStat findByacccountno(String acccountno);
	
    @Modifying
    @Transactional
    @Query("update BillingStat set  todayDate=?1,availableGB=?2,expiringDays=?3 where userStatId=?4")
	void updateUser(Date todayDate,Double availableGB,Integer days,Integer userStatId);
    
    
    @Modifying
    @Transactional
    @Query("update BillingStat set  penalt=?1 where userStatId=?2")
	void updatePenalty(Integer penalt,Integer id);
    
    @Modifying
    @Transactional
    @Query("update BillingStat set  penalt=?1,gstAmount=?2,totalBillAmount=?3,updateCheck=?4 where userStatId=?5")
    void updateAtFirst(Integer penalt,Integer gst,Integer totalBillAmount,Integer uCheck,Integer userStatId);
    
    

}
