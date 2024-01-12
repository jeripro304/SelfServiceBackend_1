package com.selfservicecentre.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.selfservicecentre.entity.DataUsage;
import com.selfservicecentre.service.AdminSerice;
import com.selfservicecentre.service.DataUsageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/datausage")
@RequiredArgsConstructor
public class DataUsageController {
	
	private final DataUsageService ds;
	
	@CrossOrigin(origins="http://localhost:4200")
	@PostMapping
	public void updateData(@RequestBody DataUsage du) {
		System.out.println("----------------------------------------");
		System.out.println(du);
		ds.addDataUsage(du);	
	}
	@CrossOrigin(origins="http://localhost:4200")
	@GetMapping("{mno}")
	public List<DataUsage> getDate(@PathVariable String mno) {
		System.out.println("-------from the data get message------------");
		return ds.getData(Long.parseLong(mno));
	}
	
	

}
