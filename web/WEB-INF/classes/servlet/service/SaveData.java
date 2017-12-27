package servlet.service;

import dao.CountryDao;
import entity.Country;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/saveData")
public class SaveData extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher("/WEB-INF/classes/servlet/jsp/private.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dataToSave = req.getParameter("dataToSave");
        Country country = new Country();
        country.setName(dataToSave);
        CountryDao.getInstance().save(country);
        System.out.println("Мы попытались сохранить страну.");
        resp.sendRedirect("/private");
    }
}
