package edu.ib.projekt_ts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtilRegister extends DBUtil{

    private String URL;
    private String name="servlet";
    private String password="servlet";

    public DBUtilRegister(String URL) {
        this.URL = URL;
    }

    public boolean checkLogin(String login) throws Exception{
        boolean status=true;
        int count=0;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            String sql = "select count(*) from employees where login = '"+login+"'";

            statement = conn.prepareStatement(sql);

            // wykonanie zapytania
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                count = Integer.parseInt(resultSet.getString("count(*)"));
                if (count>0)
                    status=false;
            }

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }

        return status;
    }

    public void register(String login, String password_employee, String name_employee) throws Exception{
        Connection conn = null;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;
        PreparedStatement statement4 = null;
        PreparedStatement statement5 = null;

        try {

            // polaczenie z BD
            conn = DriverManager.getConnection(URL, name, password);

            // zapytanie UPDATE
            String sql = "CREATE USER '"+login+"'@'localhost' IDENTIFIED BY '"+password_employee+"'";
            String sql2 = "insert into employees (employee_name,used,available,login,passwd,user_type) values ('"
                    +name_employee+"',0,26,'"+login+"','"+password_employee+"','user')";
            String sql3 = "grant select,insert,update on vacations to '"+login+"'@'localhost'";
            String sql4 = "grant select on employees to '"+login+"'@'localhost'";
            String sql5 = "grant insert on vacations_to_update to '"+login+"'@'localhost'";

            statement = conn.prepareStatement(sql);
            statement2 = conn.prepareStatement(sql2);
            statement3 = conn.prepareStatement(sql3);
            statement4 = conn.prepareStatement(sql4);
            statement5 = conn.prepareStatement(sql5);

            // wykonanie zapytania
            statement.execute();
            statement2.execute();
            statement3.execute();
            statement4.execute();

        } finally {

            // zamkniecie obiektow JDBC
            close(conn, statement, null);

        }
    }
}
