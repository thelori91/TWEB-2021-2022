package com.ium.repetitaiuvant.servlets;

import com.ium.repetitaiuvant.DAO.*;
import org.json.simple.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.ArrayList;


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

        //Check if user password are correct
        if(DAO.logInFunction(username, password))
        {
            JSONObject out = new JSONObject();
            //Get all lessons from db for the given user
            ArrayList<Lesson> lessons = DAO.getLessons();
        }


    }

    public void destroy() {
    }
}