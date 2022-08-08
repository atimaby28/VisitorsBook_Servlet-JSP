package com.visitor.visitorsbook.model.service;

import java.util.List;

import com.visitor.visitorsbook.model.VisitorsBookDto;
import com.visitor.visitorsbook.model.dao.VisitorsBookDao;
import com.visitor.visitorsbook.model.dao.VisitorsBookDaoImpl;

public class VisitorsBookServiceImpl implements VisitorsBookService {
	
	private static VisitorsBookService visitorsBookService = new VisitorsBookServiceImpl();
	
	private VisitorsBookDao visitorsBookDao;
	
	private VisitorsBookServiceImpl() {
		visitorsBookDao = VisitorsBookDaoImpl.getVisitorsBookDao();
	}

	public static VisitorsBookService getVisitorsBookService() {
		return visitorsBookService;
	}
	
	@Override
	public void registerArticle(VisitorsBookDto visitorsBookDto)  throws Exception {
		visitorsBookDao.registerArticle(visitorsBookDto);
		
	}

	@Override
	public List<VisitorsBookDto> listArticle(String key, String word) throws Exception {
		key = key == null ? "" : key.trim();
		word = word == null ? "" : word.trim();
		return visitorsBookDao.listArticle(key, word);
	}

	@Override
	public VisitorsBookDto getArticle(int articleNo) throws Exception {
		return visitorsBookDao.getArticle(articleNo);
	}

	@Override
	public void updateArticle(VisitorsBookDto visitorsBookDto) throws Exception {
		visitorsBookDao.updateArticle(visitorsBookDto);
		
	}

	@Override
	public void deleteArticle(int articleNo) throws Exception {
		visitorsBookDao.deleteArticle(articleNo);
	}
	
}
