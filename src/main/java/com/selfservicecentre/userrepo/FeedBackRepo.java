package com.selfservicecentre.userrepo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.selfservicecentre.entity.Feedback;

public interface FeedBackRepo extends JpaRepository<Feedback, Integer> {

}
