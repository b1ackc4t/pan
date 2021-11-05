package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.UserService;
import service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.FileUtil;
import util.OperUtil;

@MultipartConfig  //使用MultipartConfig注解标注改servlet能够接受文件上传的请求
public class MyShare extends HttpServlet {

	UserService userService = new UserServiceImpl();
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		 		
		String method = (req.getParameter("method")!=null)?req.getParameter("method"):"";
		
		switch (method) {
		case "delete":
			Page(req, res, method);
			break;
		default:
			mainPage(req, res);
			break;
				
		}

	}




	
	private void mainPage(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession(); 
		String name = null;
		String sep = System.getProperty("file.separator");

		if(!session.isNew() && session.getAttribute("name") != null){
			name = (String)session.getAttribute("name");
		} else {
			res.sendRedirect("./Login");
			return;
		}
		List<List> mysharelist = userService.getShareFile(name);
		
		String sharepath = "." + sep + "publicshare";
		
		ArrayList<ArrayList> mysharetotal = new ArrayList();	//存放用户目录下的目录信息列表
		
		if (mysharelist != null) {
			for (List add : mysharelist) {					
				String i = sharepath + add.get(0) + add.get(1);
				ArrayList<String> info = new ArrayList<String>();
				File f = new File(i);
				Double size = f.length()/1024.0;
				DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG);
				info.add(f.getName());
				info.add((String)add.get(0));
				info.add(dateFormat.format(new Date(f.lastModified())));
				if (size < 1024) {
					info.add(String.format("%.2fKB", size));
				} else {
					info.add(String.format("%.2fMB", size/1024.0));
				}
				info.add(FileUtil.ext(i));					
				mysharetotal.add(info);
				
			}
		}
		
		req.setAttribute("mysharetotal", mysharetotal);
		req.setAttribute("name", name);
		req.getRequestDispatcher("/MyShareView").forward(req, res);
	}
	

	
	private void Page(HttpServletRequest req, HttpServletResponse res, String method)
			throws ServletException, IOException {
		HttpSession session = req.getSession(); 
		String name = null;
		String sep = System.getProperty("file.separator");
		PrintWriter out = res.getWriter();
		String path = req.getParameter("path");
		String filename = req.getParameter("name");
		
		if(!session.isNew() && session.getAttribute("name") != null){
			name = (String)session.getAttribute("name");
		} else {
			res.sendRedirect("./Login");
			return;
		}
		
		String userpath = "." + sep + "publicshare" + path;
		switch (method) {

		case "delete":
			if (userService.deleteFile(path, filename) > 0) {
				OperUtil.delete(new File(userpath+req.getParameter("name")));
				out.println("<script>alert(\"宁的文件成功删除了！！！\");\r\n" + 
						" window.location.href = \"./MyShare\";</script>");
				
			} else {
				out.println("<script>alert(\"宁的文件删除失败了！！！{{{(>_<)}}}\");\r\n" + 
						" window.location.href = \"./MyShare\";</script>");
			}
			break;
		}
		

		
	}
	


	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		this.doGet(req, res);

	}

}
