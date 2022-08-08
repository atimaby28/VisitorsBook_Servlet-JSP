package com.visitor.visitorsbook.model.service;

import com.visitor.visitorsbook.model.VisitorDto;
import com.visitor.visitorsbook.model.dao.VisitorDao;
import com.visitor.visitorsbook.model.dao.VisitorDaoImpl;

public class VisitorServiceImpl implements VisitorService {
	
	private static VisitorService visitorService = new VisitorServiceImpl();
	private VisitorDao visitorDao;
	
	private VisitorServiceImpl() {
		visitorDao = VisitorDaoImpl.getVisitorDao();
	}
	
	public static VisitorService getVisitorService() {
		return visitorService;
	}
	
	@Override
	public int idCheck(String id) throws Exception {
		return visitorDao.idCheck(id);
	}

	@Override
	public void registerVisitor(VisitorDto visitorDto) throws Exception {
		//validation check
		visitorDao.registerVisitor(visitorDto);
	}

	@Override
	public VisitorDto login(String id, String pass) throws Exception {
		return visitorDao.login(id, pass);
	}

}
