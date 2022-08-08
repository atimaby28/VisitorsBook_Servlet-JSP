package com.visitor.visitorsbook.model.dao;

import java.util.List;

import com.visitor.visitorsbook.model.VisitorsBookDto;

public interface VisitorsBookDao {
	void registerArticle(VisitorsBookDto visitorsBookDto) throws Exception;
	List<VisitorsBookDto> listArticle(String key, String word) throws Exception;
	VisitorsBookDto getArticle(int articleNo) throws Exception;
	void updateArticle(VisitorsBookDto visitorsBookDto) throws Exception;
	void deleteArticle(int articleNo) throws Exception;
}
