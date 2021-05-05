package edu.ib.projekt_ts;

import java.time.LocalDate;

public class Vacation {

    private int id_employee;
    private LocalDate start_date;
    private LocalDate end_date;
    private String state;

    public Vacation(int id_employee, LocalDate start_date, LocalDate end_date, String state) {
        this.id_employee = id_employee;
        this.start_date = start_date;
        this.end_date = end_date;
        this.state = state;
    }

    public int getId_employee() {
        return id_employee;
    }

    public void setId_employee(int id_employee) {
        this.id_employee = id_employee;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
