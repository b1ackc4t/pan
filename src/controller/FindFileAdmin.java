package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import service.UserService;
import service.impl.UserServiceImpl;
import java.util.ArrayList;
import java.util.List;


public class FindFileAdmin extends HttpServlet {

	UserService userService = new UserServiceImpl();
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		HttpSession session = req.getSession(); 
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
		
		String key = (req.getParameter("key")!=null)?req.getParameter("key"):"";
		List<List> filetotal = new ArrayList<List>();
		String name = null;
		
		if(!session.isNew() && session.getAttribute("name") != null){
			name = (String)session.getAttribute("name");
		} else {
			res.sendRedirect("./Login");
			return;
		}
		
		if (!key.equals("") && (filetotal=userService.FindPublicShare(key)) != null ) {
			req.setAttribute("filetotal", filetotal);
		}
		req.getRequestDispatcher("/FindFileAdminView").forward(req, res);
		 		


	}

	


	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		this.doGet(req, res);

	}
	


}
