package view;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.FileUtil;

public class FindUserView extends HttpServlet {
	

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		PrintWriter out = res.getWriter();		
		List<List> userli = (List<List>)req.getAttribute("userli");
		String sepp = System.getProperty("file.separator");

		out.print("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\r\n" + 
				"<html>\r\n" + 
				"\r\n" + 
				"<head>\r\n" + 
				"\r\n" + 
				"	<meta charset=\"utf-8\">\r\n" + 
				"	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n" + 
				"	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
				"	<title>千度网盘</title>\r\n" + 
				"	<link rel=\"stylesheet\" href=\"css/reset.css\">\r\n" +
				"	<link rel=\"stylesheet\" href=\"css/bootstrap.css\">\r\n" + 
				"	<link rel=\"stylesheet\" href=\"css/xui.css\">\r\n" + 
				"	<!-- <script type=\"text/javascript\" src=\"/js/jquery.min.js\"></script> -->\r\n" + 
				"\r\n" + 
				"</head>\r\n" + 
				"\r\n" + 
				"<body>\r\n" + 
				"	<div class=\"top\" id=\"all\">\r\n" + 
				"		<!--顶部导航-->\r\n" + 
				"		<div class=\"top_nav\">\r\n" + 
				"			<nav class=\"navbar navbar-dark bg-inverse\">\r\n" + 
				"				<div class=\"nav_width\">\r\n" + 
				"					<a class=\"navbar-brand\" href=\"#\"> <img src=\"images/logo1.png\" />\r\n" + 
				"						<p>千度网盘-管理员界面</p>\r\n" + 
				"					</a>\r\n" + 
				"					<ul class=\"nav navbar-nav pull-right\">						\r\n" + 
				"						<!-- 注意跳转的位置，每一个都是一个servlet -->\r\n" + 
				"\r\n" + 
				"						<li class=\"nav-item\"><a class=\"nav-link\" href=\"./AdminHome\">用户及资源管理</a></li>\r\n" + 
				"						<!-- 当前页面，通过dir参数来跳转目录 -->\r\n" + 
				"						\r\n" + 
				"						<li class=\"nav-item\"><a class=\"nav-link\" href=\"./PublicManager\">公共资源管理</a></li>\r\n" + 
				"						<!-- 显示所有用户分享的资源列表，此页面仅提供下载 -->	\r\n" + 
				"						\r\n" + 
				"						<li class=\"nav-item\"><a class=\"nav-link\" href=\"./Login?del=true\">退出登录</a></li>\r\n" + 
				"						<!-- 回到登陆界面，清除session -->\r\n" + 
				"\r\n" + 
				"					</ul>\r\n" + 
				"				</div>\r\n" + 
				"			</nav>\r\n" + 
				"		</div>\r\n" +
				"		<div class=\"leftnav\">\r\n" + 
				"			<div class=\"container-fluid\">\r\n" + 
				"				<div class=\"row\">\r\n" + 
				"					<div class=\"col-xs-2 col-sm-2 col-md-2 col-lg-2\">\r\n" + 
				"						<div class=\"admin\">\r\n" + 
				"							<img src=\"images/admin.jpg\">\r\n" + 
				"							<p>Admin</p>\r\n" + 
				"							<table class=\"table\">\r\n" + 
				"								<tr>\r\n" + 
				"									<td>空间占用</td>\r\n" + 
				"									<td>等级</td>\r\n" + 
				"								</tr>\r\n" + 
				"								<tr>\r\n" + 
				"\r\n" + 
				"									<td><a href=\"#0\">+∞</a></td>		\r\n" + 
				"\r\n" + 
				"									<td><a href=\"#0\">ROOT</a></td>\r\n" + 
				"\r\n" + 
				"								</tr>\r\n" + 
				"							</table>\r\n" + 
				"						</div>\r\n" + 
				"					</div>\r\n" + 
				"					<div class=\"col-xs-10 col-sm-10 col-md-10 col-lg-10\">\r\n" + 
				"\r\n" + 
				"						<div class=\"rightarea\">\r\n" + 
				"							<div class=\"righthead\">\r\n" + 
				"								<p>用户及资源管理</p>\r\n" + 
				"\r\n" + 
				"								<hr>\r\n" + 
				"								<form action=\"./FindUser\">	\r\n" + 
				"									<input type=\"text\" name=\"key\" class=\"xinput xround\" placeholder=\"输入用户名\">\r\n" + 
				"									<button class=\"xbutton xround xlit xblue\">搜索</button>\r" + 
				"								</form>"+
				"								<!-- 跳转到新页面在该home范围内搜索文件,target取值为home\\myshare\\allshare,指代在不同范围内搜索 -->\r\n" + 
				"\r\n" + 
				"							</div>");
		out.print("<a href=\"./AdminHome?dir=\">用户>>\r\n");

 
		out.print("<table class=\"table fp\">\r\n" + 
				"								<tr>\r\n" + 
				"									<td></td>\r\n" +  
				"									<td>用户名</td>\r\n" + 
				"									<td>密码</td>\r\n" + 
				"									<td>使用空间</td>\r\n" + 
				"									<td>等级</td>\r\n" + 
				"									<td>操作</td>"+
				"								</tr>");
		
		
		
