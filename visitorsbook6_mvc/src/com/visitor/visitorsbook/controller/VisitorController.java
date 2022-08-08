package com.visitor.visitorsbook.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.visitor.visitorsbook.model.VisitorDto;
import com.visitor.visitorsbook.model.service.VisitorService;
import com.visitor.visitorsbook.model.service.VisitorServiceImpl;

/**
 * Servlet implementation class VisitorController
 */
@WebServlet("/visitor")
public class VisitorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private VisitorService visitorService;
	
	public void init() {
		visitorService = VisitorServiceImpl.getVisitorService();
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
		if("mvregister".equals(act)) {
			path = "/visitor/join.jsp";
			flag = false;
		} else if("mvlogin".equals(act)) {
			path = "/visitor/login.jsp";
			flag = false;
		} else if("idcheck".equals(act)) {
			path = idCheck(request, response);
		} else if("register".equals(act)) {
			path = registerVisitor(request, response);
		} else if("login".equals(act)) {
			path = loginVisitor(request, response);
		} else if("logout".equals(act)) {
			path = logoutVisitor(request, response);
			flag = false;
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


	private String logoutVisitor(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		// Service로 갈 필요가 없다.
		session.invalidate();
		return "/index.jsp";
	}

	private String loginVisitor(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("visitorid");
		String pass = request.getParameter("visitorpwd");
		
		VisitorDto visitorDto = null;
		
		try {
			visitorDto = visitorService.login(id, pass);
			System.out.println(visitorDto);
			if(visitorDto != null) { // 로그인이 성공된 경우
//				Session 설정
				HttpSession session = request.getSession();
				session.setAttribute("visitorinfo", visitorDto);
				
//				Cookie 설정
				String svid = request.getParameter("idsave");
				if("saveok".equals(svid)) { // 아이디 저장을 선택한 경우
					Cookie cookie = new Cookie("visitor_id", visitorDto.getVisitorId());
					cookie.setMaxAge(60*60*24*365);
//					cookie.setDomain(domain);
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
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "로그인 중에 문제가 발생하였습니다 !");
			return "/error/error.jsp";
		}
		
	}

	private String idCheck(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("ckid");
		int cnt = 1;
		try {
			cnt = visitorService.idCheck(id);
			request.setAttribute("idcnt", cnt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/visitor/idcheck_result.jsp";
	}

	private String registerVisitor(HttpServletRequest request, HttpServletResponse response) {
		VisitorDto visitorDto = new VisitorDto();
		
		visitorDto.setVisitorId(request.getParameter("visitorid"));
		visitorDto.setVisitorName(request.getParameter("visitorname"));
		visitorDto.setVisitorPwd(request.getParameter("visitorpwd"));
		visitorDto.setEmail(request.getParameter("email") + "@" + request.getParameter("emaildomain"));
		
		try {
			visitorService.registerVisitor(visitorDto);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "회원 가입 중에 문제가 발생하였습니다 !");
			return "/error/error.jsp";
		} 
		return "/visitor/login.jsp";
	}
}
