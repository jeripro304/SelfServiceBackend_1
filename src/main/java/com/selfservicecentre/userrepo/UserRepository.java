package com.selfservicecentre.userrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selfservicecentre.entity.ProSync_User;
import com.selfservicecentre.entity.User;

public interface UserRepository extends JpaRepository<ProSync_User, String> {

	//in userRegistered DB
	List<ProSync_User> findByaccNo(String accno);
	
	ProSync_User findBymobileno(Long mobileno);
}
