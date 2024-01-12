package com.selfservicecentre.userrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selfservicecentre.entity.CallDet;

public interface CallLog extends JpaRepository<CallDet, Integer> {
	
	List<CallDet> findByfromMobile(String phno);

}
