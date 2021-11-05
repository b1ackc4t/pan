package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import service.UserService;
import service.impl.UserServiceImpl;
import util.FileUtil;


@MultipartConfig  //使用MultipartConfig注解标注改servlet能够接受文件上传的请求
public class Upload extends HttpServlet {
	
	UserService userService = new UserServiceImpl();
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException, IllegalStateException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		PrintWriter out = res.getWriter();
		String name = null;
		
		String uploadpath = (String) req.getAttribute("uploadpath");
		HttpSession session = req.getSession(); 	
		
		if(!session.isNew() && session.getAttribute("name") != null) {
			name = (String)session.getAttribute("name");
		} else {
			res.sendRedirect("./Login");
			return;
		}
		
		Part part = (Part) req.getAttribute("part");
        String disposition = part.getHeader("Content-Disposition");
        Matcher m = Pattern.compile(".*filename=\"(.*\\..*)\"").matcher(disposition);

        if (m.find()) { 
	        disposition = m.group(1);
	        InputStream is = part.getInputStream();
	        FileOutputStream fos = new FileOutputStream(uploadpath+disposition);
	        long size = part.getSize();
	        String ext = FileUtil.ext(disposition);
	        if (req.getAttribute("username") != null) {
	        	if (userService.insertFile((String)req.getAttribute("username"), (String)req.getAttribute("path"), disposition, size, new Date(), ext) > 0) {
	        		byte[] bty = new byte[1024];
	    	        int length =0;
	    	        while((length=is.read(bty))!=-1){
	    	            fos.write(bty,0,length);
	    	        }
	    	        fos.close();
	    	        is.close();
	    	        String dir = URLEncoder.encode((String)req.getAttribute("path"), "UTF-8");
	    	        if (name.equals("admin")) {
	    	        	out.println("<script>alert(\"宁的文件成功分享了！！！\");\r\n" + 
							" window.location.href = \"./PublicManager?dir="+dir+"\";</script>");
	    	        }else {
	    	        	out.println("<script>alert(\"宁的文件成功分享了！！！\");\r\n" + 
								" window.location.href = \"./PublicShare?dir="+dir+"\";</script>");
	    	        }
	        	} else {
	        		
	        		String dir = URLEncoder.encode((String)req.getAttribute("path"), "UTF-8");
	        		if (name.equals("admin")) {
	        			out.println("<script>alert(\"宁的文件名在分享池此目录已经存在了！{{{(>_<)}}}\");\r\n" + 
								" window.location.href = \"./PublicManager?dir="+dir+"\";</script>");
	    	        }else {
	    	        	out.println("<script>alert(\"宁的文件名在分享池此目录已经存在了！{{{(>_<)}}}\");\r\n" + 
								" window.location.href = \"./PublicShare?dir="+dir+"\";</script>");
	    	        }
	        		
	        	}
	        	
	        } else {
	        	
	        	if (userService.insertHome((String)req.getAttribute("ownname"), (String)req.getAttribute("path"), disposition, size, new Date(), ext) > 0) {
	        		byte[] bty = new byte[1024];
			        int length =0;
			        while((length=is.read(bty))!=-1){
			            fos.write(bty,0,length);
			        }
			        fos.close();
			        is.close();
			        
			        String dir = URLEncoder.encode((String)req.getAttribute("path"), "UTF-8");
			        if (name.equals("admin")) {
			        	out.println("<script>alert(\"宁的文件已经存储到我的资源了！！！\");\r\n" + 
							" window.location.href = \"./UserFile?username="+(String)req.getAttribute("ownname")+"&dir="+dir+"\";</script>");
			        } else {
			        	out.println("<script>alert(\"宁的文件已经存储到我的资源了！！！\");\r\n" + 
								" window.location.href = \"./Home?dir="+dir+"\";</script>");	
			        }
	        	} else {
	        		String dir = URLEncoder.encode((String)req.getAttribute("path"), "UTF-8");
	        		if (name.equals("admin")) {
	        			out.println("<script>alert(\"宁的文件名在此目录已经存在了！{{{(>_<)}}}\");\r\n" + 
								" window.location.href = \"./UserFile?username="+(String)req.getAttribute("ownname")+"&dir="+dir+"\";</script>");
			        } else {
			        	out.println("<script>alert(\"宁的文件名在此目录已经存在了！{{{(>_<)}}}\");\r\n" + 
								" window.location.href = \"./Home?dir="+dir+"\";</script>");
			        }
	        		
	        	}
	        	
	        }
	        
        }



	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		this.doGet(req, res);

	}
}
