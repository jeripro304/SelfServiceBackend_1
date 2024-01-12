package com.selfservicecentre.userrepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BillInvoice extends JpaRepository<com.selfservicecentre.entity.BillInvoice, String> {
	
	List<com.selfservicecentre.entity.BillInvoice> findBymobileno(Long mobileno);
}
