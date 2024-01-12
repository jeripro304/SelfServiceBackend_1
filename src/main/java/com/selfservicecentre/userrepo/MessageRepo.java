package com.selfservicecentre.userrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selfservicecentre.entity.Message;

public interface MessageRepo extends JpaRepository<Message, Integer> {

	List<Message> findByfromMobile(Long mobileno);
}