		if (!userli.isEmpty()) {
			
			for (List<String> li : userli) {
				String userpath = "." + sepp + "userfile" + sepp + li.get(0) + "qiandu";
				Double size = FileUtil.getTotalSizeOfFilesInDir(new File(userpath))/1024.0;
				String si = null;
				String level = "普通用户";
				String pwd = li.get(1);
				if (size < 1024) {
					si = String.format("%.2fKB", size);
				} else {
					si = String.format("%.2fMB", size/1024.0);
				}
				if (li.get(0).equals("admin")) {
					level = "管理员";
					pwd = "**********";
					
				}
				out.print("<tr>\r\n" + 
						"									<td><img width=\"30\" height=\"30\" src=\"images/userhead.png\" /></td>\r\n" + 
						"									<td><a href=\"./UserFile?username="+URLEncoder.encode(li.get(0), "UTF-8")+"\">"+li.get(0)+"</td>\r\n" + 
						"									<td>"+pwd+"</td>\r\n" + 
						"									<td>"+si+"</td>\r\n" + 
						"									<td>"+level+"</td>\r\n" + 
						"									<td><a href=\"./AdminHome?method=delete&username="+URLEncoder.encode(li.get(0), "UTF-8")+"\">删除</a><a href=\"javascript:repwd('"+li.get(0)+"');\">修改密码</a>"+ 
						"								</tr>");
				
			}
		}else {
			out.print("<tr>\r\n" + 
					"										<td>没有更多用户啦</td>\r\n" + 
					"								</tr>");
		}
		
		out.print("							</table>\r\n" + 
				"						</div>\r\n" + 
				"					</div>\r\n" + 
				"				</div>\r\n" + 
				"			</div>\r\n" + 
				"		</div>\r\n" + 
				"	</div>\r\n" + 
				"	</div>\r\n" + 
				"\r\n	<script type=\"text/javascript\">\r\n" + 
				"\r\n" + 
				"		function repwd(name) {\r\n" + 
				"			var pwd = prompt(\"请输入文件夹名字\", \"新建文件夹\");\r\n" + 
				"\r\n" + 
				"			if (pwd === \"\") {\r\n" + 
				"				alert(\"文件名不能为空\");\r\n" + 
				"			}\r\n" + 
				"\r\n" + 
				"			if (pwd)//如果返回的有内容\r\n" + 
				"			{\r\n" + 
				"				location.href = \"./AdminHome?username=\"+name+\"&method=update&pwd=\"\r\n" + 
				"					+ encodeURI(pwd);\r\n" + 
				"			}\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"	</script>" + 
				"\r\n" + 
				"</body>");
		
		
		

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		this.doGet(req, res);
	}

}
