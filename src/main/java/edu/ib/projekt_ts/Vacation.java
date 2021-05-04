package edu.ib.projekt_ts;

import java.time.LocalDate;

public class Vacation {

    private int id_employee;
    private LocalDate start_date;
    private LocalDate end_date;

    public Vacation(int id_employee, LocalDate start_date, LocalDate end_date) {
        this.id_employee = id_employee;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    @Override
    public String toString() {
        return "Vacation{" +
                "id_employee=" + id_employee +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                '}';
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
}
