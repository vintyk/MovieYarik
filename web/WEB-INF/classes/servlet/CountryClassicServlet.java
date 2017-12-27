package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/country2")
public class CountryClassicServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


//    private String wrapInParagraph(Country country){
//        return "<p>" + country.getId() + ": " + country.getName() +"</p>";
//    }

}
