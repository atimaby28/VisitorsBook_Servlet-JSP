package com.visitor.visitorsbook.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.visitor.util.DBUtil;
import com.visitor.visitorsbook.dto.VisitorDto;
import com.visitor.visitorsbook.dto.VisitorsBookDto;

/**
 * Servlet implementation class VisitorsBookServlet
 */
@WebServlet("/visitorsbook")
public class VisitorsBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DBUtil dbUtil = DBUtil.getInstance();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisitorsBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String act = request.getParameter("act");
		String path = "/index.jsp";
		if ("register".equals(act)) {
			path = registerArticle(request, response);
		} else if ("list".equals(act)) {
			path = listArticle(request, response);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}
	
	private String registerArticle(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		VisitorDto visitorDto = (VisitorDto) session.getAttribute("visitorinfo");
		if (visitorDto != null) {
//			String visitorid = request.getParameter("visitorid");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
	
			Connection conn = null;
			PreparedStatement pstmt = null;
			int cnt = 0;
			try {
				conn = dbUtil.getConnection();
				StringBuilder registerArticle = new StringBuilder();
				registerArticle.append("insert into visitorsbook (visitorid, subject, content, regtime) \n");
				registerArticle.append("values (?, ?, ?, now())");
				pstmt = conn.prepareStatement(registerArticle.toString());
//				pstmt.setString(1, visitorid);
				pstmt.setString(1, visitorDto.getVisitorId());
				pstmt.setString(2, subject);
				pstmt.setString(3, content);
				cnt = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbUtil.close(pstmt, conn);
			}
			return cnt != 0 ? "/visitorsbook/writesuccess.jsp" : "/visitorsbook/writefail.jsp";
		} else {
			return "/visitor/login.jsp";
		}
	}

	private String listArticle(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		VisitorDto visitorDto = (VisitorDto) session.getAttribute("visitorinfo");
		
		if (visitorDto != null) {
			List<VisitorsBookDto> list = new ArrayList<VisitorsBookDto>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = dbUtil.getConnection();
				StringBuilder listArticle = new StringBuilder();
				listArticle.append("select articleno, visitorid, subject, content, regtime \n");
				listArticle.append("from visitorsbook \n");
				listArticle.append("order by articleno desc \n");
				pstmt = conn.prepareStatement(listArticle.toString());
				rs = pstmt.executeQuery();
				while (rs.next()) {
					VisitorsBookDto visitorsBookDto = new VisitorsBookDto();
					visitorsBookDto.setArticleNo(rs.getInt("articleno"));
					visitorsBookDto.setVisitorId(rs.getString("visitorid"));
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
			
			request.setAttribute("articles", list);
			
			return "/visitorsbook/list.jsp";
		} else {
			return "/visitor/login.jsp";
		}
	}
}
