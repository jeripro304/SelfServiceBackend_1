package com.selfservicecentre.userrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selfservicecentre.entity.DataUsage;

public interface DataUsageRepo extends JpaRepository<DataUsage, Integer> {
	
	List<DataUsage> findBymobileno(Long mobileno);

}
