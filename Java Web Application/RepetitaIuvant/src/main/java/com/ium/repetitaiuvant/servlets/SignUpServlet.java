package com.ium.repetitaiuvant.servlets;

import com.ium.repetitaiuvant.DAO.DAO;
import com.ium.repetitaiuvant.DAO.Role;
import com.ium.repetitaiuvant.DAO.User;

import javax.servlet.RequestDispatcher;
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
import java.net.SocketTimeoutException;
import java.util.Date;

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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("uname");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        response.setContentType("text/html;charset=UTF-8");
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/index.html");
        //Getting a PrintWriter to send the response
        PrintWriter out = response.getWriter();
        //request.setAttribute("servletResponse", username);
        out.write(username);
        requestDispatcher.forward(request, response);

        //Check if Username is already in use, since it's a primary key
        /*if(DAO.existsUser(username))
        {
            out.println("Username already used");
        }
        else
        {
            //Since the new student can be added, we do it
            User student = new User(username, password, Role.STUDENT, name,surname);
            DAO.addStudent(student);
            out.println("Operation Completed");
        }*/
    }

    public void destroy() {
    }
}