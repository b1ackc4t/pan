package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.DateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.IdIsNullException;
import service.UserService;
import service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.Date;

import util.FileUtil;
import util.OperUtil;

@MultipartConfig  //使用MultipartConfig注解标注改servlet能够接受文件上传的请求
public class PublicManager extends HttpServlet {

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
		String method = (req.getParameter("method")!=null)?req.getParameter("method"):"";
		try {
			switch (method) {
			case "upload":
				uploadPage(req, res);
				break;
			case "create":
			case "delete":
				Page(req, res, method);
				break;
			default:
				mainPage(req, res);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
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


		
		String userpath = "." + sep + "publicshare" + dir;
		req.setAttribute("username", "admin");
		req.setAttribute("path", dir);
		req.setAttribute("uploadpath", userpath);
		req.setAttribute("part", req.getPart("uploadfile"));
		req.getRequestDispatcher("/Upload").forward(req, res);
		
		
		
		
	}


	
	private void mainPage(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String name = null;
		String sep = System.getProperty("file.separator");
		String dir = null;
		if (req.getParameter("dir")==null || req.getParameter("dir").equals("")) {
			dir = sep;
		} else {
			dir = (String)req.getParameter("dir");
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
			info.add(f.getName());
			info.add(dateFormat.format(new Date(f.lastModified())));
			info.add(String.valueOf(FileUtil.getTotalSizeOfFilesInDir(f)/1024));
			info.add("DIR");	
			dirtotal.add(info);
			
		}

		req.setAttribute("filetotal", filetotal);
		req.setAttribute("dirtotal", dirtotal);
		req.setAttribute("dir", dir);
		req.setAttribute("name", name);
		req.getRequestDispatcher("/PublicManagerView").forward(req, res);
	}
	

	
	private void Page(HttpServletRequest req, HttpServletResponse res, String method)
			throws ServletException, IOException, IdIsNullException {
		String sep = System.getProperty("file.separator");
		String dir = null;
		if (req.getParameter("dir")==null || req.getParameter("dir").equals("")) {
			dir = sep;
		} else {
			dir = (String)req.getParameter("dir");
		}
		PrintWriter out = res.getWriter();
		String filename = req.getParameter("name");
		String path = dir;
		

		String userpath = "." + sep + "publicshare" + dir;
		
		switch (method) {
		case "create":
			
			if (OperUtil.create(userpath, filename)) {
				dir = URLEncoder.encode(dir, "UTF-8");
				out.println("<script>alert(\"宁的文件夹创建成功了！！！\");\r\n" + 
						" window.location.href = \"./PublicManager?dir="+dir+"\";</script>");
			} else {
				dir = URLEncoder.encode(dir, "UTF-8");
				out.println("<script>alert(\"宁的文件夹创建失败，可能是名称重复了！{{{(>_<)}}}\");\r\n" + 
						" window.location.href = \"./PublicManager?dir="+dir+"\";</script>");
			}
			break;
		case "delete":
			if (req.getParameter("ifdir") != null) {
				userService.delShareDir(dir, filename);
				OperUtil.delete(new File(userpath+sep+filename));
				dir = URLEncoder.encode(dir, "UTF-8");
				out.println("<script>alert(\"宁的文件夹成功删除了！！！\");\r\n" + 
					" window.location.href = \"./PublicManager?dir="+dir+"\";</script>");

			} else {
				if (userService.deleteFile(dir, filename) > 0) {
					OperUtil.delete(new File(userpath+filename));
					dir = URLEncoder.encode(dir, "UTF-8");
					out.println("<script>alert(\"宁的文件成功删除了！！！\");\r\n" + 
						" window.location.href = \"./PublicManager?dir="+dir+"\";</script>");
				
				} else {
					dir = URLEncoder.encode(dir, "UTF-8");
					out.println("<script>alert(\"宁的文件删除失败了！！！{{{(>_<)}}}\");\r\n" + 
							" window.location.href = \"./PublicManager?dir="+dir+"\";</script>");
				}
			}		
			
			break;

		}
		
	}
	


	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		this.doGet(req, res);

	}

}
