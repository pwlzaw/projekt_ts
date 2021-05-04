package edu.ib.projekt_ts;

import java.util.List;

public class DBUtilAdmin extends DBUtil{

    private String URL;
    private String name;
    private String password;

    public DBUtilAdmin(String URL) {
        this.URL = URL;
    }


    @Override
    List<Employee> getEmployee() throws Exception {
        return null;
    }
}
