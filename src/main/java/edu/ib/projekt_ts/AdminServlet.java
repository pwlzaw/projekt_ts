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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        String name = request.getParameter("loginInput");
        String password = request.getParameter("passwordInput");

        dbUtil.setName(name);
        dbUtil.setPassword(password);

        if (validate(name, password)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin_view.jsp");

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

            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin_login.html");
            dispatcher.include(request, response);
        }
    }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {


            try {

                // odczytanie zadania
                String command = request.getParameter("command");

                if (command == null)
                    command = "LIST";

                switch (command) {

                    case "LIST":
                        listVacations(request, response);
                        break;
                        
                    case "UPDATE":
                        updateVacations(request, response);
                        break;

                    case "DELETE":
                        deleteVacations(request, response);
                        break;

                    default:
                        listVacations(request, response);
                }

            } catch (Exception e) {
                throw new ServletException(e);
            }

        }

    private void deleteVacations(HttpServletRequest request, HttpServletResponse response) {
    }

    private void updateVacations(HttpServletRequest request, HttpServletResponse response) {
    }

    private void listVacations(HttpServletRequest request, HttpServletResponse response) {
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
