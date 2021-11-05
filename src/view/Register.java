package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Register extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		PrintWriter out = res.getWriter();		
		out.print("<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n" + 
				"    <title>register</title>\r\n" + 
				"    <link rel=\"stylesheet\" href=\"css/style1.css\">\r\n" + 
				"    <SCRIPT language=\"JavaScript\">\r\n" + 
				"\r\n" + 
				"        function checkUser() {  \r\n" + 
				"            var fname = document.myform.username.value;\r\n" + 
				"            var userpass = document.myform.password.value;\r\n" + 
				"            var p1 = document.myform.password.value;\r\n" + 
				"            var p2 = document.myform.password2.value;\r\n" + 
				"\r\n" + 
				"            if (fname.length != 0) {\r\n" + 
				"                if (fname.length < 2 || fname.length > 16) {\r\n" + 
				"                    alert(\"只能输入2-16个字符\")\r\n" + 
				"                    return false;\r\n" + 
				"                }\r\n" + 
				"            }\r\n" + 
				"            else {\r\n" + 
				"                alert(\"请输入用户名\");\r\n" + 
				"                document.myform.username.focus();\r\n" + 
				"                return false\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            if (userpass == \"\") {\r\n" + 
				"                alert(\"未输入密码 \\n\" + \"请输入密码\");\r\n" + 
				"                document.myform.password.focus();\r\n" + 
				"                return false;\r\n" + 
				"            }\r\n" + 
				"            if (userpass.length < 3 || userpass.length > 12) {\r\n" + 
				"                alert(\"密码必须在 3-12 个字符。\\n\");\r\n" + 
				"                return false;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            if (p1 != p2) {\r\n" + 
				"                alert(\"确认密码与密码输入不一致\");\r\n" + 
				"                return false;\r\n" + 
				"            } else {\r\n" + 
				"                return true;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"           return true;\r\n" + 
				"        }\r\n" + 
				"    </SCRIPT>\r\n" + 
				"</head>\r\n" + 
				"\r\n" + 
				"<body>\r\n" + 
				"    \r\n" + 
				"        <form class=\"form\" name=\"myform\" action='./RegisterController' method='post' onsubmit = \"return checkUser()\">\r\n" + 
				"            <div class=\"form-inner\">\r\n" + 
				"                <h2>用户注册</h2>\r\n" + 
				"                <div class=\"input-wrapper\">\r\n" + 
				"                    <label for=\"login-username\">用户名:</label>\r\n" + 
				"                    <div class=\"input-group\">\r\n" + 
				"                    <input class=\"form-control\" id=\"username\" data-lpignore=\"true\" name=\"username\" type=\"text\" value=\"\" placeholder=\"只能输入字母或数字，4-16个字符\" />\r\n" + 
				"                    </div>\r\n" + 
				"                </div>\r\n" + 
				"\r\n" + 
				"                <div class=\"input-wrapper\">\r\n" + 
				"                    <label for=\"login-username\">密码:</label>\r\n" + 
				"                    <div class=\"input-group\">\r\n" + 
				"                        <input class=\"form-control\" id=\"password\" data-lpignore=\"true\" name=\"password\" type=\"password\" value=\"\" placeholder=\"密码长度6-12位\" />\r\n" + 
				"                    </div>\r\n" + 
				"                </div>\r\n" + 
				"\r\n" + 
				"                <div class=\"input-wrapper\">\r\n" + 
				"                    <label for=\"login-username\">确定密码:</label>\r\n" + 
				"                    <div class=\"input-group\">\r\n" + 
				"                        <input class=\"form-control\" id=\"password2\" data-lpignore=\"true\" name=\"password2\" type=\"password\" value=\"\" placeholder=\"密码长度6-12位\" />\r\n" + 
				"                    </div>\r\n" + 
				"                </div>\r\n" + 
				"                \r\n" + 
				"          \r\n" + 
				"                <div id=\" btn\" class=\"btn-group\">\r\n" + 
				"                        <button name=\"registerButton\" class=\"btn btn--primary\" type=\"submit\" id=\"Button\" >注册</button>\r\n" + 
				"                            <br>\r\n" + 
				"                        <a href=\"./Login\"><button class=\"btn btn-primary\" name=\"loginButton\" id=\"Button\" type=\"button\"\r\n" + 
				"                                >登陆</button></a>\r\n" + 
				"                </div>\r\n" + 
				"            </div>\r\n" + 
				"        </form>\r\n" + 
				"    </div>\r\n" + 
				"\r\n" + 
				"</body>\r\n" + 
				"\r\n" + 
				"</html>");


	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		this.doGet(req, res);
	}

}