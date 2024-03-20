package com.visitor.visitorsbook.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.visitor.util.DBUtil;
import com.visitor.visitorsbook.model.VisitorDto;

public class VisitorDaoImpl implements VisitorDao {

	private static VisitorDao visitorDao = new VisitorDaoImpl();
	private DBUtil dbUtil;
	
	private VisitorDaoImpl() {
		dbUtil = DBUtil.getInstance();
	}
	
	public static VisitorDao getVisitorDao() {
		return visitorDao;
	}


	@Override
	public int idCheck(String id) throws SQLException {
		int cnt = 1;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dbUtil.getConnection();
			StringBuilder loginVisitor = new StringBuilder();
			loginVisitor.append("SELECT COUNT(visitorid) \n");
			loginVisitor.append("FROM visitors \n");
			loginVisitor.append("WHERE visitorid = ?");
			pstmt = conn.prepareStatement(loginVisitor.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			rs.next();
			cnt = rs.getInt(1);
		}  finally {
			dbUtil.close(rs, pstmt, conn);
		}
		
		return cnt;
	}

	@Override
	public void registerVisitor(VisitorDto visitorDto) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbUtil.getConnection();
			String sql = "INSERT INTO visitors (visitorid, visitorname, visitorpwd, email, joindate) \n";
			sql += "VALUES (?, ?, ?, ?, now())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, visitorDto.getVisitorId());
			pstmt.setString(2, visitorDto.getVisitorName());
			pstmt.setString(3, visitorDto.getVisitorPwd());
			pstmt.setString(4, visitorDto.getEmail());
			
			pstmt.executeUpdate();
			
		} finally {
			dbUtil.close(pstmt, conn);
		}
		
	}

	@Override
	public VisitorDto login(String id, String pass) throws Exception {
		VisitorDto visitorDto = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dbUtil.getConnection();
			String sql = "SELECT visitorid, visitorname \n";
			sql += "FROM visitors \n";
			sql += "WHERE visitorid = ? and visitorpwd = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				visitorDto = new VisitorDto();
				visitorDto.setVisitorId(rs.getString("visitorid"));
				visitorDto.setVisitorName(rs.getString("visitorname"));
				
			}
			
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return visitorDto;
	}



}
