package servlet.examples;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/uniqueCookie")
public class UniqueVisitCookieServlet extends HttpServlet {
    private static final String COOKIE_NAME = "visits";
    public static final int A_DAY = 24 * 60 * 60;
    private AtomicInteger counter = new AtomicInteger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        boolean cookieExists = (cookies !=null && Arrays.stream(cookies)
                .anyMatch(cookie -> cookie.getName().equals(COOKIE_NAME)));
        if (!cookieExists){
            Cookie cookie = new Cookie(COOKIE_NAME, "someValue");
            cookie.setMaxAge(A_DAY);
            counter.incrementAndGet();
            resp.addCookie(cookie);
        }
        resp.setContentType("text/html");
        resp.getWriter().write("Число уникальных посещений" + counter.get());
    }
}
