package com.selfservicecentre.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.selfservicecentre.entity.CallDet;
import com.selfservicecentre.userrepo.CallLog;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MobileService {

	private final CallLog callDB;
	
	public void addCall(CallDet cd) {
		callDB.save(cd);
	}
	
	public List<CallDet> getCallList(String phno){
		List<CallDet> cList= callDB.findByfromMobile(phno);
		System.out.println(cList);
		return cList;
	}
	
}
