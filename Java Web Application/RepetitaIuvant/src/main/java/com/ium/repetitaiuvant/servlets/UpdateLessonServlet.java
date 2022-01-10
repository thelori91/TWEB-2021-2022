package com.ium.repetitaiuvant.servlets;

import com.ium.repetitaiuvant.DAO.Conversions;
import com.ium.repetitaiuvant.DAO.DAO;
import com.ium.repetitaiuvant.DAO.Lesson;
import com.ium.repetitaiuvant.DAO.Role;

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


@WebServlet(name = "updateLessonServlet", value = "/updateLesson-servlet")
public class UpdateLessonServlet extends HttpServlet {

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
            Role userRole = DAO.getRole((String) session.getAttribute("username"), (String) session.getAttribute("password"));
            out = response.getWriter();
            String lessonId = request.getParameter("lessonId");
            Lesson lesson = DAO.getLessonsById(Long.parseLong(lessonId));
            long teacherId = lesson.getTeacher().getID();
            String course = lesson.getCourse().getName();
            String day = Conversions.dayToString(lesson.getDay());
            String time = Conversions.timeToString(lesson.getTime());
            String user;
            if (userRole == Role.ADMIN) {
                user = lesson.getUser().getUsername();
            } else {
                user = (String) session.getAttribute("username");
            }
            String password = (String) session.getAttribute("password");
            String nextState = request.getParameter("nextState");
            try {
                if (DAO.logInFunction((String) session.getAttribute("username"), password)) {
                    if (nextState.equals("Cancelled")) {
                        DAO.updateLesson(Long.parseLong(lessonId), nextState);
                        out.println("Success:");
                        out.println("Lesson is now Updated");
                    } else if (DAO.checkBeforeUpdateLesson(teacherId, course, user, day, Long.parseLong(lessonId), time) == 0) {
                        DAO.updateLesson(Long.parseLong(lessonId), nextState);
                        out.println("Success:");
                        out.println("Lesson is now Updated");
                    } else {
                        out.println("Error:");
                        out.println("Lesson already exists");
                    }
                }
            } catch (ConnectException connectException) {
                out.println("Error:");
                out.println("Cannot contact server");
            } catch (Exception ex) {
                out.println("Error:");
                out.println("Cannot update Lesson");
            }
        } catch (IOException e) {
            System.err.println("Error: can't use PrintWriter");
        }
    }

    public void destroy() {
    }
}