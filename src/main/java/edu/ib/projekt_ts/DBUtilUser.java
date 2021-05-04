package edu.ib.projekt_ts;

import java.util.List;

public class DBUtilUser extends DBUtil{

    private String URL;
    private String name;
    private String password;

    public DBUtilUser(String URL) {
        this.URL = URL;
    }


    @Override
    List<Employee> getEmployee() throws Exception {
        return null;
    }
}
