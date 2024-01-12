package com.selfservicecentre.userrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selfservicecentre.entity.User;

public interface AdminRepository extends JpaRepository<User, Integer> {
	
	User findByaccNo(String accno);

}
