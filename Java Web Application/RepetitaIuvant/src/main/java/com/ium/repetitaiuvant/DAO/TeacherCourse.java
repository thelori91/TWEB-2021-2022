package com.ium.repetitaiuvant.DAO;

public class TeacherCourse {
    private Teacher teacher;
    private Course course;

    public TeacherCourse(Teacher teacher, Course course) {
        this.teacher = teacher;
        this.course = course;
    }

    public Teacher getTeachers() {
        return teacher;
    }

    public Course getCourses() {
        return course;
    }

    @Override
    public String toString() {
        return "TeacherCourse: " + " Teacher= " + teacher + " ,Course= " + course;
    }
}
