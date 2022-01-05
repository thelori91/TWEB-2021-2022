package com.ium.repetitaiuvant.servlets;

import com.ium.repetitaiuvant.DAO.*;

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


@WebServlet(name = "addTeacherCourseServlet", value = "/addTeacherCourse-servlet")
public class AddTeacherCourseServlet extends HttpServlet {
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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null) return;
        PrintWriter out = null;
        try {
            out = response.getWriter();
            String user = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");
            try {
                if (DAO.logInFunction(user, password) && DAO.getRole(user, password) == Role.ADMIN) {
                    ArrayList<TeacherCourse> teachersCourses = DAO.getTeachersCourses();
                    String teacher = request.getParameter("teacher");
                    String[] teacherInfo = teacher.split(" ");
                    String course = request.getParameter("course");
                    for (TeacherCourse teacherCourse : teachersCourses) {
                        Teacher t = teacherCourse.getTeacher();
                        Course c = teacherCourse.getCourse();
                        if(t.getID() == Long.parseLong(teacherInfo[2]) && c.getName().equals(course)){
                            out.println("Error:");
                            out.println("Cannot add Teacher Course because already exists");
                            return;
                        }
                    }
                    DAO.addTeacherCourse(Long.parseLong(teacherInfo[2]), course);
                    out.println("Success:");
                    out.println("Teacher Course added correctly");
                }
            } catch (ConnectException connectException) {
                out.println("Error:");
                out.println("Cannot contact server");
            } catch (Exception ex) {
                out.println("Error:");
                out.println("Cannot add Teacher Course");
            }
        } catch (IOException e) {
            System.err.println("Error: can't use PrintWriter");
        }
    }

    public void destroy() {
    }
}
