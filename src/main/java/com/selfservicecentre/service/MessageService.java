package com.selfservicecentre.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.selfservicecentre.entity.Message;
import com.selfservicecentre.userrepo.MessageRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final MessageRepo mr;
	
	public String updateMsg(Message m) {
		Long mno=m.getFromMobile();
		String disp;
		List<Message> mList=mr.findByfromMobile(mno);
		int totalLength = mList.size();
		System.out.println(totalLength);
		if(totalLength<30) {
			Integer availableMessages=2999-totalLength;
			disp="Message Sent , Total messages availabe "+availableMessages;
			mr.save(m);
		}
		else {
			disp="Message Limit Reached";
		}
		return disp;
	}
	
	public List<Message> getMessage(Long mno){
		List<Message> mList= mr.findByfromMobile(mno);
		return mList;
		
	}
}
