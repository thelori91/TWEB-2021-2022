package com.ium.repetitaiuvant.servlets;

import com.ium.repetitaiuvant.DAO.DAO;

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
            /*TODO controllo role user e se ADMIN CAMBIO PARAMETRO*/
            out = response.getWriter();
            String teacherId = request.getParameter("teacherId");
            String course = request.getParameter("course");
            String user = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");
            String day = request.getParameter("day");
            String time = request.getParameter("time");
            String state = request.getParameter("state");
            try {
                if (DAO.logInFunction(user, password)) {
                    DAO.updateLesson(Long.parseLong(teacherId), course, user, day, time, state);
                    out.println("Success:");
                    out.println("Lesson is now Updated");
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