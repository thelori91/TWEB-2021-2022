package com.ium.repetitaiuvant.DAO;

public class Lesson {
    //Primary key
    private long id;
    //Foreign key
    private Teacher teacher;
    private Course course;
    private User user;
    //Other fields
    private Day day;
    private Time time;


    public Lesson(long id, Teacher teacher, Course course, User user, Day day, Time time) {
        this.id = id;
        this.teacher = teacher;
        this.course = course;
        this.user = user;
        this.day = day;
        this.time = time;
    }

    //Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    //Getters
    public long getId() {
        return id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Course getCourse() {
        return course;
    }

    public User getUser() {
        return user;
    }

    public Day getDay() {
        return day;
    }

    public Time getTime() {
        return time;
    }
}
