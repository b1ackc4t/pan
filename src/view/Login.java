package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		PrintWriter out = res.getWriter();		
		HttpSession session = req.getSession();
		Cookie cookie = null;
		String del = req.getParameter("del");
		
		if (del != null) {
			
			session.invalidate();
			res.sendRedirect("./Login");
			
		} else { 
		
			if(!session.isNew()){	
				if(session.getAttribute("name") != null){
					res.sendRedirect("./LoginController");
				}
			}
			   
			String msg = (String) req.getAttribute("errMsg");
	
			out.print("<!DOCTYPE html>\r\n" + 
					"<html lang=\"en\">\r\n" + 
					"\r\n" + 
					"<head>\r\n" + 
					"    <meta http-equiv='keywords' content='keyword1,keyword2,keyword3'>\r\n" + 
					"    <meta http-equiv='description' content='this is my page'>\r\n" + 
					"    <meta http-equiv='content-type' content='text/html; charset=UTF-8'>\r\n" + 
					"    <title>Login</title>\r\n" + 
					"\r\n" + 
					"    <link rel=\"stylesheet\" href=\"css/style1.css\">\r\n" + 
					"    <style>\r\n" + 
					"        .input-group input {\r\n" + 
					"            border-radius: var(--base-border-radius);\r\n" + 
					"            padding-left: calc(var(--space-s) + 60px);\r\n" + 
					"        }\r\n" + 
					"\r\n" + 
					"         .demo {\r\n" + 
					"            background: #666666;\r\n" + 
					"            width: 430px;\r\n" + 
					"            padding: 10px;\r\n" + 
					"            font: bold 16px/100% \"微软雅黑\", \"Lucida Grande\", \"Lucida Sans\", Helvetica, Arial, Sans;;\r\n" + 
					"            color: #fff;\r\n" + 
					"            text-transform: uppercase;\r\n" + 
					"        }\r\n" + 
					"    </style>\r\n" + 
					"\r\n" + 
					"</head>\r\n" + 
					"\r\n" + 
					"<body>\r\n" + 
					"    \r\n" + 
					"    <form class=\"form\" action=\"./LoginController\" method=\"post\" autocomplete=\"off\">\r\n" +
					"        \r\n" + 
					"        <div class=\"form-inner\">\r\n" + 
					"            <h2>用户登录</h2>\r\n" +
					"            <div class=\"input-wrapper\">\r\n" + 
					"                <label for=\"login-username\">用户名</label>\r\n" + 
					"                <div class=\"input-group\"><span class=\"icon\">\r\n" + 
					"                        <svg viewBox=\"0 0 24 24\">\r\n" + 
					"                            <path\r\n" + 
					"                                d=\"M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z\">\r\n" + 
					"                            </path>\r\n" + 
					"                        </svg></span>\r\n" + 
					"                    <input type=\"text\" name=\"username\" id=\"login-username\" data-lpignore=\"true\">\r\n" + 
					"                </div>\r\n" + 
					"            </div>\r\n" + 
					"            <div class=\"input-wrapper\">\r\n" + 
					"                <label for=\"login-password\">密码</label>\r\n" + 
					"                <div class=\"input-group\"><span class=\"icon\">\r\n" + 
					"                        <svg viewBox=\"0 0 24 24\">\r\n" + 
					"                            <path\r\n" + 
					"                                d=\"M11.83,1.73C8.43,1.79 6.23,3.32 6.23,3.32C5.95,3.5 5.88,3.91 6.07,4.19C6.27,4.5 6.66,4.55 6.96,4.34C6.96,4.34 11.27,1.15 17.46,4.38C17.75,4.55 18.14,4.45 18.31,4.15C18.5,3.85 18.37,3.47 18.03,3.28C16.36,2.4 14.78,1.96 13.36,1.8C12.83,1.74 12.32,1.72 11.83,1.73M12.22,4.34C6.26,4.26 3.41,9.05 3.41,9.05C3.22,9.34 3.3,9.72 3.58,9.91C3.87,10.1 4.26,10 4.5,9.68C4.5,9.68 6.92,5.5 12.2,5.59C17.5,5.66 19.82,9.65 19.82,9.65C20,9.94 20.38,10.04 20.68,9.87C21,9.69 21.07,9.31 20.9,9C20.9,9 18.15,4.42 12.22,4.34M11.5,6.82C9.82,6.94 8.21,7.55 7,8.56C4.62,10.53 3.1,14.14 4.77,19C4.88,19.33 5.24,19.5 5.57,19.39C5.89,19.28 6.07,18.92 5.95,18.6V18.6C4.41,14.13 5.78,11.2 7.8,9.5C9.77,7.89 13.25,7.5 15.84,9.1C17.11,9.9 18.1,11.28 18.6,12.64C19.11,14 19.08,15.32 18.67,15.94C18.25,16.59 17.4,16.83 16.65,16.64C15.9,16.45 15.29,15.91 15.26,14.77C15.23,13.06 13.89,12 12.5,11.84C11.16,11.68 9.61,12.4 9.21,14C8.45,16.92 10.36,21.07 14.78,22.45C15.11,22.55 15.46,22.37 15.57,22.04C15.67,21.71 15.5,21.35 15.15,21.25C11.32,20.06 9.87,16.43 10.42,14.29C10.66,13.33 11.5,13 12.38,13.08C13.25,13.18 14,13.7 14,14.79C14.05,16.43 15.12,17.54 16.34,17.85C17.56,18.16 18.97,17.77 19.72,16.62C20.5,15.45 20.37,13.8 19.78,12.21C19.18,10.61 18.07,9.03 16.5,8.04C14.96,7.08 13.19,6.7 11.5,6.82M11.86,9.25V9.26C10.08,9.32 8.3,10.24 7.28,12.18C5.96,14.67 6.56,17.21 7.44,19.04C8.33,20.88 9.54,22.1 9.54,22.1C9.78,22.35 10.17,22.35 10.42,22.11C10.67,21.87 10.67,21.5 10.43,21.23C10.43,21.23 9.36,20.13 8.57,18.5C7.78,16.87 7.3,14.81 8.38,12.77C9.5,10.67 11.5,10.16 13.26,10.67C15.04,11.19 16.53,12.74 16.5,15.03C16.46,15.38 16.71,15.68 17.06,15.7C17.4,15.73 17.7,15.47 17.73,15.06C17.79,12.2 15.87,10.13 13.61,9.47C13.04,9.31 12.45,9.23 11.86,9.25M12.08,14.25C11.73,14.26 11.46,14.55 11.47,14.89C11.47,14.89 11.5,16.37 12.31,17.8C13.15,19.23 14.93,20.59 18.03,20.3C18.37,20.28 18.64,20 18.62,19.64C18.6,19.29 18.3,19.03 17.91,19.06C15.19,19.31 14.04,18.28 13.39,17.17C12.74,16.07 12.72,14.88 12.72,14.88C12.72,14.53 12.44,14.25 12.08,14.25Z\">\r\n" + 
					"                            </path>\r\n" + 
					"                        </svg></span>\r\n" + 
					"                    <input type=\"password\" name=\"pwd\" id=\"login-password\" data-lpignore=\"true\">\r\n" + 
					"                </div>\r\n" + 
					"            </div>\r\n" + 
					"\r\n" + 
					"            <div class=\"btn-group\">\r\n" + 
					"                <button class=\"btn btn--primary\">登录</button><a class=\"btn--text\" href=\"./Register\">注册</a>\r\n" + 
					"            </div>\r\n" + 
					"        </div>\r\n" + 
					"    </form>\r\n" + 
					"    <span class=\"demo\">格式乱了，或者颜色不显示，请更换谷歌浏览器！！！</span>\r\n" + 
					"\r\n" + 
					"</body>\r\n" + 
					"\r\n" + 
					"</html>");
			if (msg != null) {
				out.println(msg);
			}
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		this.doGet(req, res);
	}

}