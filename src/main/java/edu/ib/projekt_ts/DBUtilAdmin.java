package edu.ib.projekt_ts;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBUtilAdmin extends DBUtil{

    private String URL;
    private String name;
    private String password;

    public DBUtilAdmin(String URL) {
        this.URL = URL;
    }


    boolean hasPermision(String login) throws Exception{
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String type ="";
        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie SELECT
            String sql = "SELECT user_type FROM employees where login = '"+login+"'";
            statement = conn.createStatement();

            // wykonanie zapytania SQL
            resultSet = statement.executeQuery(sql);

            // przetworzenie wyniku zapytania

            while (resultSet.next()) {
                type = resultSet.getString("user_type");
            }
            return type.equals("admin");
        } finally {
            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }
    }



    List<Vacation> getVacations() throws Exception {

        List<Vacation> vacations = new ArrayList<>();

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // waiting acceptation   waiting change acceptation waiting deletion
            String sql = "SELECT * FROM vacations where state= 'waiting acceptation' or state='waiting change acceptation' or state= 'waiting deletion'";
            statement = conn.createStatement();

            // wykonanie zapytania SQL
            resultSet = statement.executeQuery(sql);

            // przetworzenie wyniku zapytania
            while (resultSet.next()) {

                // pobranie danych z rzedu
                int id = Integer.parseInt(resultSet.getString("id"));
                int idEmployee = Integer.parseInt(resultSet.getString("id_employee"));
                LocalDate start = LocalDate.parse(resultSet.getString("start_date"));
                LocalDate end = LocalDate.parse(resultSet.getString("end_date"));
                String state = resultSet.getString("state");


                // dodanie do listy nowego obiektu
                vacations.add(new Vacation(id,idEmployee, start, end, state));

            }
            sql = "SELECT * FROM vacations_to_update";
            statement = conn.createStatement();

            // wykonanie zapytania SQL
            resultSet = statement.executeQuery(sql);

            // przetworzenie wyniku zapytania
            while (resultSet.next()) {

                // pobranie danych z rzedu
                int id = Integer.parseInt(resultSet.getString("id"));
                int idEmployee = Integer.parseInt(resultSet.getString("id_employee"));
                LocalDate start = LocalDate.parse(resultSet.getString("start_date"));
                LocalDate end = LocalDate.parse(resultSet.getString("end_date"));
                String state = resultSet.getString("state");


                // dodanie do listy nowego obiektu
                vacations.add(new Vacation(id,idEmployee, start, end, state));

            }
            vacations.sort((v1,v2) -> {
                if(v1.getId()>v2.getId())
                    return 1;
                else
                    return -1;

            });

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, resultSet);
        }
        return vacations;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void deleteVacation(int id) throws Exception{
        Connection conn = null;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie UPDATE
            String sql = "delete from vacations WHERE id ="+id;

            statement = conn.prepareStatement(sql);

            // wykonanie zapytania
            statement.execute();

            String sql2 = "delete from vacations_to_update where id ="+id;

            statement2 = conn.prepareStatement(sql2);

            statement2.execute();


        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }
    }

    public void updateVacation(Vacation vacation) throws Exception{
        Connection conn = null;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;

        try {
            if(vacation.getState().equals("denied")){
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

            }else if (vacation.getState().equals("accepted")){
                // polaczenie z BD
                conn = DriverManager.getConnection(URL, name, password);

                // zapytanie UPDATE
                String sql = "UPDATE vacations SET state=?,start_date=?,end_date=? " +
                        "WHERE id =?";

                statement = conn.prepareStatement(sql);
                statement.setString(1, vacation.getState());
                statement.setString(2, vacation.getStart_date().toString());
                statement.setString(3, vacation.getEnd_date().toString());
                statement.setString(4, String.valueOf(vacation.getId()));

                // wykonanie zapytania
                statement.execute();
            }

            String sql2 = "delete from vacations_to_update where id =?";

            statement2 = conn.prepareStatement(sql2);
            statement2.setString(1, String.valueOf(vacation.getId()));


            statement2.execute();


        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }
    }
}
