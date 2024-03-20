package com.visitor.visitorsbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.visitor.util.DBUtil;

/**
 * Servlet implementation class VisitorsBookWrite
 */
@WebServlet("/articlewrite")
public class VisitorsBookWrite extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private final DBUtil util = DBUtil.getInstance();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		1. data get
		request.setCharacterEncoding("utf-8"); // post에서만 !
		String visitorid = request.getParameter("visitorid");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		System.out.println(subject);
		
//		2. logic
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		
		try {
			conn = util.getConnection();
			String sql = "INSERT INTO visitorsbook (visitorid, subject, content) \n";
			sql += "VALUES (?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, visitorid);
			pstmt.setString(2, subject);
			pstmt.setString(3, content);
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(pstmt, conn);
		}
		
		
//		3. response page >> Success or Fail.
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("    <meta charset=\"UTF-8\">");
		out.println("    <title>Visitor - 글목록</title>");
		out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		out.println("    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">");
		out.println("    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>");
		out.println("    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js\"></script>");
		out.println("    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js\"></script>");
		out.println("    <script type=\"text/javascript\">");
		out.println("        $(document).ready(function () {");
		out.println("            $(\"#mvListBtn\").click(function () {");
		out.println("                location.href = \"/visitorsbook1_servlet/visitorsbook/list.html\";");
		out.println("            });");
		out.println("            $(\"#mvRegisterBtn\").click(function () {");
		out.println("                location.href = \"/visitorsbook1_servlet/visitorsbook/write.html\";");
		out.println("            });");
		out.println("        });");
		out.println("    </script>");
		out.println("</head>");
		out.println("<body>");
		out.println("    <div class=\"container text-center mt-3\">");
		out.println("        <div class=\"col-lg-8 mx-auto\">");
		if(cnt != 0) {
			out.println("            <div class=\"jumbotron\">");
			out.println("                <h1 class=\"text-primary\">글 작성을 성공하였습니다.</h1>");
			out.println("                <p class=\"mt-4\"><button type=\"button\" id=\"mvListBtn\" class=\"btn btn-outline-dark\">글 목록 페이지로 이동</button>");
			out.println("                </p>");
			out.println("            </div>");
		} else {
			out.println("            <div class=\"jumbotron\">");
			out.println("                <h1 class=\"text-danger\">글 작성을 실패하였습니다.</h1>");
			out.println("                <p class=\"mt-4\"><button type=\"button\" id=\"mvRegisterBtn\" class=\"btn btn-outline-dark\">글쓰기 페이지로 이동</button>");
			out.println("                </p>");
			out.println("            </div>");
		}
		out.println("        </div>");
		out.println("    </div>");
		out.println("</body>");
		out.println("</html>");
	}

}
