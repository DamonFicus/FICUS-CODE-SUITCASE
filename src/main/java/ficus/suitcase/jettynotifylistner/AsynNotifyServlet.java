package ficus.suitcase.jettynotifylistner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by DamonFicus on 2018/10/9.
 * @author DamonFicus
 */
public class AsynNotifyServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        /**
         * servlet的service中处理jetty监听到的回调请求
         * 在这里做集中统一的处理;
         */
    }
}
