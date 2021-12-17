package com.ium.repetitaiuvant.DAO;

public class Course {
    private String Name;

    public Course(String name) {
        this.Name = name;
    }

    public String getName() {
        return Name;
    }

    @Override
    public String toString() {
        return "Course: " + Name;
    }
}
