package edu.ib.projekt_ts;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private DBUtilRegister dbUtil;
    private final String db_url = "jdbc:mysql://localhost:3306/projektTS?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=CET";


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {

            dbUtil = new DBUtilRegister(db_url);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if(dbUtil.checkLogin(login)) {
                if (password.equals(password2)) {
                    try {
                        dbUtil.register(login,password,name);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
                        dispatcher.forward(request, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                   // request.setAttribute("WRONG_PASS", "Password mismatch");
                }
            }else {
               // request.setAttribute("WRONG_LOGIN", "User already exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
