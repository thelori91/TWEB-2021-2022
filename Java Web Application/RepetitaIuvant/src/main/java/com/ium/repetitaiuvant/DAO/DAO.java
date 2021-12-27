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
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public static boolean existsUser(String username) throws ConnectException {
        Connection conn1 = null;
        String role = null;
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("Connected to the database Tutoring");
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
        String role = null;
        try {
            conn1 = DriverManager.getConnection(DAO.url, DAO.user, DAO.psw);
            if (conn1 == null) {
                System.err.println("Unable to establish a connection!");
                throw new ConnectException();
            }
            System.out.println("Connected to the database Tutoring");
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

    public static Role getRole(String usr, String userPassword) {
        Connection conn1 = null;
        String role = null;
        try {
            conn1 = DriverManager.getConnection(url, user, psw);
            if (conn1 != null) {
                System.out.println("Connected to the database Tutoring");
            }
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

    public static ArrayList<Course> getCourses() {
        Connection conn1 = null;
        ArrayList<Course> courses = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url, user, psw);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

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

    public static ArrayList<Teacher> getTeachers() {
        Connection conn1 = null;
        ArrayList<Teacher> teachers = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url, user, psw);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

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

    public static ArrayList<Teacher> getTeacherByNameSurname(String name, String surname) {
        Connection conn1 = null;
        ArrayList<Teacher> teachers = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url, user, psw);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();
            String sql = "SELECT * FROM Teacher WHERE Teacher.Name= " + name + " && Teacher.Surname=" + surname;
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

    public static ArrayList<TeacherCourse> getTeachersCourses() {
        Connection conn1 = null;
        ArrayList<TeacherCourse> teachersCourses = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url, user, psw);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

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

    public static ArrayList<Lesson> getLessons() {
        Connection conn1 = null;
        ArrayList<Lesson> lessons = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url, user, psw);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Lesson join Teacher T on T.ID = Lesson.Teacher join Course C on C.Name = Lesson.Course join User U on U.Username = Lesson.User");
            while (rs.next()) {

                Teacher teacher = new Teacher(rs.getLong("T.ID"), rs.getString("T.Name"), rs.getString("T.Surname"));
                Course course = new Course(rs.getString("C.Name"));
                User user1 = new User(rs.getString("U.username"), rs.getString("U.Password"), Conversions.stringToRole(rs.getString("U.Role")), rs.getString("U.Name"), rs.getString("U.Surname"));
                Day day = Conversions.stringToDay(rs.getString("Lesson.Day"));
                Time time = Conversions.stringToTime(rs.getString("Lesson.Time"));
                Lesson lesson = new Lesson(rs.getLong("Lesson.ID"), teacher, course, user1, day, time);
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

    public static void addLesson(long teacher, String course, String user, Day day, int ID, Time time) {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url, user, psw);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }
            //TODO fare controllo TeacherCourse prima di aggiungere
            String sql = "INSERT INTO Lesson(Teacher, Course, User, Day, ID, Time)" + "VALUES(?,?,?,?,?,?)";
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setLong(1, teacher);
            st.setString(2, course);
            st.setString(3, user);
            st.setObject(4, day);
            st.setInt(5, ID);
            st.setObject(6, time);
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

    public static void rmvLesson(long teacher, String course, String user, Day day, int ID, Time time) {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url, user, psw);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }
            String sql = "DELETE FROM `Lesson` WHERE Lesson.Teacher=" + "?" + "&& Lesson.Course=" + "?" + "&& Lesson.User=" + "?" + "&& Lesson.Day=" + "?" + "&& Lesson.ID=" + "?" + "&& Lesson.Time=" + "?";
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setLong(1, teacher);
            st.setString(2, course);
            st.setString(3, user);
            st.setObject(4, day);
            st.setInt(5, ID);
            st.setObject(6, time);
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


    public static void initLesson() {
        Connection conn1 = null;
        Statement statement = null;
        try {
            conn1 = DriverManager.getConnection(url, user, psw);
            statement = conn1.createStatement();
            statement.execute("DELETE FROM `Lesson`");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (conn1 != null && statement != null) {
                try {
                    conn1.close();
                    statement.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
    }

    public static void addCourseServlet(String Name) {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url, user, psw);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }
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

    public static void rmvCourseServlet(String name) {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url, user, psw);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }
            String sql = "DELETE FROM `Course` WHERE Course.Name=" + "?";
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setString(1, name);
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

    public static void addTeacherServlet(String name, String surname) {
        Connection conn1 = null;
        String choice;
        try {
            conn1 = DriverManager.getConnection(url, user, psw);
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

    public static void rmvTeacherServlet(String name, String surname) {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url, user, psw);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }
            String sql = "DELETE FROM `Teacher` WHERE Teacher.Name=" + name + "&& Teacher.Surname=" + surname;
            PreparedStatement st = conn1.prepareStatement(sql);
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

    public static void addTeacherCourse(String teacher, String course) {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url, user, psw);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }
            String sql = "INSERT INTO TeacherCourse(Teacher, Course)" + "VALUES(?,?)";
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setString(1, teacher);
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

    public static void rmvTeacherCourse(String teacher, String course) {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url, user, psw);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }
            String sql = "DELETE FROM `TeacherCourse` WHERE TeacherCourse.Teacher= " + "?" + "&& TeacherCourse.Course= " + "?";
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setString(1, teacher);
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

    public static void addStudent(User student) {
        if (student.getRole() == Role.STUDENT) {
            Connection conn1 = null;
            try {
                conn1 = DriverManager.getConnection(url, user, psw);
                if (conn1 != null) {
                    System.out.println("Connected to the database test");
                }
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
}
