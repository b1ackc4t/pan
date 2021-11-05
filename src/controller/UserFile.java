package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.UserService;
import service.impl.UserServiceImpl;
import util.FileUtil;
import util.OperUtil;

@MultipartConfig  //使用MultipartConfig注解标注改servlet能够接受文件上传的请求
public class UserFile extends HttpServlet {

	UserService userService = new UserServiceImpl();
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		HttpSession session = req.getSession();
		String name = req.getParameter("username");
		String method = (req.getParameter("method")!=null)?req.getParameter("method"):"";
		
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
		
		switch (method) {
		case "upload":
			uploadPage(req, res);
			break;
		case "create":
		case "delete":
		case "share":
			Page(req, res, method);
			break;
		default:
			mainPage(req, res);
			break;
				
		}





	}
	
	private void uploadPage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		
		String name = req.getParameter("username");
		String sep = System.getProperty("file.separator");
		String dir = null;
		if (req.getParameter("dir")==null || req.getParameter("dir").equals("")) {
			dir = sep;
		} else {
			dir = (String)req.getParameter("dir");
		}
		
		String userpath = "." + sep + "userfile" + sep + name + "qiandu" + dir;
		req.setAttribute("path", dir);
		req.setAttribute("ownname", name);
		req.setAttribute("uploadpath", userpath);
		req.setAttribute("part", req.getPart("uploadfile"));
		req.getRequestDispatcher("/Upload").forward(req, res);
		
		
		
		
	}
	
	private void mainPage(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String sep = System.getProperty("file.separator");
		String dir = null;
		if (req.getParameter("dir")==null || req.getParameter("dir").equals("")) {
			dir = sep;
		} else {
			dir = (String)req.getParameter("dir");
		}
		String name = req.getParameter("username");
		if (name.equals("admin")) {
			res.sendRedirect("./AdminHome");
			return;
		}

		String userpath = "." + sep + "userfile" + sep + name + "qiandu" + dir;
		
		ArrayList<ArrayList> dirtotal = new ArrayList();	//存放用户目录下的目录信息列表
		ArrayList<ArrayList> filetotal = new ArrayList();	//存放用户目录下的文件信息列表

		for (String i:FileUtil.getFiles(userpath)) {
			ArrayList<String> info = new ArrayList();
			File f = new File(i);
			Double size = f.length()/1024.0;
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG);
			info.add(f.getName());
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
			Double size = FileUtil.getTotalSizeOfFilesInDir(f)/1024.0;
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG);
			info.add(f.getName());
			info.add(dateFormat.format(new Date(f.lastModified())));
			if (size < 1024) {
				info.add(String.format("%.2fKB", size));
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
		req.getRequestDispatcher("/UserFileView").forward(req, res);
	}
	
	private void Page(HttpServletRequest req, HttpServletResponse res, String method)
			throws ServletException, IOException {
		String sep = System.getProperty("file.separator");
		String dir = null;
		if (req.getParameter("dir")==null || req.getParameter("dir").equals("")) {
			dir = sep;
		} else {
			dir = (String)req.getParameter("dir");
		}
		String name = req.getParameter("username");
		PrintWriter out = res.getWriter();
		

		
		String userpath = "." + sep + "userfile" + sep + name + "qiandu" + dir;
		
		switch (method) {
		case "create":

			if (userService.insertHome(name, dir, req.getParameter("name"), -1, new Date(), "DIR")>0 && OperUtil.create(userpath, req.getParameter("name"))) {
				dir = URLEncoder.encode(dir, "UTF-8");
				out.println("<script>alert(\"宁的文件夹创建成功了！！！\");\r\n" + 
						" window.location.href = \"./UserFile?username="+name+"&dir="+dir+"\";</script>");
			} else {
				dir = URLEncoder.encode(dir, "UTF-8");
				out.println("<script>alert(\"宁的文件夹创建失败，可能是名称重复了！{{{(>_<)}}}\");\r\n" + 
						" window.location.href = \"./UserFile?username="+name+"&dir="+dir+"\";</script>");
			}
			break;
		case "delete":

			if (userService.deleteHome(name, dir, req.getParameter("name"))>0) {
				OperUtil.delete(new File(userpath+req.getParameter("name")));
				dir = URLEncoder.encode(dir, "UTF-8");
				out.println("<script>alert(\"宁的文件成功删除了！！！\");\r\n" + 
						" window.location.href = \"./UserFile?username="+name+"&dir="+dir+"\";</script>");
				
			} else {
		
				dir = URLEncoder.encode(dir, "UTF-8");
				out.println("<script>alert(\"宁的文件删除失败，可能已经被删除！{{{(>_<)}}}\");\r\n" + 
						" window.location.href = \"./UserFile?username="+name+"&dir="+dir+"\";</script>");
			}
			break;

		case "share":	
			File f = new File(userpath+sep+req.getParameter("name"));
			long size = f.length();
			String ext = FileUtil.ext(req.getParameter("name"));
			
			if (userService.insertFile(name, sep, req.getParameter("name"), size, new Date(), ext)>0 && OperUtil.share(userpath, req.getParameter("name"), "." + sep + "publicshare" + sep)) {
				dir = URLEncoder.encode(dir, "UTF-8");
				out.println("<script>alert(\"宁的文件成功分享了！！！\");\r\n" + 
						" window.location.href = \"./UserFile?username="+name+"&dir="+dir+"\";</script>");
			} else {
				dir = URLEncoder.encode(dir, "UTF-8");
				out.println("<script>alert(\"宁的文件名在分享池根目录已经存在了！{{{(>_<)}}}\");\r\n" + 
						" window.location.href = \"./UserFile?username="+name+"&dir="+dir+"\";</script>");
			}
			break;
		}
		
	}
	

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		this.doGet(req, res);

	}
}
