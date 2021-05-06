package edu.ib.projekt_ts;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBUtilUser extends DBUtil{

    private String URL;
    private String name;
    private String password;

    public DBUtilUser(String URL) {
        this.URL = URL;
    }


    int getID(String login) {

        int id = 0;
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            System.out.println("test");
            // zapytanie SELECT
            String sql = "SELECT id FROM employees where login = \"" + login + "\"";
            statement = conn.createStatement();

            // wykonanie zapytania SQL
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                id = Integer.parseInt(resultSet.getString("id"));
            }

        }catch (SQLException e){

        }finally {
            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }
        return id;
    }

    String getEmployee(int id) throws Exception {

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String result="";

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "SELECT employee_name, used, available FROM employees where id = "+id;
            statement = conn.createStatement();

            // wykonanie zapytania SQL
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // pobranie danych
                String name = resultSet.getString("employee_name");
                int used = Integer.parseInt(resultSet.getString("used"));
                int available = Integer.parseInt(resultSet.getString("available"));

                result = "User: " + name + "Vacation days used: " + used + "Vacation days available: " + available;
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

    Vacation getVacation(int inputId) throws Exception{


        Vacation vacation = null;
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "SELECT * FROM vacations where id = "+inputId;
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
                vacation = new Vacation(id,idEmployee, start,end,state);


            }
            return vacation;

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }
    }

    List<Vacation> getVacations(int inputId) throws Exception{

        List<Vacation> vacations = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "SELECT * FROM vacations where id_employee = "+inputId;
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
                vacations.add(new Vacation(id,idEmployee, start,end,state));
            }
            return vacations;

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }
    }

    public void updateVacation(Vacation vacation) throws Exception{
        Connection conn = null;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie UPDATE
            String sql = "UPDATE vacations SET state=?" +
                    "WHERE id =?";

            statement = conn.prepareStatement(sql);
            statement.setString(1, vacation.getState());
            statement.setString(2, String.valueOf(vacation.getId()));

            // wykonanie zapytania
            statement.execute();

            String sql2 = "insert into vacations_to_update(id,id_employee,start_date,end_date,state) values (?,?,?,?,?)";

            statement2 = conn.prepareStatement(sql2);
            statement2.setString(1, String.valueOf(vacation.getId()));
            statement2.setString(2, String.valueOf(vacation.getId_employee()));
            statement2.setString(3, String.valueOf((vacation.getStart_date())));
            statement2.setString(4, String.valueOf((vacation.getEnd_date())));
            statement2.setString(5, "New Value");


            statement2.execute();


        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

    }

    public void addVacation(Vacation vacation) throws Exception{

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
