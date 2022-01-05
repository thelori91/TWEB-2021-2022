package com.ium.repetitaiuvant.servlets;

import com.ium.repetitaiuvant.DAO.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Objects;


@WebServlet(name = "ReservationServlet", value = "/reservation-servlet")
public class ReservationServlet extends HttpServlet {

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
        String reservations = request.getParameter("reservations");
        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = (String) session.getAttribute("username");
            String password = (String) session.getAttribute("password");
            PrintWriter out = null;
            if (DAO.logInFunction(username, password)) {

                try {
                    JSONParser parser = new JSONParser();
                    JSONArray reservationsJSON = (JSONArray) parser.parse(reservations);

                    ArrayList<Request> requests = new ArrayList<>();

                    out = response.getWriter();

                    //Cycle used to check if all is correct
                    boolean allRight = true;
                    if (reservationsJSON.size() == 0) {
                        out.println("Error:");
                        out.println("Nothing was sent");
                    }
                    for (Object o : reservationsJSON) {
                        JSONObject obj = (JSONObject) o;
                        Long teacherId = Long.parseLong((String) obj.get("teacherId"));
                        String course = (String) obj.get("course");
                        Day day = Conversions.stringToDay((String) obj.get("day"));
                        Time time = Conversions.stringToTime((String) obj.get("time"));
                        Request addRequest = new Request(teacherId, course, day, time, username);
                        allRight = (checkTeacher(teacherId, course) && checkDayTime(teacherId, day, time) && nothingIsEmpty(teacherId, course, day, time) && checkSimultaneousEvent(requests, addRequest));
                        if (!allRight) {
                            out.println("Error:");
                            out.println("One of the requests failed!\n");
                            return;
                        }
                        requests.add(addRequest);
                    }

                    for (Request req : requests) {
                        DAO.addLesson(req.getTeacherId(), req.getCourse(), username, req.getDay(), req.getTime());
                    }

                    out.println("Success:");
                    out.println("Every lesson has been added!");

                } catch (ConnectException connectException) {
                    out.println("Error:");
                    out.println("Cannot contact server");
                } catch (Exception e) {
                    out.println("Error:");
                    out.println("Something went wrong!");
                }
            }
        }
    }

    //No user can be at two different lessons at once
    private boolean checkSimultaneousEvent(ArrayList<Request> requests, Request request) {
        for (Request req : requests) {
            if (request.getUser().equals(req.user) && req.getDay() == request.getDay() && req.getTime() == request.getTime())
                return false;
        }
        return true;
    }

    // Check Teacher teaches the subject
    private boolean checkTeacher(Long teacherId, String course) {
        ArrayList<TeacherCourse> teacherCourses = null;
        try {
            teacherCourses = DAO.getTeachersCourses();
        } catch (ConnectException connectException) {
            return false;
        }

        for (TeacherCourse teacherCourse : teacherCourses) {
            if (teacherId == teacherCourse.getTeacher().getID() && teacherCourse.getCourse().getName().compareTo(course) == 0)
                return true;
        }
        return false;
    }

    // Check DayTime is available (Cancelled lessons don't count)
    private boolean checkDayTime(Long teacherId, Day day, Time time) {
        ArrayList<Lesson> lessons = DAO.getLessons(null);
        for (Lesson lesson : lessons) {
            if (lesson.getState() != State.CANCELLED && lesson.getTeacher().getID() == teacherId && day == lesson.getDay() && time == lesson.getTime())
                return false;
        }
        return true;

    }

    private boolean nothingIsEmpty(Long teacherId, String course, Day day, Time time) {
        String dayTmp = Conversions.dayToString(day);
        String timeTmp = Conversions.timeToString(time);
        if (Objects.isNull(teacherId)) return false;
        if (Objects.isNull(course) || course.equals("")) return false;
        if (Objects.isNull(dayTmp)) return false;
        if (Objects.isNull(timeTmp)) return false;

        return true;
    }

    public void destroy() {
    }

    class Request {
        final Long teacherId;
        final String course;
        final Day day;
        final Time time;
        final String user;

        public Request(Long teacherId, String course, Day day, Time time, String user) {
            this.teacherId = teacherId;
            this.course = course;
            this.day = day;
            this.time = time;
            this.user = user;
        }

        public Long getTeacherId() {
            return teacherId;
        }

        public String getCourse() {
            return course;
        }

        public String getUser() {
            return user;
        }

        public Day getDay() {
            return day;
        }

        public Time getTime() {
            return time;
        }
    }
}