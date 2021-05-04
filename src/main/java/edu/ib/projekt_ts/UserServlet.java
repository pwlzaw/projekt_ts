package edu.ib.projekt_ts;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    private DBUtilUser dbUtil;
    private final String db_url = "jdbc:mysql://localhost:3306/projektTS?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=CET";


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {

            dbUtil = new DBUtilUser(db_url);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        String name = request.getParameter("loginInput");
        String password = request.getParameter("passwordInput");

        dbUtil.setName(name);
        dbUtil.setPassword(password);

        if (validate(name, password)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/user_view.jsp");

            List<Employee> employeeList = null;

            try {

                employeeList = dbUtil.getEmployee();

            } catch (Exception e) {
                e.printStackTrace();
            }

            // dodanie listy do obiektu zadania
            request.setAttribute("EMPLOYEES_LIST", employeeList);

            dispatcher.forward(request, response);
        } else {

            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
            dispatcher.include(request, response);
        }
    }

    private boolean validate(String name, String pass) {
        boolean status = false;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }

        Connection conn = null;

        try {

            conn = DriverManager.getConnection(db_url, name, pass);
            status = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}
