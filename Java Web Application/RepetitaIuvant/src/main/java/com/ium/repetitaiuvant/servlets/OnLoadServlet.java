package com.ium.repetitaiuvant.servlets;

import com.ium.repetitaiuvant.DAO.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


@WebServlet(name = "OnLoadServlet", value = "/onLoadServlet-servlet")
public class OnLoadServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        if (session == null) return;
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        //TODO creare JSON con tutte info sulle lezioni e login

    }

    public void destroy() {
    }
}