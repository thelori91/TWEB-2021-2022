package com.ium.repetitaiuvant.servlets;

import com.ium.repetitaiuvant.DAO.Course;
import com.ium.repetitaiuvant.DAO.DAO;
import com.ium.repetitaiuvant.DAO.TeacherCourse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "GetAllTeacherCoursesServlet", value = "/getAllTeacherCourses-servlet")
public class GetAllTeacherCoursesServlet extends HttpServlet {

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
        ArrayList<TeacherCourse> teacherCourses = DAO.getTeachersCourses();
        JSONArray ret = new JSONArray();
        PrintWriter out;
        try{
            out = response.getWriter();
        } catch (IOException ex) {
            System.err.println("Error: can't use PrintWriter");
            return;
        }

        //Get all different courses from the arraylist teacherCourses
        ArrayList<Course> courses = new ArrayList<>();
        for(TeacherCourse teacherCourse : teacherCourses)
        {
            if(!courses.contains(teacherCourse.getCourse()))
            {
                courses.add(teacherCourse.getCourse());
            }
        }

        //For each of the teachers find all courses taught
        for(Course course : courses)
        {
            JSONObject courseJSON = new JSONObject();
            courseJSON.put("courseName", course.getName());
            JSONArray teachersOfCourse = new JSONArray();
            for(TeacherCourse teacherCourse : teacherCourses)
            {
                if(teacherCourse.getCourse().equals(course))
                {
                    //Teacher teaches this course, so we add it to the courses it teaches
                    JSONObject teacher = new JSONObject();
                    teacher.put("teacherId", teacherCourse.getTeacher().getID());
                    teacher.put("teacherName", teacherCourse.getTeacher().getName());
                    teacher.put("teacherSurname", teacherCourse.getTeacher().getSurname());
                    teachersOfCourse.add(teacher);
                }
            }
            courseJSON.put("teachers", teachersOfCourse);
            ret.add(courseJSON);
        }

        if(out != null) out.print(ret.toJSONString());

    }

    public void destroy() {
    }
}

