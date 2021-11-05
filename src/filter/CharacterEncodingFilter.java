package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wrapper.MyHttpServletRequest;

public class CharacterEncodingFilter implements Filter {

	private FilterConfig filterConfig;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request=(HttpServletRequest) req;
		HttpServletResponse response=(HttpServletResponse) res;
		
		String encode=filterConfig.getInitParameter("encode");
		
		request.setCharacterEncoding(encode);
		response.setContentType("text/html;charset="+encode);
		
		MyHttpServletRequest mrequest=new MyHttpServletRequest(request);
		
		chain.doFilter(mrequest, response);
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig=filterConfig;
	}

}

