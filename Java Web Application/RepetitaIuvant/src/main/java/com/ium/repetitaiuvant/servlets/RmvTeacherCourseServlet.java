package com.ium.repetitaiuvant.servlets;

import com.ium.repetitaiuvant.DAO.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.util.ArrayList;


@WebServlet(name = "rmvTeacherCourseServlet ", value = "/rmvTeacherCourse-servlet")
public class RmvTeacherCourseServlet extends HttpServlet {
    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
        ServletContext ctx = conf.getServletContext();
        String url = ctx.getInitParameter("DB-URL");
        String user = ctx.getInitParameter("user");
        String pwd = ctx.getInitParameter("psw");
        DAO.initDAO(url, user, pwd);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/plain;charset=UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null) return;
        PrintWriter out = null;
        try {
            out = response.getWriter();
            String user = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");
            try {
                if (DAO.logInFunction(user, password) && DAO.getRole(user, password) == Role.ADMIN) {
                    String teacherName = request.getParameter("teacherName");
                    String teacherSurname = request.getParameter("teacherSurname");
                    String teacherId = request.getParameter("teacherId");
                    String courseName = request.getParameter("courseName");
                    ArrayList<Teacher> teachers = DAO.getTeacherByNameSurname(teacherName, teacherSurname);
                    ArrayList<Course> courses = DAO.getCourses();
                    if (teachers.size() == 0) {
                        out.println("Error:");
                        out.println("Cannot remove Teacher Course because Teacher doesn't exists");
                        return;
                    }
                    if (courses.size() == 0) {
                        out.println("Error:");
                        out.println("Cannot remove Teacher Course because Course doesn't exists");
                        return;
                    }
                    boolean teacherFound = false;
                    boolean courseFound = false;
                    boolean teacherCourseFound = false;
                    for (Teacher teacher : teachers) {
                        if (teacher.getName().equals(teacherName) && teacher.getSurname().equals(teacherSurname) && teacher.getID() == Long.parseLong(teacherId)) {
                            teacherFound = true;
                            break;
                        }
                    }
                    for (Course course : courses) {
                        if (course.getName().equals(courseName)) {
                            courseFound = true;
                            break;
                        }
                    }
                    ArrayList<TeacherCourse> teachersCourses = DAO.getTeachersCourses();
                    for (TeacherCourse tc : teachersCourses) {
                        Teacher t = tc.getTeacher();
                        Course c = tc.getCourse();
                        if (t.getName().equals(teacherName) && t.getSurname().equals(teacherSurname) && t.getID() == Long.parseLong(teacherId) && c.getName().equals(courseName)) {
                            teacherCourseFound = true;
                            break;
                        }
                    }
                    if (teacherFound && courseFound && teacherCourseFound) {
                        DAO.rmvTeacherCourse(Long.parseLong(teacherId), courseName);
                        out.println("Success:");
                        out.println("Teacher Course removed correctly");
                    } else {
                        out.println("Error:");
                        out.println("Cannot remove Teacher Course because the choice doesn't exists");
                    }
                }
            } catch (ConnectException connectException) {
                out.println("Error:");
                out.println("Cannot contact server");
            } catch (Exception ex) {
                out.println("Error:");
                out.println("Cannot remove Teacher Course");
            }
        } catch (IOException e) {
            System.err.println("Error: can't use PrintWriter");
        }
    }

    public void destroy() {
    }
}