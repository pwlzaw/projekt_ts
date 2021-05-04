package edu.ib.projekt_ts;

public class Employee {

    private int id;
    private String name;
    private int used;
    private int available;

    public Employee(int id, String name, int used, int available) {
        this.id = id;
        this.name = name;
        this.used = used;
        this.available = available;
    }

    public Employee(String name, int used, int available) {
        this.name = name;
        this.used = used;
        this.available = available;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", used=" + used +
                ", available=" + available +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
