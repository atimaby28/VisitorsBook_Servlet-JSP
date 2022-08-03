package com.visitor.visitorsbook.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
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
		} else if("idcheck".equals(act)) {
			int cnt = idCheck(request, response);
			response.getWriter().append(cnt + "");
		} else if("login".equals(act)) {
			path = loginVisitor(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response); 								// Forward와 Redirect의 차이점 숙지 !
//			response.sendRedirect(root + path);
		} else if("logout".equals(act)) {
			path = logoutVisitor(request, response);
			response.sendRedirect(root + path);
		} else {
			response.sendRedirect(root + path);
		}
	}

	private int idCheck(HttpServletRequest request, HttpServletResponse response) {
		int cnt = 1;
		String ckid = request.getParameter("ckid");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = dbUtil.getConnection();
			StringBuilder loginVisitor = new StringBuilder();
			loginVisitor.append("select count(visitorid) \n");
			loginVisitor.append("from visitors \n");
			loginVisitor.append("where visitorid = ?");
			pstmt = conn.prepareStatement(loginVisitor.toString());
			pstmt.setString(1, ckid);
			rs = pstmt.executeQuery();
			rs.next();
			cnt = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		
		return cnt;
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
			String sql = "insert into visitors (visitorid, visitorname, visitorpwd, email, joindate) \n";
			sql += "values (?, ?, ?, ?, now())";
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
			String sql = "select visitorid, visitorname, email \n";
			sql += "from visitors \n";
			sql += "where visitorid = ? and visitorpwd = ?";
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
			return "/index.jsp";
		} else {
			request.setAttribute("msg", "아이디 또는 비밀번호 확인 후 다시 로그인하세요!");
			return "/visitor/login.jsp";
		}

	} 
	
}
