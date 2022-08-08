package com.visitor.visitorsbook.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.visitor.util.DBUtil;
import com.visitor.visitorsbook.model.VisitorsBookDto;

public class VisitorsBookDaoImpl implements VisitorsBookDao {

	private static VisitorsBookDao visitorsBookDao = new VisitorsBookDaoImpl();
	
	private DBUtil dbUtil;
	
	private VisitorsBookDaoImpl() {
		dbUtil = DBUtil.getInstance();
	}
	
	public static VisitorsBookDao getVisitorsBookDao() {
		return visitorsBookDao;
	}


	@Override
	public void registerArticle(VisitorsBookDto visitorsBookDto) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder registerArticle = new StringBuilder();
			registerArticle.append("insert into visitorsbook (visitorid, subject, content, regtime) \n");
			registerArticle.append("values (?, ?, ?, now())");
			pstmt = conn.prepareStatement(registerArticle.toString());
//			pstmt.setString(1, visitorid);
			pstmt.setString(1, visitorsBookDto.getVisitorId());
			pstmt.setString(2, visitorsBookDto.getSubject());
			pstmt.setString(3, visitorsBookDto.getContent());
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}

	}

	@Override
	public List<VisitorsBookDto> listArticle(String key, String word)  throws Exception {
		List<VisitorsBookDto> list = new ArrayList<VisitorsBookDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder listArticle = new StringBuilder();
			listArticle.append("select b.articleno, b.visitorid, b.subject, b.content, b.regtime, v.visitorname \n");
			listArticle.append("from visitorsbook b, visitors v \n");
			listArticle.append("where b.visitorid = v.visitorid \n");
			if(!word.isEmpty()) {
				if(key.equals("visitorid"))
					listArticle.append("and b.visitorid = ? \n");
				else if(key.equals("subject"))
					listArticle.append("and b.subject like ? \n");
			}
			listArticle.append("order by b.articleno desc \n");
			pstmt = conn.prepareStatement(listArticle.toString());
			if(key.equals("visitorid"))
				pstmt.setString(1, word);
			else if(key.equals("subject"))
				pstmt.setString(1, "%" + word + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				VisitorsBookDto visitorsBookDto = new VisitorsBookDto();
				visitorsBookDto.setArticleNo(rs.getInt("articleno"));
				visitorsBookDto.setVisitorId(rs.getString("visitorid"));
				visitorsBookDto.setVisitorName(rs.getString("visitorname"));
				visitorsBookDto.setSubject(rs.getString("subject"));
				visitorsBookDto.setContent(rs.getString("content"));
				visitorsBookDto.setRegTime(rs.getString("regtime"));
				
				list.add(visitorsBookDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return list;
	}

	@Override
	public VisitorsBookDto getArticle(int articleNo) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs=null;
		VisitorsBookDto visitorsBookDto=null;
		
		try {
			conn = dbUtil.getConnection();
			StringBuilder registerArticle = new StringBuilder();
			registerArticle.append("select articleno, visitorid, subject, content, regtime \n ");
			registerArticle.append("from visitorsbook \n");
			registerArticle.append("where articleno=?");
			pstmt = conn.prepareStatement(registerArticle.toString());
			pstmt.setInt(1, articleNo);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				visitorsBookDto = new VisitorsBookDto();
				visitorsBookDto.setArticleNo(rs.getInt("articleno"));
				visitorsBookDto.setVisitorId(rs.getString("visitorid"));
				visitorsBookDto.setSubject(rs.getString("subject"));
				visitorsBookDto.setContent(rs.getString("content"));
				visitorsBookDto.setRegTime(rs.getString("regtime"));
			}
		} finally {
			dbUtil.close(pstmt, conn);
		}
		
		return visitorsBookDto;
	}

	@Override
	public void updateArticle(VisitorsBookDto visitorsBookDto) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder registerArticle = new StringBuilder();
			registerArticle.append("update visitorsbook set subject=?, content=? ");
			registerArticle.append("where articleno=?");
			pstmt = conn.prepareStatement(registerArticle.toString());
			pstmt.setString(1, visitorsBookDto.getSubject());
			pstmt.setString(2, visitorsBookDto.getContent());
			pstmt.setInt(3, visitorsBookDto.getArticleNo());
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}
	}

	@Override
	public void deleteArticle(int articleNo) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder registerArticle = new StringBuilder();
			registerArticle.append("delete from visitorsbook \n");
			registerArticle.append("where articleno = ?");
			pstmt = conn.prepareStatement(registerArticle.toString());
			pstmt.setInt(1, articleNo);
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}

	}

}
