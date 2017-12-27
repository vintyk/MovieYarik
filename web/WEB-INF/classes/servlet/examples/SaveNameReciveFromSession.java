package servlet.examples;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/fullName")
public class SaveNameReciveFromSession extends HttpServlet {

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/http");
        PrintWriter writer = resp.getWriter();
        HttpSession session = req.getSession();
        writer.write(session.getAttribute(FIRST_NAME) + " " + session.getAttribute(LAST_NAME));
        System.out.println(session.getAttribute(FIRST_NAME) + " " + session.getAttribute(LAST_NAME));
    }
}
