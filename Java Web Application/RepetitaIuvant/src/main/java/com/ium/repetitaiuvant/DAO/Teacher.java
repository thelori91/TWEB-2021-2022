package com.ium.repetitaiuvant.DAO;

import java.util.Objects;

public class Teacher {
    private long ID;
    private String name;
    private String surname;


    public  Teacher(long ID, String name, String surname) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;

    }

    public long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return ID + " " + name + " " + surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return ID == teacher.ID;
    }
}
