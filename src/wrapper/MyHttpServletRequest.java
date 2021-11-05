package wrapper;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyHttpServletRequest extends HttpServletRequestWrapper {

	HttpServletRequest request;

	public MyHttpServletRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	public String getParameter(String name) {

		String value = request.getParameter(name);

		if (value == null)
			return null;

		// 只考虑get方式
		String method = request.getMethod();

		if ("get".equalsIgnoreCase(method)) {
			try {
				value = new String(value.getBytes("iso-8859-1"),
						request.getCharacterEncoding());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return value;

	}

}

