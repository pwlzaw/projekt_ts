package edu.ib.projekt_ts;

public class Employee {

    private int id;
    private String employee_name;
    private int used;
    private int available;
    private String login;
    private String passwd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Employee(String employee_name, int used, int available, String login, String passwd) {
        this.employee_name = employee_name;
        this.used = used;
        this.available = available;
        this.login = login;
        this.passwd = passwd;
    }

    public Employee(int id, String employee_name, int used, int available, String login, String passwd) {
        this.id = id;
        this.employee_name = employee_name;
        this.used = used;
        this.available = available;
        this.login = login;
        this.passwd = passwd;
    }
}
