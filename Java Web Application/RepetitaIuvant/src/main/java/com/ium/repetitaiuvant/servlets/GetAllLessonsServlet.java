package com.ium.repetitaiuvant.servlets;

import com.ium.repetitaiuvant.DAO.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


@WebServlet(name = "GetAllLessonsServlet", value = "/getAllLessons-servlet")
public class GetAllLessonsServlet extends HttpServlet {

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
        try {
            PrintWriter out = response.getWriter();
            try {
                ArrayList<Lesson> lessons = DAO.getLessons(null);
                ArrayList<Teacher> teachers = new ArrayList<>();
                JSONArray ret = new JSONArray();
                for (Lesson lesson : lessons) {
                    if (!teachers.contains(lesson.getTeacher())) {
                        teachers.add(lesson.getTeacher());
                    }
                }

                for (Teacher teacher : teachers) {
                    JSONObject teacherJSON = new JSONObject();
                    teacherJSON.put("teacherName", teacher.getName());
                    teacherJSON.put("teacherSurname", teacher.getSurname());
                    teacherJSON.put("teacherId", teacher.getID());
                    JSONArray lessonsJSON = new JSONArray();
                    for (Lesson lesson : lessons) {
                        JSONObject lessonJSON = new JSONObject();
                        if (lesson.getTeacher().equals(teacher)) {
                            lessonJSON.put("lessonWithUser", lesson.getUser().getUsername());
                            lessonJSON.put("lessonId", lesson.getId());
                            lessonJSON.put("lessonCourse", lesson.getCourse().getName());
                            lessonJSON.put("lessonDay", Conversions.dayToString(lesson.getDay()));
                            lessonJSON.put("lessonTime", Conversions.timeToString(lesson.getTime()));
                            lessonJSON.put("lessonState", Conversions.stateToString(lesson.getState()));
                            lessonsJSON.add(lessonJSON);
                        }
                        teacherJSON.put("lessons", lessonsJSON);
                    }
                    ret.add(teacherJSON);
                }
                out.print(ret.toJSONString());
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

