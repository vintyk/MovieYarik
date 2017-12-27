package servlet.examples;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/session")
public class SessionServlet extends HttpServlet {

    public static final String SESSION_ATTRIBUTE_NAME = "name";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        HttpSession session = req.getSession();
        if (session.getAttribute(SESSION_ATTRIBUTE_NAME) == null) {
            session.setAttribute(SESSION_ATTRIBUTE_NAME, "Vinty");
            writer.write("Сессия создана!");
        } else {
            System.out.println(session.getAttribute(SESSION_ATTRIBUTE_NAME));
            writer.write("Уже есть!");
        }
    }
}
