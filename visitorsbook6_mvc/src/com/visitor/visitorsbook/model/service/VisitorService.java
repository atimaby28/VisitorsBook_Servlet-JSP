package com.visitor.visitorsbook.model.service;

import com.visitor.visitorsbook.model.VisitorDto;

public interface VisitorService {
	
	int idCheck(String id) throws Exception;
	void registerVisitor(VisitorDto visitorDto) throws Exception;
	VisitorDto login(String id, String pass) throws Exception;
	
//	VisitorDto getVisitor(String id) throws Exception;
//	void updateVisitor(VisitorDto visitorDto) throws Exception;
//	void deleteVisitor(String id) throws Exception;
	
}
