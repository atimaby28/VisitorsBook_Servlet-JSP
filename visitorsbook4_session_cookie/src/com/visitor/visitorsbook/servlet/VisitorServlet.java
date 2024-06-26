package com.visitor.visitorsbook.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.visitor.util.DBUtil;
import com.visitor.visitorsbook.dto.VisitorDto;

/**
 * Servlet implementation class VisitorServlet
 */
@WebServlet("/visitor")
public class VisitorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DBUtil dbUtil = DBUtil.getInstance();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String root = request.getContextPath();
		String act = request.getParameter("act");
		String path = "/index.jsp";
		
		if("register".equals(act)) {
			path = registerVisitor(request, response);
			response.sendRedirect(root + path);
		} else if("login".equals(act)) {
			path = loginVisitor(request, response);
//			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
//			dispatcher.forward(request, response);
			response.sendRedirect(root + path);
		} else if("logout".equals(act)) {
			path = logoutVisitor(request, response);
			response.sendRedirect(root + path);
		} else {
			response.sendRedirect(root + path);
		}
	}

	private String logoutVisitor(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
//		session.removeAttribute("visitorinfo");
		session.invalidate();
		return "/index.jsp";
	}

	private String registerVisitor(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("visitorid");
		String name = request.getParameter("visitorname");
		String pass = request.getParameter("visitorpwd");
		String email = request.getParameter("emailid") + "@" + request.getParameter("emaildomain");
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dbUtil.getConnection();
			String sql = "INSERT INTO visitors (visitorid, visitorname, visitorpwd, email, joindate) \n";
			sql += "VALUES (?, ?, ?, ?, now())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, pass);
			pstmt.setString(4, email);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(pstmt, conn);
		}

		return "/visitor/login.jsp";
		
	}
	
	private String loginVisitor(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("visitorid");
		String pass = request.getParameter("visitorpwd");
		
		VisitorDto visitorDto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = dbUtil.getConnection();
			String sql = "SELECT visitorid, visitorname, email \n";
			sql += "FROM visitors \n";
			sql += "WHERE visitorid = ? AND visitorpwd = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				visitorDto = new VisitorDto();
				visitorDto.setVisitorId(id);
				visitorDto.setVisitorName(rs.getString("visitorname"));
				visitorDto.setEmail(rs.getString("email"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(pstmt, conn);
		}
		
//		request.setAttribute("visitorinfo", visitorDto);
		if(visitorDto != null) { // 로그인이 성공된 경우
//			Session 설정
			HttpSession session = request.getSession();
			session.setAttribute("visitorinfo", visitorDto);
			
//			Cookie 설정
			String svid = request.getParameter("idsave");
			if("saveok".equals(svid)) { // 아이디 저장을 선택한 경우
				Cookie cookie = new Cookie("visitor_id", visitorDto.getVisitorId());
				cookie.setMaxAge(60*60*24*365);
//				cookie.setDomain(domain);
				cookie.setPath(request.getContextPath());
				
				response.addCookie(cookie);				
			} else { // 아이디 저장을 해제한 경우
				Cookie[] cookies = request.getCookies();
				if(cookies != null) {
					for(Cookie cookie : cookies) {
						if("visitor_id".equals(cookie.getName())) {
							cookie.setMaxAge(0);
							cookie.setPath(request.getContextPath());
							response.addCookie(cookie);
							break;
						}
					}
				}
			}
		} 
		
		return "/index.jsp";
	}
	
}
