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
        int id = Integer.parseInt(request.getParameter("id_employee"));

        dbUtil.setName(name);
        dbUtil.setPassword(password);

        if (validate(name, password)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/user_view.jsp");

            List<Vacation> vacationList = null;

            try {

                vacationList = dbUtil.getVacations(id);

            } catch (Exception e) {
                e.printStackTrace();
            }

            // dodanie listy do obiektu zadania
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

    private void deleteVacations(HttpServletRequest request, HttpServletResponse response) throws Exception{
        int id = Integer.parseInt(request.getParameter("id"));
        int idEmployee = Integer.parseInt(request.getParameter("id_employee"));
        LocalDate start = LocalDate.parse(request.getParameter("start_date"));
        LocalDate end = LocalDate.parse(request.getParameter("end_date"));
        LocalDate state = LocalDate.parse(request.getParameter("state"));

        Vacation vacation = new Vacation(id, start, end, "waiting deletion");

        // uaktualnienie danych w BD
        dbUtil.updateVacation(vacation);

        // wyslanie danych do strony z lista urlopów
        listVacations(request, response);
    }

    private void changeVacations(HttpServletRequest request, HttpServletResponse response) throws Exception{
        int id = Integer.parseInt(request.getParameter("id"));
        int idEmployee = Integer.parseInt(request.getParameter("id_employee"));
        LocalDate start = LocalDate.parse(request.getParameter("start_date"));
        LocalDate end = LocalDate.parse(request.getParameter("end_date"));
        LocalDate state = LocalDate.parse(request.getParameter("state"));

            Vacation vacation = new Vacation(id, start, end, "waiting change acceptation");

            // uaktualnienie danych w BD
            dbUtil.updateVacation(vacation);


        // wyslanie danych do strony z lista urlopów
        listVacations(request, response);

    }

    private void addVacations(HttpServletRequest request, HttpServletResponse response) throws Exception{

        int idEmployee = Integer.parseInt(request.getParameter("id_employee"));
        LocalDate start = LocalDate.parse(request.getParameter("start_date"));
        LocalDate end = LocalDate.parse(request.getParameter("end_date"));
        LocalDate state = LocalDate.parse(request.getParameter("state"));

        int available = Integer.parseInt(request.getParameter("available"));

        if (available >= DAYS.between(start,end)) {

            Vacation vacation = new Vacation(id, start, end, "waiting acceptation");

            // uaktualnienie danych w BD
            dbUtil.addVacation(vacation);

            // wyslanie danych do strony z lista urlopów
            listVacations(request, response);

        }else {
                request.setAttribute("FAIL_RESPONSE","Not enough available vacation days");
            }

    }

    private void listVacations(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id_employee"));
        String user = dbUtil.getEmployee(id);
        List<Vacation> resortList = dbUtil.getVacations(id);

        // dodanie listy do obiektu zadania
        request.setAttribute("USER_INFO", user);
        request.setAttribute("VACATIONS_LIST", resortList);

        // dodanie request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin_view.jsp");

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
