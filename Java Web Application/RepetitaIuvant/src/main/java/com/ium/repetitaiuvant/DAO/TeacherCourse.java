package com.ium.repetitaiuvant.DAO;

public class TeacherCourse {
    private Teacher teacher;
    private Course course;

    public TeacherCourse(Teacher teacher, Course course) {
        this.teacher = teacher;
        this.course = course;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "TeacherCourse: " + " Teacher= " + teacher + " ,Course= " + course;
    }
}
