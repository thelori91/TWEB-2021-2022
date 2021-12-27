package com.ium.repetitaiuvant.servlets;

import com.ium.repetitaiuvant.DAO.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet(name = "signInServlet", value = "/signIn-servlet")
public class SignInServlet extends HttpServlet {

    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
        ServletContext ctx = conf.getServletContext();
        String url = ctx.getInitParameter("DB-URL");
        String user = ctx.getInitParameter("user");
        String pwd = ctx.getInitParameter("psw");
        DAO.initDAO(url, user, pwd);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("uname");
        String password = request.getParameter("password");
        HttpSession s = request.getSession();
        String jsessionID = s.getId();
        if (username != null && password != null) {
            s.setAttribute("username", username);
            s.setAttribute("password", password);
        }
        String url = response.encodeURL("signIn-servlet");
        PrintWriter out = response.getWriter();
        if (!DAO.existsUser(username)) {
            out.println("Error:");
            out.println("user doesn't exists");
        } else if (!DAO.logInFunction(username, password)) {
            out.println("Error:");
            out.println("password is not correct!");
        } else {
            out.println("Success:");
            out.println(username);
            Role role = DAO.getRole(username, password);
            out.println(Conversions.roleToString(role));
        }
    }

    public void destroy() {
    }
}