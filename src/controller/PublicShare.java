package controller;

import java.io.File;
import java.io.IOException;
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

import util.FileUtil;

@MultipartConfig  //使用MultipartConfig注解标注改servlet能够接受文件上传的请求
public class PublicShare extends HttpServlet {

	UserService userService = new UserServiceImpl();
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		 		
		String method = (req.getParameter("method")!=null)?req.getParameter("method"):"";
		
		switch (method) {
		case "upload":
			uploadPage(req, res);
			break;

		default:
			mainPage(req, res);
			break;
				
		}

	}

	private void uploadPage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		HttpSession session = req.getSession(); 	
		String name = null;
		String sep = System.getProperty("file.separator");
		String dir = null;
		if (req.getParameter("dir")==null || req.getParameter("dir").equals("")) {
			dir = sep;
		} else {
			dir = (String)req.getParameter("dir");
		}

		if(!session.isNew() && session.getAttribute("name") != null){
			name = (String)session.getAttribute("name");
		} else {
			res.sendRedirect("./Login");
			return;
		}
		
		String userpath = "." + sep + "publicshare" + dir;
		req.setAttribute("username", name);
		req.setAttribute("path", dir);
		req.setAttribute("uploadpath", userpath);
		req.setAttribute("part", req.getPart("uploadfile"));
		req.getRequestDispatcher("/Upload").forward(req, res);
		
		
		
		
	}


	
	private void mainPage(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession(); 
		String name = null;
		String sep = System.getProperty("file.separator");
		String dir = null;
		if (req.getParameter("dir")==null || req.getParameter("dir").equals("")) {
			dir = sep;
		} else {
			dir = (String)req.getParameter("dir");
		}

		if(!session.isNew() && session.getAttribute("name") != null){
			name = (String)session.getAttribute("name");
		} else {
			res.sendRedirect("./Login");
			return;
		}
		
		String userpath = "." + sep + "publicshare" + dir;
		
		ArrayList<ArrayList> dirtotal = new ArrayList();	//存放用户目录下的目录信息列表
		ArrayList<ArrayList> filetotal = new ArrayList();	//存放用户目录下的文件信息列表
		
		for (String i:FileUtil.getFiles(userpath)) {
			ArrayList<String> info = new ArrayList();
			File f = new File(i);
			String n = f.getName();
			Double size = f.length()/1024.0;
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG);
			info.add(n);
			info.add(userService.getOwner(dir, n));
			info.add(dateFormat.format(new Date(f.lastModified())));
			if (size < 1024) {
				info.add(String.format("%.2fKB", size));
			} else {
				info.add(String.format("%.2fMB", size/1024.0));
			}
			info.add(FileUtil.ext(i));	
			filetotal.add(info);
			
		}
		
		
		for (String i:FileUtil.getDirs(userpath)) {
			ArrayList<String> info = new ArrayList();
			File f = new File(i);
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG);
			long size = FileUtil.getTotalSizeOfFilesInDir(f)/1024;
			info.add(f.getName());
			info.add(dateFormat.format(new Date(f.lastModified())));
			if (size < 1024) {
				info.add(String.format("%dKB", size));
			} else {
				info.add(String.format("%.2fMB", size/1024.0));
			}
			info.add("DIR");	
			dirtotal.add(info);
			
		}

		req.setAttribute("filetotal", filetotal);
		req.setAttribute("dirtotal", dirtotal);
		req.setAttribute("dir", dir);
		req.setAttribute("name", name);
		req.getRequestDispatcher("/PublicShareView").forward(req, res);
	}
	

	

	


	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		this.doGet(req, res);

	}

}
