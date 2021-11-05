package view;

import util.FileUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PublicManagerView extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		PrintWriter out = res.getWriter();
		String sep = "/|\\\\";
		String sepp = System.getProperty("file.separator");
		String dir = (String) req.getAttribute("dir");
		ArrayList<ArrayList> filetotal = (ArrayList<ArrayList>) req.getAttribute("filetotal");
		ArrayList<ArrayList> dirtotal = (ArrayList<ArrayList>) req.getAttribute("dirtotal");
		
		if (dir == null || dir.equals("")) {
			dir = sepp;
		}

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
				"	<script src=\"js/jquery.min.js\" type=\"text/javascript\" charset=\"utf-8\"></script>\r\n" + 
				"	<script type=\"text/javascript\">\r\n" + 
				"		$(function () {\r\n" + 
				"			//当点击\"一键上传\"按钮的时候,触发表单中的浏览文件的操作\r\n" + 
				"			$(\"#xxx\").click(function () {\r\n" + 
				"				$(\"#uploadfile\").click();\r\n" + 
				"			})\r\n" + 
				"			//当选择好上传文件后,执行提交表单的操作\r\n" + 
				"			$(\"#uploadfile\").change(function () {\r\n" + 
				"				$(\"#form1\").submit();\r\n" + 
				"			})\r\n" + 
				"		})\r\n" + 
				"	</script>\r\n" + 
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
				"						<p>千度网盘</p>\r\n" + 
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
				"								<p>公共资源管理</p>\r\n" + 
				"\r\n" + 
				"								<button id=\"xxx\" type=\"button\" class=\"btn btn-primary-outline btn-sm\">					\r\n" + 
				"									上传资源\r\n" + 
				"								</button>\r\n" + 
				"								<form style=\"display: none;\" id=\"form1\" action=\"./PublicManager?dir="+URLEncoder.encode(dir, "UTF-8")+"&method=upload\" method=\"post\" enctype=\"multipart/form-data\" >\r\n" + 
				"									<input type=\"file\" name=\"uploadfile\" id=\"uploadfile\" />\r\n" + 
				"								</form>\r\n" + 
				"								<!-- 上传页面，点击即可进入，可上传一个或多个资源到当前的文件夹，全部上传成功后跳回来 -->\r\n" + 
				"\r\n" + 
				"								</br>\r\n" + 
				"								<button onclick=\"prom()\" type=\"button\" class=\"btn btn-primary-outline btn-sm\">			\r\n" + 
				"									新建文件夹\r\n" + 
				"								</button>\r\n" + 
				"\r\n" + 
				"								<hr>\r\n" + 
				"								<form action=\"./FindFileAdmin\" >	\r\n" + 
				"									<input type=\"text\" name=\"key\" class=\"xinput xround\" placeholder=\"搜索宁的文件\">\r\n" + 
				"									<button class=\"xbutton xround xlit xblue\">搜索</button>\r\n" + 
				"								</form>\r\n" + 
				"								<!-- 跳转到新页面在该home范围内搜索文件,target取值为home\\myshare\\allshare,指代在不同范围内搜索 -->\r\n" + 
				"\r\n" + 
				"							</div>");
		
		
		out.print("<a href=\"./PublicManager?dir=\">公共资源管理</a>>>	\r\n");
		
		String m = sepp;
		for (String str : Arrays.asList(dir.split(sep))) {
			if (!str.trim().equals("")) {
				m += str + sepp;
				out.print("<a href=\"./PublicManager?dir="+URLEncoder.encode(m, "UTF-8")+"\">"+str+"</a>>>	\r\n");
			}
		} 

		
			
			
		out.print("<table class=\"table fp\">\r\n" + 
				"								<tr>\r\n" + 
				"									<td></td>\r\n" + 
				"									<td>文件名</td>\r\n" + 
				"									<td>上传者</td>\r\n" + 
				"									<td>上传时间</td>\r\n" + 
				"									<td>大小</td>\r\n" + 
				"									<td>类型</td>\r\n" + 
				"									<td>操作</td>\r\n" + 
				"								</tr>");
		
		if (!(filetotal.isEmpty() && dirtotal.isEmpty())) {
			
			for (ArrayList dli : dirtotal) {
				out.print("<tr>\r\n" + 
						"									<td><img width=\"50\" height=\"30\" src=\"images/1024.png\" /></td>\r\n" + 
						"									<td><a href=\"./PublicManager?dir="+URLEncoder.encode(dir+dli.get(0)+sepp, "UTF-8")+"\">"+dli.get(0)+"</a></td>\r\n" + 
						"									<td>-</td>\r\n" + 
						"									<td>"+dli.get(1)+"</td>\r\n" + 
						"									<td>"+dli.get(2)+"</td>\r\n" + 
						"									<td>"+dli.get(3)+"</td>\r\n" + 
						"									<td><a href=\"./PublicManager?method=delete&ifdir=true&dir="+URLEncoder.encode(dir, "UTF-8")+"&name="+dli.get(0)+"\">删除</a></td>\r\n" + 
						"									<!-- 删除后，弹出删除成功的信息，然后回到原页面，重命名、分享也要如此 -->\r\n" + 
						"								</tr>");
				
			}
			
			for (ArrayList fli : filetotal) {
				out.print("<tr>\r\n" + 
						"									<td><img width=\"45\" height=\"30\" src=\"images/02.png\" /></td>\r\n" + 
						"									<td>"+fli.get(0)+"</td>\r\n" + 
						"									<td>"+fli.get(1)+"</td>\r\n" + 
						"									<td>"+fli.get(2)+"</td>\r\n" + 
						"									<td>"+fli.get(3)+"</td>\r\n" + 
						"									<td>"+fli.get(4)+"</td>\r\n" + 
						"									<td><a href=\"./PublicManager?method=delete&dir="+URLEncoder.encode(dir, "UTF-8")+"&name="+fli.get(0)+"\">删除</a><a href=\"./DownloadFile?target=public&dir="+URLEncoder.encode(dir, "UTF-8")+"&name="+fli.get(0)+"\">下载</a></td>\r\n" + 
						"									<!-- 点击下载 -->\r\n" + 
						"								</tr>");
				
			}
			
			
			
		} else {
			out.print("<tr>\r\n" + 
					"										<td>对不起，没有文件了</td>\r\n" + 
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
				"\r\n" + 
				"	<script type=\"text/javascript\">\r\n" + 
				"\r\n" + 
				"		function prom() {\r\n" + 
				"			var name = prompt(\"请输入文件夹名字\", \"新建文件夹\");\r\n" + 
				"\r\n" + 
				"			if (name === \"\") {\r\n" + 
				"				alert(\"文件名不能为空\");\r\n" + 
				"			}\r\n" + 
				"\r\n" + 
				"			if (name)//如果返回的有内容\r\n" + 
				"			{\r\n" + 
				"				location.href = \"./PublicManager?dir=\"+encodeURI('"+ FileUtil.addslashes(dir) +"')+\"&method=create&name=\"\r\n" +
				"					+ encodeURI(name);				\r\n" + 
				"			}\r\n" + 
				"\r\n" + 
				"		}\r\n" + 
				"	</script>\r\n" + 
				"\r\n" + 
				"</body>");
		
		  


	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		this.doGet(req, res);
	}

}

