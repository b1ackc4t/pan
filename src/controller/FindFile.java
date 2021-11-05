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


public class FindFile extends HttpServlet {

	UserService userService = new UserServiceImpl();
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		 		
		String target = (req.getParameter("target")!=null)?req.getParameter("target"):"";
		req.setAttribute("target", target);
		switch (target) {
		case "home":
			home(req, res);
			break;
		case "myshare":
			myShare(req, res);
			break;
		case "publicshare":
			publicShare(req, res);
			break;
			
		case "any":
			any(req, res);
			break;
		default:
			break;
				
		}

	}

	private void home(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		HttpSession session = req.getSession(); 
		String key = (req.getParameter("key")!=null)?req.getParameter("key"):"";
		String name = null;
		List<List> filetotal = new ArrayList<List>();
		
		if(!session.isNew() && session.getAttribute("name") != null){
			name = (String)session.getAttribute("name");
		} else {
			res.sendRedirect("./Login");
			return;
		}
		
		if (!key.equals("") && (filetotal=userService.FindHome(name, key)) != null ) {
			req.setAttribute("filetotal", filetotal);
		}
		req.setAttribute("name", name);
		req.getRequestDispatcher("/FindFileView").forward(req, res);
		
		
		
	}
	
	


	
	private void myShare(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession(); 
		String key = (req.getParameter("key")!=null)?req.getParameter("key"):"";
		String name = null;
		List<List> filetotal = new ArrayList<List>();
		
		if(!session.isNew() && session.getAttribute("name") != null){
			name = (String)session.getAttribute("name");
		} else {
			res.sendRedirect("./Login");
			return;
		}
		
		if (!key.equals("") && (filetotal=userService.FindHomeShare(name, key)) != null ) {
			req.setAttribute("filetotal", filetotal);
		}
		req.setAttribute("name", name);
		req.getRequestDispatcher("/FindFileView").forward(req, res);
	}
	

	
	private void publicShare(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession(); 
		String key = (req.getParameter("key")!=null)?req.getParameter("key"):"";
		String name = null;
		List<List> filetotal = new ArrayList<List>();
		
		if(!session.isNew() && session.getAttribute("name") != null){
			name = (String)session.getAttribute("name");
		} else {
			res.sendRedirect("./Login");
			return;
		}
		
		if (!key.equals("") && (filetotal=userService.FindPublicShare(key)) != null ) {
			req.setAttribute("filetotal", filetotal);
		}
		req.setAttribute("name", name);
		req.getRequestDispatcher("/FindFileView").forward(req, res);
		
	
		
	}
	
	private void any(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String name = req.getParameter("username");
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
		
		
		if (!key.equals("") && (filetotal=userService.FindHome(name, key)) != null ) {
			req.setAttribute("filetotal", filetotal);
		}
		req.setAttribute("name", name);
		req.getRequestDispatcher("/FindAnyView").forward(req, res);
		
		
		
	}
	


	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		this.doGet(req, res);

	}
	


}
