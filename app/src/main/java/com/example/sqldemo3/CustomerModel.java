package com.example.sqldemo3;

// this is the ojbect that holds 1 database entry
// That is the id, name, age, active fields
//
public class CustomerModel {
    private int id;
    private String name;
    private int age;
    private boolean isActive;

    public CustomerModel(int id, String name, int age, boolean isActive) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.isActive = isActive;
    }

    // toString is necessary for printing the contents of a class object
    @Override
    public String toString() {
        return "CustomerModel{ " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age= " + age +
                ", isActive= " + isActive +
                '}' ;
    }

    // getters and setters

    public CustomerModel() {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}