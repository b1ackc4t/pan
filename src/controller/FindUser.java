package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.UserService;
import service.impl.UserServiceImpl;
import java.util.List;

public class FindUser extends HttpServlet {

	UserService userService = new UserServiceImpl();
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		HttpSession session = req.getSession(); 
		List<List> userli = null;
		String key = req.getParameter("key");
		if(!session.isNew() && session.getAttribute("name") != null) {
			if(session.getAttribute("name").equals("admin")){
				;
			} else {
				res.sendRedirect("./Login");
				return;
			}
		} else {
			res.sendRedirect("./Login");
			return;
		}
				
		if (!key.equals("") && (userli=userService.findUser(key)) != null ) {
			req.setAttribute("userli", userli);
		}
		req.getRequestDispatcher("/FindUserView").forward(req, res);
		

	}


	
	



	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		this.doGet(req, res);

	}
	


}
