package edu.ib.projekt_ts;

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



    String getEmployee(int id) throws Exception {

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String result="";

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "SELECT employee_name, used, available FROM employees where id_employee = "+id;
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

    List<Vacation> getVacations(int idEmployee) throws Exception{

        List<Vacation> vacations = new ArrayList<>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "SELECT * FROM vacations where id_employee = "+idEmployee;
            statement = conn.createStatement();

            // wykonanie zapytania SQL
            resultSet = statement.executeQuery(sql);

            // przetworzenie wyniku zapytania
            while (resultSet.next()) {

                // pobranie danych z rzedu
                int id = resultSet.getInt("id");
                LocalDate start = LocalDate.parse(resultSet.getString("start_date"));
                LocalDate end = LocalDate.parse(resultSet.getString("end_date"));
                String state = resultSet.getString("state");


                // dodanie do listy nowego obiektu
                vacations.add(new Vacation(id,idEmployee, start,end,state));

            }

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }


        return vacations;
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

            String sql2 = "insert into vacations_to_update(id_employee,start_date,end_date,state) values (?,?,?,?)";

            statement2 = conn.prepareStatement(sql2);
            statement2.setString(1, String.valueOf(vacation.getId_employee()));
            statement2.setString(2, String.valueOf((vacation.getStart_date())));
            statement2.setString(3, String.valueOf((vacation.getEnd_date())));
            statement2.setString(4, (vacation.getState()));


            statement2.execute();


        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

    }

    public void addVacation(Vacation vacation) {
    }
}
