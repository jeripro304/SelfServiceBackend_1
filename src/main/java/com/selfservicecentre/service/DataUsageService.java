package com.selfservicecentre.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.selfservicecentre.entity.DataUsage;
import com.selfservicecentre.userrepo.DataUsageRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DataUsageService {
	
	private final DataUsageRepo dRepo;
	
	public void addDataUsage(DataUsage du) {
		dRepo.save(du);
	}
	
	public List<DataUsage> getData(Long mno){
		List<DataUsage> dList= dRepo.findBymobileno(mno);
		return dList;
	}

}
