package servlet;
import dao.CountryDao;
import entity.Country;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet("/country")
public class CountryServlet extends HttpServlet {

    //Get запрос на эту страницу (/country) отрабатывает каждый раз при заходе и F5
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        //writer умеет писать посимвольно. В него будем пихать текст
        PrintWriter writer = resp.getWriter();
        //Получаем список объектов Country из БД
        // (В объект типа Set<Country> все пишем, это что-то типа HasgMap)
        String result = CountryDao.getInstance().getUniqueAll()
                // через стрим читаем весь список
                .stream()
                //для каждой записи из этого списка запускаем метод: "wrapInParagraph(Country country)"
                //этот медод почти то же самое что ToString
                //он разбирает объект и красиво его показывает
                // а показывать мы будем на Web странице - поэтому в ТЭГе <p></p>
                .map(this::wrapInParagraph)
                //после того, как метод wrapInParagraph(Country country) отработал
                //получилась строка. Эту строку склеиваем  через .joining()
                // с предыдущей строкой.
                // Внутри получится запись типа: <p> 5: Belarus</p><p>1:Russia</p> и т.д
                //Все записи из HashSet слеятся. Стрим их склеит.
                .collect(Collectors.joining());
        //Ну и результат этой склейки поместить на HTML страницу.
        writer.write(result);

    }
    private String wrapInParagraph(Country country){
        return "<p>" + country.getId() + ": " + country.getName() +"</p>";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String collect = req
                .getReader()
                .lines()
                .collect(Collectors.joining("\n"));
        System.out.printf(collect);
    }
}
