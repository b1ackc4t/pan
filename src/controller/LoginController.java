package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Users;
import service.UserService;
import service.impl.UserServiceImpl;

public class LoginController extends HttpServlet {

	UserService userService = new UserServiceImpl();
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		HttpSession session = req.getSession();
		Cookie cookie = null;
		
		if(!session.isNew() && session.getAttribute("name") != null){
			req.getRequestDispatcher("/Home").forward(req, res); 
		}

		String name = req.getParameter("username");
		String pwd = req.getParameter("pwd");
		
	
		Users users = new Users();
		users.setName(name);
		users.setPwd(pwd);
		
		if (users.getName() != null && userService.checkUser(users)) {	
			
			String sessionId = session.getId();
			cookie = new Cookie("JSESSIONID", sessionId);
			res.addCookie(cookie);
			session.setAttribute("name", name);
			req.getRequestDispatcher("/Home").forward(req, res);
			
			
		} else {
			
			String errMsg = "用户名不存在或密码错误！！！！！请重新输入！！！！！";
			req.setAttribute("errMsg", errMsg);
			req.getRequestDispatcher("/Login").forward(req, res);
		}


	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		this.doGet(req, res);

	}
}
