package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import model.Users;
import service.UserService;
import service.impl.UserServiceImpl;
import util.OperUtil;

public class RegisterController extends HttpServlet {

		
	UserService userService = new UserServiceImpl();
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		String name = req.getParameter("username");
		String pwd = req.getParameter("password");
		PrintWriter out = res.getWriter();
		String sep = System.getProperty("file.separator");
		String userpath = "." + sep + "userfile";
		
		Users users = new Users();
		users.setName(name);;
		users.setPwd(pwd);
		
		if (userService.addUser(users) > 0 && OperUtil.create(userpath, name+"qiandu")) {
			;
			out.print("<script>alert(\"注册成功了！！！\");\r\n" + 
					" window.location.href = \"./Login\";</script>");

		} else {
			out.print("<script>alert(\"宁注册的昵称重复了！！！\");\r\n" + 
					" window.location.href = \"./Register\";</script>");
		}
		
		


	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		this.doGet(req, res);
	}

}