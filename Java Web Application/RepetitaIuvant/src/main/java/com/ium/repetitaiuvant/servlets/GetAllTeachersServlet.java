package com.ium.repetitaiuvant.servlets;

import com.ium.repetitaiuvant.DAO.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.util.ArrayList;


@WebServlet(name = "GetAllTeachersServlet", value = "/getAllTeachers-servlet")
public class GetAllTeachersServlet extends HttpServlet {

    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
        ServletContext ctx = conf.getServletContext();
        String url = ctx.getInitParameter("DB-URL");
        String user = ctx.getInitParameter("user");
        String pwd = ctx.getInitParameter("psw");
        DAO.initDAO(url, user, pwd);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            try {
                ArrayList<Teacher> teachers = DAO.getTeachers();
                JSONArray ret = new JSONArray();
                for (Teacher teacher : teachers) {
                    JSONObject teacherJSON = new JSONObject();
                    teacherJSON.put("teacherName", teacher.getName());
                    teacherJSON.put("teacherSurname", teacher.getSurname());
                    teacherJSON.put("teacherId", teacher.getID());
                    ret.add(teacherJSON);
                }
                out.print(ret.toJSONString());
            } catch (ConnectException connectException) {
                out.println("Error:");
                out.println("Cannot contact server");
            } catch (Exception ex) {
                out.println("Error:");
                out.println("Unable to perform the operation");
            }
        } catch (IOException ex) {
            System.err.println("Error: can't use PrintWriter");
        }
    }

    public void destroy() {
    }
}

