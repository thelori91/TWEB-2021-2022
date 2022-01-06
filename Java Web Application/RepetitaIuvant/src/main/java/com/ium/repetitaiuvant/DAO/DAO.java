package com.ium.repetitaiuvant.DAO;

import java.net.ConnectException;
import java.sql.*;
import java.util.*;

public class DAO {
    private static String url;
    private static String user;
    private static String psw;

    public static void initDAO(String url, String user, String psw) {
        DAO.url = url;
        DAO.user = user;
        DAO.psw = psw;
        registerDriver();
    }

    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /* SELECT */

    public static boolean existsUser(String username) throws ConnectException {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("existsUser: Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("existsUser: Connected to the database Tutoring");
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT Username FROM User WHERE User.Username = '" + username + "'");
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        throw new ConnectException();
    }

    public static boolean logInFunction(String username, String password) throws ConnectException {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("logInFunction: Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("logInFunction: Connected to the database Tutoring");
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT Username, Password FROM User WHERE User.Username = '" + username + "' && User.Password = '" + password + "'");
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        throw new ConnectException();
    }

    public static ArrayList<Teacher> getTeachers() throws ConnectException {
        Connection conn1 = null;
        ArrayList<Teacher> teachers = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("getTeachers: Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("getTeachers: Connected to the database Tutoring");
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Teacher");
            while (rs.next()) {
                Teacher teacher = new Teacher(rs.getLong("ID"), rs.getString("Name"), rs.getString("Surname"));
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        return teachers;
    }

    public static ArrayList<Teacher> getTeacherByNameSurname(String name, String surname) throws ConnectException {
        Connection conn1 = null;
        ArrayList<Teacher> teachers = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("getTeacherByNameSurname: Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("getTeacherByNameSurname: Connected to the database Tutoring");
            Statement st = conn1.createStatement();
            String sql = "SELECT * FROM Teacher WHERE Teacher.Name= '" + name + "' && Teacher.Surname= '" + surname + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Teacher teacher = new Teacher(rs.getLong("ID"), rs.getString("Name"), rs.getString("Surname"));
                teachers.add(teacher);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        return teachers;
    }

    public static ArrayList<Course> getCourses() throws ConnectException {
        Connection conn1 = null;
        ArrayList<Course> courses = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("getCourses: Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("getCourses: Connected to the database Tutoring");
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Course");
            while (rs.next()) {
                Course course = new Course(rs.getString("Name"));
                courses.add(course);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        return courses;
    }

    public static ArrayList<TeacherCourse> getTeachersCourses() throws ConnectException {
        Connection conn1 = null;
        ArrayList<TeacherCourse> teachersCourses = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("getTeachersCourses: Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("getTeachersCourses: Connected to the database Tutoring");
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM TeacherCourse join Teacher T on TeacherCourse.Teacher = T.ID join Course C on TeacherCourse.Course = C.Name");
            while (rs.next()) {
                Teacher teacher = new Teacher(rs.getInt("T.ID"), rs.getString("T.Name"), rs.getString("T.Surname"));
                Course course = new Course(rs.getString("C.Name"));
                TeacherCourse teacherCourse = new TeacherCourse(teacher, course);
                teachersCourses.add(teacherCourse);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        return teachersCourses;
    }

    public static ArrayList<Lesson> getLessons(String username) throws ConnectException {
        Connection conn1 = null;
        ArrayList<Lesson> lessons = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("getLessons: Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("getLessons: Connected to the database Tutoring");
            Statement st = conn1.createStatement();
            String sql = "SELECT * FROM Lesson join Teacher T on T.ID = Lesson.Teacher join Course C on C.Name = Lesson.Course join User U on U.Username = Lesson.User";
            if (username != null) {
                sql += " WHERE U.Username = '" + username + "';";
            }
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Teacher teacher = new Teacher(rs.getLong("T.ID"), rs.getString("T.Name"), rs.getString("T.Surname"));
                Course course = new Course(rs.getString("C.Name"));
                User user1 = new User(rs.getString("U.username"), rs.getString("U.Password"), Conversions.stringToRole(rs.getString("U.Role")), rs.getString("U.Name"), rs.getString("U.Surname"));
                Day day = Conversions.stringToDay(rs.getString("Lesson.Day"));
                Time time = Conversions.stringToTime(rs.getString("Lesson.Time"));
                State state = Conversions.stringToState(rs.getString("Lesson.state"));
                Lesson lesson = new Lesson(rs.getLong("Lesson.ID"), teacher, course, user1, day, time, state);
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        return lessons;
    }

    public static Role getRole(String usr, String userPassword) throws ConnectException {
        Connection conn1 = null;
        String role = null;
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("getRole: Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("getRole: Connected to the database Tutoring");
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT Role FROM User WHERE User.Username = '" + usr + "' && User.Password =  '" + userPassword + "'");
            rs.next();
            role = rs.getString("Role");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        return Conversions.stringToRole(role);
    }

    /* INSERT */

    public static void addStudent(User student) throws ConnectException {
        if (student.getRole() == Role.STUDENT) {
            Connection conn1 = null;
            try {
                conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
                if (conn1 == null) {
                    System.err.println("addStudent: Unable to establish a connection!");
                    throw new ConnectException();
                }
                System.out.println("addStudent: Connected to the database Tutoring");
                String sql = "INSERT into User(Username, Password, Role, Name, Surname) values ('" + student.getUsername() + "', '" + student.getPassword() + "', 'Student', '" + student.getName() + "' , '" + student.getSurname() + "')";
                Statement update = (Statement) conn1.createStatement();
                update.executeUpdate(sql);
                System.out.println("Student added correctly!");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                if (conn1 != null) {
                    try {
                        conn1.close();
                    } catch (SQLException e2) {
                        System.out.println(e2.getMessage());
                    }
                }
            }
        }
    }

    public static void addLesson(long teacher, String course, String user, Day day, Time time) throws ConnectException {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("addLesson: Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("addLesson: Connected to the database Tutoring");
            String sql = "INSERT INTO Lesson(Teacher, Course, User, Day, Time)" + "VALUES(?,?,?,?,?)";
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setLong(1, teacher);
            st.setString(2, course);
            st.setString(3, user);
            st.setString(4, Conversions.dayToString(day));
            st.setString(5, Conversions.timeToString(time));
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
    }

    public static void addTeacher(String name, String surname) throws ConnectException {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("addTeacher: Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("addTeacher: Connected to the database Tutoring");
            String sql = "INSERT INTO Teacher(Name, Surname) VALUES(?,?)";
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, surname);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
    }

    public static void addCourse(String Name) throws ConnectException {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("addCourse: Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("addCourse: Connected to the database Tutoring");
            String sql = "INSERT INTO Course(Name)" + "VALUES(?)";
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setString(1, Name);
            st.executeUpdate();
            st.close();
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
    }

    public static void addTeacherCourse(long teacher, String course) throws ConnectException {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("addTeacherCourse: Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("addTeacherCourse: Connected to the database Tutoring");
            String sql = "INSERT INTO TeacherCourse(Teacher, Course)" + "VALUES(?,?)";
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setLong(1, teacher);
            st.setString(2, course);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
    }

    /* DELETE */

    public static void rmvTeacher(long teacherId) throws ConnectException {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("rmvTeacher: Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("rmvTeacher: Connected to the database Tutoring");
            String sql = "DELETE FROM `TeacherCourse` WHERE TeacherCourse.Teacher=" + "?";
            PreparedStatement st = conn1.prepareStatement(sql);
            System.out.println(teacherId);
            st.setLong(1, teacherId);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
    }

    public static void rmvCourse(String courseName) throws ConnectException {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("rmvCourse: Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("rmvCourse: Connected to the database Tutoring");
            String sql = "DELETE FROM `TeacherCourse` WHERE TeacherCourse.Course=" + "?";
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setString(1, courseName);
            st.executeUpdate();
            st.close();
        } catch (
                SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
    }

    public static void rmvTeacherCourse(long teacherId, String courseName) throws ConnectException {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("rmvTeacherCourse: Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("rmvTeacherCourse: Connected to the database Tutoring");
            String sql = "DELETE FROM `TeacherCourse` WHERE TeacherCourse.Teacher= " + "?" + "&& TeacherCourse.Course= " + "?";
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setLong(1, teacherId);
            st.setString(2, courseName);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
    }

    /* UPDATE */

    public static int checkBeforeUpdateLesson(long teacherId, String course, String user, String day, long lessonId, String time) throws ConnectException {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("checkBeforeUpdateLesson: Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("checkBeforeUpdateLesson: Connected to the database Tutoring");
            String tId = " Lesson.Teacher= " + "?";
            String c = " && Lesson.Course= " + "?";
            String usr = " && Lesson.User= " + "?";
            String d = " && Lesson.Day= " + "?";
            String lId = " && Lesson.ID <> " + "?";
            String t = " && Lesson.Time= " + "?";
            String s = " && Lesson.State <> 'Cancelled' ";
            String sql = "SELECT COUNT(Lesson.ID) AS NumberOfLessonId  FROM `Lesson` WHERE " + tId + c + usr + d + lId + t + s;
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setLong(1, teacherId);
            st.setString(2, course);
            st.setString(3, user);
            st.setString(4, day);
            st.setLong(5, lessonId);
            st.setString(6, time);
            ResultSet rs = st.executeQuery();
            rs.next();
            int numberSameLesson = rs.getInt("NumberOfLessonId");
            st.close();
            return numberSameLesson;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        return -1;
    }

    public static void updateLesson(long teacherId, long lessonId, String course, String user, String day, String time, String state, String nextState) throws ConnectException {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("updateLesson: Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("updateLesson: Connected to the database Tutoring");
            String newState = " State= " + "?";
            String tId = " WHERE Lesson.Teacher= " + "?";
            String lId = " && Lesson.ID= " + "?";
            String c = " && Lesson.Course= " + "?";
            String usr = " && Lesson.User= " + "?";
            String d = " && Lesson.Day= " + "?";
            String t = " && Lesson.Time= " + "?";
            String s = " && Lesson.State= " + "?";
            String sql = "UPDATE Lesson SET" + newState + tId + c + usr + d + lId + t + s;
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setString(1, nextState);
            st.setLong(2, teacherId);
            st.setString(3, course);
            st.setString(4, user);
            st.setString(5, day);
            st.setLong(6, lessonId);
            st.setString(7, time);
            st.setString(8, state);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
    }
}
