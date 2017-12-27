package servlet.service;

import dao.CountryDao;
import entity.Country;
import entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/private")
public class PrivateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("name", "VINTY");
        req.setAttribute("user", new User(
                1L,
                "Ярослав",
                "Зыскунов",
                "LKGhost",
                "lk@gmail.com"));
//-------------------------------------------------------
        req.setAttribute("country1", new Country(1L, "USA"));
        req.setAttribute("country2", new Country(2L, null));

        List<Country> countryList = new ArrayList<>();
        countryList = CountryDao.getInstance().findAll();
        req.setAttribute("countries", countryList);


        getServletContext().getRequestDispatcher("/WEB-INF/classes/servlet/jsp/private.jsp")
        .forward(req, resp);
    }
}
