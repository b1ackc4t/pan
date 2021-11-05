package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.UserService;
import service.impl.UserServiceImpl;
import util.OperUtil;


public class AdminHome extends HttpServlet {

	UserService userService = new UserServiceImpl();
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");	
		HttpSession session = req.getSession(); 
		String method = (req.getParameter("method")!=null)?req.getParameter("method"):"";
		String name = null;
		if(!session.isNew() && session.getAttribute("name") != null) {
			if(session.getAttribute("name").equals("admin")){
				name = (String)session.getAttribute("name");
			} else {
				res.sendRedirect("./Login");
				return;
			}
		} else {
			res.sendRedirect("./Login");
			return;
		}
		
		switch (method) {

		case "update":			
		case "delete":
		case "share":
			Page(req, res, method);
			break;
		default:
			mainPage(req, res);
			break;
				
		}
		
		
		
		
		


	}
		
	private void mainPage(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setAttribute("userli", userService.selectAll());
		req.getRequestDispatcher("/AdminMain").forward(req, res);
	}
	
	private void Page(HttpServletRequest req, HttpServletResponse res, String method)
			throws ServletException, IOException {
 

		String name = req.getParameter("username");
		PrintWriter out = res.getWriter();
		String sep = System.getProperty("file.separator");
		String userpath = "." + sep + "userfile" + sep + name + "qiandu";
		
		switch (method) {

		case "delete":
			try {
				if (userService.delUserByName(name)>0 ) {
					OperUtil.delete(new File(userpath));
					
					out.println("<script>alert(\"此用户已经成功删除了！！！\");\r\n" + 
							" window.location.href = \"./AdminHome\";</script>");
				} else {
					out.println("<script>alert(\"此用户删除失败，可能已经被删除！{{{(>_<)}}}\");\r\n" + 
							" window.location.href = \"./AdminHome\";</script>");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			break;
		case "update":		
			try {
				if (userService.updatePwd(name, req.getParameter("pwd")) > 0) {
					out.println("<script>alert(\"宁已成功修改密码了！！！\");\r\n" + 
							" window.location.href = \"./AdminHome\";</script>");
				} else {
					out.println("<script>alert(\"宁修改失败了！！！{{{(>_<)}}}\");\r\n" + 
							" window.location.href = \"./AdminHome\";</script>");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		this.doGet(req, res);

	}

}
