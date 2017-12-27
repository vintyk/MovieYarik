package servlet.examples;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/getCookie")
public class GetCookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie nameCookie = Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals("nameCookie"))
                .findFirst().orElse(null);
        if (nameCookie != null){
            System.out.println(nameCookie);
            System.out.println(nameCookie.getValue());
        }
    }
}
