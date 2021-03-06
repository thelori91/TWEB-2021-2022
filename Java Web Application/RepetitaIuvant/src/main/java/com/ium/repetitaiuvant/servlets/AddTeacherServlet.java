package com.ium.repetitaiuvant.servlets;

import com.ium.repetitaiuvant.DAO.DAO;
import com.ium.repetitaiuvant.DAO.Role;
import com.ium.repetitaiuvant.DAO.StringFormatter;

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


@WebServlet(name = "addTeacherServlet", value = "/addTeacher-servlet")
public class AddTeacherServlet extends HttpServlet {
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
                    String name = request.getParameter("teacherName");
                    String surname = request.getParameter("teacherSurname");
                    name = name.trim();
                    surname = surname.trim();
                    if (name.length() == 0 || surname.length() == 0) {
                        out.println("Error:");
                        out.println("Invalid input");
                        return;
                    }
                    /*add in database with our default rule*/
                    name = StringFormatter.capitalizeWord(name);
                    surname = StringFormatter.capitalizeWord(surname);
                    DAO.addTeacher(name, surname);
                    out.println("Success:");
                    out.println("Teacher added correctly");
                }
            } catch (ConnectException connectException) {
                out.println("Error:");
                out.println("Cannot contact server");
            } catch (Exception ex) {
                out.println("Error:");
                out.println("Cannot add Teacher");
            }
        } catch (IOException e) {
            System.err.println("Error: can't use PrintWriter");
        }
    }

    public void destroy() {
    }
}
