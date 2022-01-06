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


@WebServlet(name = "OnLoadServlet", value = "/onLoad-servlet")
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
        response.setContentType("text/plain;charset=UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null) return;
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        try {
            PrintWriter out = response.getWriter();
            try {
                //Check if user password are correct
                if (DAO.logInFunction(username, password)) {
                    JSONArray outArray = new JSONArray();
                    //Get all lessons from db for the given user
                    ArrayList<Lesson> lessons = DAO.getLessons(username);
                    JSONObject stater = new JSONObject();
                    stater.put("username", username);
                    stater.put("role", Conversions.roleToString(DAO.getRole(username, password)));
                    outArray.add(stater);
                    for (Lesson lesson : lessons) {
                        JSONObject lessonJSON = new JSONObject();
                        lessonJSON.put("username", lesson.getUser().getUsername());
                        lessonJSON.put("role", Conversions.roleToString(lesson.getUser().getRole()));
                        lessonJSON.put("course", lesson.getCourse().getName());
                        lessonJSON.put("teacherName", lesson.getTeacher().getName());
                        lessonJSON.put("teacherSurname", lesson.getTeacher().getSurname());
                        lessonJSON.put("teacherId", lesson.getTeacher().getID());
                        lessonJSON.put("day", Conversions.dayToString(lesson.getDay()));
                        lessonJSON.put("time", Conversions.timeToString(lesson.getTime()));
                        lessonJSON.put("state", Conversions.stateToString(lesson.getState()));
                        outArray.add(lessonJSON);
                    }
                    out.print(outArray.toJSONString());
                }
            } catch (ConnectException connectException) {
                out.println("Error:");
                out.println("Cannot contact server");
            } catch (Exception ex) {
                out.println("Error:");
                out.println("Unable to perform the operation");
            }
        } catch (IOException ioException) {
            System.out.println("Error: can't use PrintWriter");
        }


    }

    public void destroy() {
    }
}