package com.visitor.visitorsbook;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.visitor.util.DBUtil;

@WebServlet("/articlelist")
public class VisitorsBookList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private final DBUtil dbUtil = DBUtil.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("    <meta charset=\"UTF-8\">");
		out.println("    <title>Visitor - 글목록</title>");
		out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		out.println("    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">");
		out.println("    <style>");
		out.println("        mark.sky {");
		out.println("            background: linear-gradient(to top, #54fff9 20%, transparent 30%);");
		out.println("        }");
		out.println("    </style>");
		out.println("    <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>");
		out.println("    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js\"></script>");
		out.println("    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js\"></script>");
		out.println("    <script type=\"text/javascript\">");
		out.println("        $(document).ready(function () {");
		out.println("            $(\"#mvRegisterBtn\").click(function () {");
		out.println("                location.href = \"/visitorsbook1_servlet/visitorsbook/write.html\";");
		out.println("            });");
		out.println("        });");
		out.println("    </script>");
		out.println("</head>");
		out.println("<body>");
		out.println("    <div class=\"container text-center mt-3\">");
		out.println("        <div class=\"col-lg-8 mx-auto\">");
		out.println("            <h2 class=\"p-3 mb-3 shadow bg-light\">글 목록</h2>");
		out.println("            <div class=\"m-3 text-right\">");
		out.println("                <button type=\"button\" id=\"mvRegisterBtn\" class=\"btn btn-link\"><mark class=\"sky\">글 작성</mark></button>");
		out.println("            </div>");

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder listArticle = new StringBuilder();
			listArticle.append("SELECT articleno, visitorid, subject, content, regtime \n");
			listArticle.append("FROM visitorsbook \n");
			listArticle.append("ORDER BY articleno DESC \n");
			pstmt = conn.prepareStatement(listArticle.toString());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				out.println("            <table class=\"table table-active text-left\">");
				out.println("                <tbody>");
				out.println("                    <tr class=\"table-info\">");
				out.println("                        <td>작성자 : atimaby28</td>");
				out.println("                        <td class=\"text-right\">작성일 : 2024-02-28 16:30:12</td>");
				out.println("                    </tr>");
				out.println("                    <tr>");
				out.println("                        <td colspan=\"2\" class=\"table-danger\">");
				out.println("                            <strong>1. 안녕하세요!!!</strong>");
				out.println("                        </td>");
				out.println("                    </tr>");
				out.println("                    <tr>");
				out.println("                        <td class=\"p-4\" colspan=\"2\">즐거운 하루 되세요!!</td>");
				out.println("                    </tr>");
				out.println("                </tbody>");
				out.println("            </table>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		out.println("        </div>");
		out.println("    </div>");
		out.println("</body>");
		out.println("</html>");
	}

}
