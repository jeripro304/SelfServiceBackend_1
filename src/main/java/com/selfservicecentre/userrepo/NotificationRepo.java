package com.selfservicecentre.userrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selfservicecentre.entity.Notification;

public interface NotificationRepo extends JpaRepository<Notification, Integer> {

	List<Notification> findBymobileno(Long mobileno);
}
