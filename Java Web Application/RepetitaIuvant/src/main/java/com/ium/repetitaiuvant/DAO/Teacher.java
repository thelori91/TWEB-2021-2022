package com.ium.repetitaiuvant.DAO;

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

    public String getNome() {
        return name;
    }

    public String getCognome() {
        return surname;
    }

    @Override
    public String toString() {
        return ID + " " + name + " " + surname;
    }
}
