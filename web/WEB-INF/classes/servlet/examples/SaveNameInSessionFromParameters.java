package servlet.examples;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/saveName")
public class SaveNameInSessionFromParameters extends HttpServlet {

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute(FIRST_NAME, req.getParameter(FIRST_NAME));
        session.setAttribute(LAST_NAME, req.getParameter(LAST_NAME));
    }
}
