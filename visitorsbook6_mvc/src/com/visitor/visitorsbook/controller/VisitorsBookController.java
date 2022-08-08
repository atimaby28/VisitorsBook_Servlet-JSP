package com.visitor.visitorsbook.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.visitor.visitorsbook.model.VisitorDto;
import com.visitor.visitorsbook.model.VisitorsBookDto;
import com.visitor.visitorsbook.model.service.VisitorsBookService;
import com.visitor.visitorsbook.model.service.VisitorsBookServiceImpl;

/**
 * Servlet implementation class VisitorsBookController
 */
@WebServlet("/visitorsbook")
public class VisitorsBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private VisitorsBookService visitorsBookService;
	
	public void init() {
		visitorsBookService = VisitorsBookServiceImpl.getVisitorsBookService();
	}
       

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
		
		boolean flag = true;
		
		if("list".equals(act)) {
			path = listArticle(request, response);
		} else if("mvregister".equals(act)) {
			path = "/visitorsbook/write.jsp";
			flag = false;
		} else if("register".equals(act)) {
			path = registerArticle(request, response);
		} else if("delete".equals(act)) {
			path = deleteArticle(request, response);
		} else if("mvmodify".equals(act)) {
			path = mvmodifyArticle(request, response);
		} else if("modify".equals(act)) {
			path = modifyArticle(request, response);
		} else {
			path = "/index.jsp";
		}
		
		if(flag) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect(root + path);
		}
	}


	private String modifyArticle(HttpServletRequest request, HttpServletResponse response) {
		VisitorsBookDto visitorsBookDto = new VisitorsBookDto();
		visitorsBookDto.setArticleNo(Integer.parseInt(request.getParameter("articleno")));
		visitorsBookDto.setSubject(request.getParameter("subject"));
		visitorsBookDto.setContent(request.getParameter("content"));
		
		try {
			visitorsBookService.updateArticle(visitorsBookDto);
			return "/visitorsbook?act=list";
			
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "글작성중 문제가 발생했습니다.");
		}
		return "/error/error.jsp";
	}


	private String mvmodifyArticle(HttpServletRequest request, HttpServletResponse response) {
		int articleNo=Integer.parseInt(request.getParameter("articleno"));
		
		VisitorsBookDto visitorsBookDto;
		try {
			visitorsBookDto = visitorsBookService.getArticle(articleNo);
			request.setAttribute("article", visitorsBookDto);
			return "/visitorsbook/modify.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg","글 수정 중 문제 발생!!!");
		}
		return "/error/error.jsp";
	}


	private String deleteArticle(HttpServletRequest request, HttpServletResponse response) {
		int articleNo = Integer.parseInt(request.getParameter("articleno"));
		HttpSession session = request.getSession();
		VisitorDto visitorDto = (VisitorDto) session.getAttribute("visitorinfo");
		if (visitorDto != null) {
			try {
				visitorsBookService.deleteArticle(articleNo);
				return "/visitorsbook?act=list";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "글 삭제 중에 문제가 발생하였습니다 !");
				return "/error/error.jsp";
			}
		} else {
			return "/visitor/login.jsp";
		}
	}


	private String registerArticle(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		VisitorDto visitorDto = (VisitorDto) session.getAttribute("visitorinfo");
		if (visitorDto != null) {
			VisitorsBookDto visitorsBookDto = new VisitorsBookDto();
			visitorsBookDto.setVisitorId(visitorDto.getVisitorId());
			visitorsBookDto.setSubject(request.getParameter("subject"));
			visitorsBookDto.setSubject(request.getParameter("content"));
			
			try {
				visitorsBookService.registerArticle(visitorsBookDto);
				return "/visitorsbook?act=list";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "글 작성 중에 문제가 발생하였습니다 !");
				return "/error/error.jsp";
			}
			
		} else {
			return "/visitor/login.jsp";
		}
	}


	private String listArticle(HttpServletRequest request, HttpServletResponse response) {
		String key = request.getParameter("key"); // 아이디, 제목, 내용
		String word = request.getParameter("word"); // 검색어
		
		try {
			HttpSession session = request.getSession();
			VisitorDto visitorDto = (VisitorDto) session.getAttribute("visitorinfo");
			
			if (visitorDto != null) {
				List<VisitorsBookDto> list = visitorsBookService.listArticle(key, word);
				request.setAttribute("articles", list);
				
				return "/visitorsbook/list.jsp";
			} else {
				return "/visitor/login.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "글 목록 출력 중에 문제가 발생하였습니다 !");
			return "/error/error.jsp";
		}
		
	}
	
}
