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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

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


            int id=0;
            RequestDispatcher dispatcher = request.getRequestDispatcher("/user_view.jsp");
            List<Vacation> vacationList = null;

            try {
                id = dbUtil.getID(name);
                vacationList = dbUtil.getVacations(id);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // dodanie listy do obiektu zadania
            String user = null;
            try {
                user = dbUtil.getEmployee(dbUtil.getID(name));
            } catch (Exception e) {
                e.printStackTrace();
            }
            request.setAttribute("USER_INFO", user);
            request.setAttribute("EmployeeID", id);
            request.setAttribute("VACATION_LIST", vacationList);

            dispatcher.forward(request, response);
        } else {

            RequestDispatcher dispatcher = request.getRequestDispatcher("/user_login.html");
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

                case "LOAD":
                    loadVacation(request, response);
                    break;

                case "ADD":
                    addVacations(request, response);
                    break;

                case "CHANGE":
                    changeVacations(request, response);
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

    private void loadVacation(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("vacationID"));
        Vacation vacation = dbUtil.getVacation(id);
        request.setAttribute("VACATION", vacation);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/edit_vacation.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteVacations(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("vacationID"));
        Vacation tempVacation = dbUtil.getVacation(id);

        Vacation vacation = new Vacation(tempVacation.getId(), tempVacation.getId_employee(), tempVacation.getStart_date(), tempVacation.getEnd_date(), "waiting deletion");

        // uaktualnienie danych w BD
        dbUtil.updateVacation(vacation);

        // wyslanie danych do strony z lista urlopów
        listVacations(request, response);
    }

    private void changeVacations(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        int idEmployee = Integer.parseInt(request.getParameter("id_employee"));
        LocalDate start = LocalDate.parse(request.getParameter("start_date"));
        LocalDate end = LocalDate.parse(request.getParameter("end_date"));

        Vacation vacation = new Vacation(id, idEmployee, start, end, "waiting change acceptation");

        // uaktualnienie danych w BD
        dbUtil.updateVacation(vacation);


        // wyslanie danych do strony z lista urlopów
        listVacations(request, response);

    }

    private void addVacations(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int idEmployee = Integer.parseInt(request.getParameter("id_employee"));
        LocalDate start = LocalDate.parse(request.getParameter("start_date"));
        LocalDate end = LocalDate.parse(request.getParameter("end_date"));
        Vacation vacation = new Vacation(idEmployee, start, end, "waiting acceptation");

        // uaktualnienie danych w BD
        dbUtil.addVacation(vacation);

        // wyslanie danych do strony z lista urlopów
        listVacations(request, response);
    }

    private void listVacations(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int idEmployee = Integer.parseInt(request.getParameter("id_employee"));
        String user = dbUtil.getEmployee(idEmployee);
        List<Vacation> resortList = dbUtil.getVacations(idEmployee);

        // dodanie listy do obiektu zadania

        request.setAttribute("EmployeeID", idEmployee);
        request.setAttribute("USER_INFO", user);
        request.setAttribute("VACATION_LIST", resortList);

        // dodanie request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user_view.jsp");

        // przekazanie do JSP
        dispatcher.forward(request, response);

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
