package edu.ib.projekt_ts;

import jdk.vm.ci.meta.Local;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class DBUtilUser extends DBUtil {

    private String URL;
    private String name;
    private String password;

    public DBUtilUser(String URL) {
        this.URL = URL;
    }


    int getUserAvailableDays(int id) {
        int available = 0;

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "SELECT available FROM employees where id = " + id;
            statement = conn.createStatement();

            // wykonanie zapytania SQL
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                available = Integer.parseInt(resultSet.getString("available"));
            }


        } catch (SQLException e) {

        } finally {
            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }


        return available;
    }

    int getUserUsedDays(int id) {
        int used = 0;

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "SELECT used FROM employees where id = " + id;
            statement = conn.createStatement();

            // wykonanie zapytania SQL
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                used = Integer.parseInt(resultSet.getString("used"));
            }

        } catch (SQLException e) {

        } finally {
            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }


        return used;
    }

    void setUserDays(int id, int available, int used) {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "UPDATE employees SET used=?,available=? WHERE id =?";

            statement = conn.prepareStatement(sql);

            statement.setInt(1, used);
            statement.setInt(2, available);
            statement.setInt(3, id);

            System.out.println("Metoda");
            System.out.println(used);
            System.out.println(available);
            System.out.println(id);

            statement.execute();

        } catch (SQLException e) {

        } finally {
            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }
    }

    int getID(String login) {

        int id = 0;
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "SELECT id FROM employees where login = \"" + login + "\"";
            statement = conn.createStatement();

            // wykonanie zapytania SQL
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                id = Integer.parseInt(resultSet.getString("id"));
            }

        } catch (SQLException e) {

        } finally {
            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }
        return id;
    }

    String getEmployee(int id) throws Exception {

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String result = "";

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "SELECT employee_name, used, available FROM employees where id = " + id;
            statement = conn.createStatement();

            // wykonanie zapytania SQL
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // pobranie danych
                String name = resultSet.getString("employee_name");
                int used = Integer.parseInt(resultSet.getString("used"));
                int available = Integer.parseInt(resultSet.getString("available"));

                result = "Użytkownik: " + name + "\t Liczba wykorzystanych dni wolnego: " + used + "\t Liczba dostępnych dni wolnego: " + available;
            }

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }


        return result;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    Vacation getVacation(int inputId) throws Exception {


        Vacation vacation = null;
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "SELECT * FROM vacations where id = " + inputId;
            statement = conn.createStatement();

            // wykonanie zapytania SQL
            resultSet = statement.executeQuery(sql);

            // przetworzenie wyniku zapytania
            while (resultSet.next()) {

                // pobranie danych z rzedu
                int id = resultSet.getInt("id");
                int idEmployee = resultSet.getInt("id_employee");
                LocalDate start = LocalDate.parse(resultSet.getString("start_date"));
                LocalDate end = LocalDate.parse(resultSet.getString("end_date"));
                String state = resultSet.getString("state");


                // dodanie do listy nowego obiektu
                vacation = new Vacation(id, idEmployee, start, end, state);


            }
            return vacation;

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }
    }

    List<Vacation> getVacations(int inputId) throws Exception {

        List<Vacation> vacations = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "SELECT * FROM vacations where id_employee = " + inputId;
            statement = conn.createStatement();

            // wykonanie zapytania SQL
            resultSet = statement.executeQuery(sql);

            // przetworzenie wyniku zapytania
            while (resultSet.next()) {
                // pobranie danych z rzedu
                int id = resultSet.getInt("id");
                int idEmployee = resultSet.getInt("id_employee");
                LocalDate start = LocalDate.parse(resultSet.getString("start_date"));
                LocalDate end = LocalDate.parse(resultSet.getString("end_date"));
                String state = resultSet.getString("state");

                // dodanie do listy nowego obiektu
                vacations.add(new Vacation(id, idEmployee, start, end, state));
            }
            return vacations;

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }
    }

    public void updateVacation(Vacation vacation) throws Exception {
        Connection conn = null;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;
        ResultSet resultSet = null;
        int difference= 0;

        try {
            int length = (int) DAYS.between(vacation.getStart_date(), vacation.getEnd_date())+1;


            if ((vacation.getState().equals("waiting deletion") || (getUserAvailableDays(vacation.getId_employee()) >= length))&&length>0) {



                conn = DriverManager.getConnection(URL, name, password);

                // zapytanie UPDATE
                String sql = "UPDATE vacations SET state=? WHERE id =?";

                statement = conn.prepareStatement(sql);
                statement.setString(1, vacation.getState());
                statement.setString(2, String.valueOf(vacation.getId()));

                // wykonanie zapytania
                statement.execute();

                if (!vacation.getState().equals("waiting deletion")) {


                    String sql2 = "insert into vacations_to_update(id,id_employee,start_date,end_date,state) values (?,?,?,?,?)";

                    statement2 = conn.prepareStatement(sql2);
                    statement2.setString(1, String.valueOf(vacation.getId()));
                    statement2.setString(2, String.valueOf(vacation.getId_employee()));
                    statement2.setString(3, String.valueOf((vacation.getStart_date())));
                    statement2.setString(4, String.valueOf((vacation.getEnd_date())));
                    statement2.setString(5, "New Value");
                    statement2.execute();


                    String sql3 = "select * from vacations where id=?";

                    statement3 = conn.prepareStatement(sql3);
                    statement3.setString(1, String.valueOf(vacation.getId()));
                    statement3.execute();


                    resultSet = statement.executeQuery(sql3);

                    // przetworzenie wyniku zapytania
                    while (resultSet.next()) {
                        // pobranie danych z rzedu
                        LocalDate start_date = LocalDate.parse(resultSet.getString("start_date"));
                        LocalDate end_date = LocalDate.parse(resultSet.getString("end_date"));
                        difference = (int)DAYS.between(start_date,end_date);

                    }

                    setUserDays(vacation.getId_employee(), getUserAvailableDays(vacation.getId_employee()) +difference, getUserUsedDays(vacation.getId_employee()) - difference);


                    setUserDays(vacation.getId_employee(), getUserAvailableDays(vacation.getId_employee()) - length, getUserUsedDays(vacation.getId_employee()) + length);

                }else{
                    setUserDays(vacation.getId_employee(), getUserAvailableDays(vacation.getId_employee()) + length, getUserUsedDays(vacation.getId_employee()) - length);
                }
            }
        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

    }

    public void addVacation(Vacation vacation) throws Exception {

        int length = (int) DAYS.between(vacation.getStart_date(), vacation.getEnd_date())+1;


        if ((getUserAvailableDays(vacation.getId_employee()) >= length)&&length>0) {
            setUserDays(vacation.getId_employee(), getUserAvailableDays(vacation.getId_employee()) - length, getUserUsedDays(vacation.getId_employee()) + length);


            Connection conn = null;
            PreparedStatement statement = null;

            try {
                // polaczenie z BD
                conn = DriverManager.getConnection(URL, name, password);

                // zapytanie INSERT i ustawienie jego parametrow
                String sql = "INSERT INTO vacations(id_employee,start_date,end_date,state) " +
                        "VALUES(?,?,?,?)";

                statement = conn.prepareStatement(sql);
                statement.setInt(1, vacation.getId_employee());
                statement.setString(2, vacation.getStart_date().toString());
                statement.setString(3, vacation.getEnd_date().toString());
                statement.setString(4, "waiting acceptation");

                // wykonanie zapytania
                statement.execute();


            } finally {

                close(conn, statement, null);

            }
        }
    }
}
