package startup;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.File;
import java.io.IOException;

public class FirstServlet extends GenericServlet {

    @Override
    public void init() throws ServletException {

        File f1 = new File("./userfile");
        File f2 = new File("./publicshare");
        if (!f1.exists()) {
            f1.mkdir();
        }
        if (!f2.exists()) {
            f2.mkdir();
        }
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
    }
}
