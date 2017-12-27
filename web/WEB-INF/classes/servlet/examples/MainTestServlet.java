package servlet.examples;

import entity.Country;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/test")
public class MainTestServlet  extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Country country = new Country();
        country.setId(1L);
        country.setName("USA");

        resp.setContentType("text/html");
        resp.getWriter().write("<p> "+ country.getId()   + " </p>");
        resp.getWriter().write("<p> "+ country.getName() + " </p>");
//        PrintWriter writer = resp.getWriter();
//        writer.println("<h2> Вот и я!</h2>");
    }



}
