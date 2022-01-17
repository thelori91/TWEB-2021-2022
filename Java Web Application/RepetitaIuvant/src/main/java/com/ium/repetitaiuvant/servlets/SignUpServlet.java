package com.ium.repetitaiuvant.servlets;

import com.ium.repetitaiuvant.DAO.DAO;
import com.ium.repetitaiuvant.DAO.Role;
import com.ium.repetitaiuvant.DAO.User;

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


@WebServlet(name = "signUpServlet", value = "/signUp-servlet")
public class SignUpServlet extends HttpServlet {

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
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String username = request.getParameter("uname");
        String password = request.getParameter("password");
        try {
            PrintWriter out = response.getWriter();
            if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
                out.println("Error:");
                out.println("not valid Username or/and Password ");

            } else if (password.length() < 8 || password.length() > 20) {
                out.println("Error:");
                out.println("password must have from 8 to 20 chars, your password length is" + password.length());

            } else if (username.length() == 0 || username.length() > 30) {
                out.println("Error:");
                out.println("improper username length, please insert a new one " + username.length());
            } else if (name.length() == 0 || name.length() > 30) {
                out.println("Error:");
                out.println("improper name length, please insert a new one " + name.length());
            }else if (surname.length() == 0 || surname.length() > 30) {
                out.println("Error:");
                out.println("improper surname length, please insert a new one " + surname.length());
            }else {
                try {
                    if (DAO.existsUser(username)) {
                        out.println("Error:");
                        out.println("username already used");
                    } else {
                        //Since the new student can be added, we do it
                        User student = new User(username, password, Role.STUDENT, name, surname);
                        DAO.addStudent(student);
                        out.println("Success:");
                        out.println(username);
                        out.println("Student");
                        HttpSession s = request.getSession(); // already checked if attribute are null
                        s.setAttribute("username", username);
                        s.setAttribute("password", password);
                    }
                } catch (ConnectException connectException) {
                    out.println("Error:");
                    out.println("Cannot contact server");
                } catch (Exception ex) {
                    out.println("Error:");
                    out.println("unable to contact database");
                }
            }
        } catch (IOException ex) {
            System.err.println("Error: can't use PrintWriter");
        }
    }

    public void destroy() {
    }
}