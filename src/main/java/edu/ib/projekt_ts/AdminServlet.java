package edu.ib.projekt_ts;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {

    private DBUtilAdmin dbUtil;
    private final String db_url = "jdbc:mysql://localhost:3306/projektTS?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=CET";


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {

            dbUtil = new DBUtilAdmin(db_url);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
