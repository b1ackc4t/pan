package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletOutputStream;

import exception.IdIsNullException;
import model.Users;
import service.UserService;
import service.impl.UserServiceImpl;

public class DownloadFile extends HttpServlet {

	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		String name = null;
		String path = null;
		HttpSession session = req.getSession(); 
		if(!session.isNew() && session.getAttribute("name") != null){
			name = (String)session.getAttribute("name");
		} else {
			res.sendRedirect("./Login");
			return;
		}
		String sep = System.getProperty("file.separator");
		String file = req.getParameter("name"); //客户端传递的需要下载的文件名
		
		if (req.getParameter("target").equals("public")) {
			path = "." + sep + "publicshare" + req.getParameter("dir") + file;
		} else {
			path = "." + sep + "userfile" + sep + name + "qiandu" + sep + req.getParameter("dir") + file;
		}
		
        File f = new File(path);
        if(f.exists()){  
            FileInputStream  fis = new FileInputStream(f);  
            String filename=URLEncoder.encode(f.getName(),"utf-8"); //解决中文文件名下载后乱码的问题 
            
            byte[] b = new byte[fis.available()];  
            fis.read(b);  
            res.setCharacterEncoding("utf-8");  
            res.setHeader("Content-Disposition","attachment; filename="+filename+"");  
            //获取响应报文输出流对象  
            ServletOutputStream  out =res.getOutputStream();  
            //输出  
            out.write(b);  
            out.flush();  
            out.close();
        }




	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		this.doGet(req, res);

	}
}

