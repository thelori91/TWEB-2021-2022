package DAO;

import java.sql.*;
import java.util.*;

public class DAO {
    private static String url1;
    private static String user;
    private static String password;
    /*private static final String url1 = "jdbc:mysql://localhost:8889/test?useSSL=false";
    private static final String user = "root";
    private static final String password = "root";*/

    public DAO(String url, String usr, String pwd) {
        url1 = url;
        user = usr;
        password = pwd;
        registerDriver();
    }

    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public static String getRole(String usr, String psw) {
        Connection conn1 = null;
        String role = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }
            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT utenti.Ruolo FROM utenti WHERE utenti.Account= '" + usr + "' && utenti.Password= '" + psw + "'");
            rs.next();
            role = rs.getString("Ruolo");
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
        return role;
    }

    public static ArrayList<Corsi> courseList() {
        Connection conn1 = null;
        ArrayList<Corsi> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM corsi");
            while (rs.next()) {
                Corsi c = new Corsi(rs.getInt("IDcorso"), rs.getString("TitoloCorso"));
                out.add(c);
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
        return out;
    }

    public static ArrayList<Docenti> teacherList() {
        Connection conn1 = null;
        ArrayList<Docenti> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM docenti");
            while (rs.next()) {
                Docenti d = new Docenti(rs.getInt("IDdocente"), rs.getString("Nome"), rs.getString("Cognome"));
                out.add(d);
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
        return out;
    }


    public static ArrayList<CorsoDocente> courseTeacherList() {
        Connection conn1 = null;
        ArrayList<CorsoDocente> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM corsodocente");
            while (rs.next()) {
                Statement st2 = conn1.createStatement();
                Statement st3 = conn1.createStatement();
                ResultSet rs2 = st2.executeQuery("SELECT * FROM docenti WHERE docenti.IDdocente=" + rs.getInt("Docenti"));
                rs2.next();
                Docenti t = new Docenti(rs2.getInt("IDdocente"), rs2.getString("Nome"), rs2.getString("Cognome"));
                st2.close();
                ResultSet rs3 = st3.executeQuery("SELECT * FROM corsi WHERE corsi.IDcorso=" + rs.getInt("Corsi"));
                rs3.next();
                Corsi c = new Corsi(rs3.getInt("IDcorso"), rs3.getString("TitoloCorso"));
                st3.close();
                CorsoDocente p = new CorsoDocente(c, t);
                out.add(p);
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
        return out;
    }

    public static ArrayList<Prenotazioni> reservationList() {
        Connection conn1 = null;
        ArrayList<Prenotazioni> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }

            Statement st = conn1.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM prenotazioni");
            while (rs.next()) {
                Statement st2 = conn1.createStatement();
                Statement st3 = conn1.createStatement();
                Statement st4 = conn1.createStatement();

                ResultSet rs2 = st2.executeQuery("SELECT * FROM corsi WHERE corsi.IDcorso=" + rs.getInt("Corso"));
                rs2.next();
                Corsi c = new Corsi(rs2.getInt("IDcorso"), rs2.getString("TitoloCorso"));
                st2.close();

                ResultSet rs3 = st3.executeQuery("SELECT * FROM docenti WHERE docenti.IDdocente=" + rs.getInt("Docente"));
                rs3.next();
                Docenti t = new Docenti(rs3.getInt("IDdocente"), rs3.getString("Nome"), rs3.getString("Cognome"));
                st3.close();

                ResultSet rs4 = st4.executeQuery("SELECT * FROM utenti WHERE utenti.IDutente=" + rs.getInt("Utente"));
                rs4.next();
                Utenti u = new Utenti(rs4.getInt("IDutente"), rs4.getString("Account"), rs4.getString("Password"), rs4.getString("Ruolo"));
                st4.close();

                Prenotazioni p = new Prenotazioni(c, t, u, rs.getString("Slot"));
                out.add(p);
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
        return out;
    }

    public static void addReservation(String IDutente, String IDdocente, String IDcorso, String Slot) {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }
            String sql = "INSERT INTO Prenotazioni(Utente, Docente, Corso, Slot)" + "VALUES(?,?,?,?)";
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setString(1, IDutente);
            st.setString(2, IDdocente);
            st.setString(3, IDcorso);
            st.setString(4, Slot);
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

    public static void rmvReservation(String IDutente, String IDdocente, String IDcorso, String Slot) {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }
            String sql = "DELETE FROM `prenotazioni` WHERE prenotazioni.Utente=" + "?" + "&& prenotazioni.Docente=" + "?" + "&& prenotazioni.Corso=" + "?" + "&& prenotazioni.Slot=" + "?";
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setString(1, IDutente);
            st.setString(2, IDdocente);
            st.setString(3, IDcorso);
            st.setString(4, Slot);
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

    public static void initPrenotazioni() {
        Connection conn1 = null;
        Statement statement = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            statement = conn1.createStatement();
            statement.execute("DELETE FROM `prenotazioni`");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + ": no problem");
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

    public static void addCourseServlet(String TitoloCorso) {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }
            String sql = "INSERT INTO corsi(TitoloCorso)" + "VALUES(?)";
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setString(1, TitoloCorso);
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

    public static void rmvCourseServlet(String TitoloCorso) {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }
            String sql = "DELETE FROM `corsi` WHERE corsi.TitoloCorso=" + "?";
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setString(1, TitoloCorso);
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

    public static void addTeachersServlet(String nome, String cognome) {
        Connection conn1 = null;
        String choice;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            String sql = "INSERT INTO docenti(Nome, Cognome) VALUES(?,?)";
            PreparedStatement st = conn1.prepareStatement(sql);
            st.setString(1, nome);
            st.setString(2, cognome);
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

    public static void rmvTeachersServlet(String nome, String cognome) {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }
            String sql = "DELETE FROM `docenti` WHERE docenti.Nome=" + nome + "&& docenti.Cognome=" + cognome;
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

    public static void addCourseTeacher(String teacher, String course) {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }
            String sql = "INSERT INTO corsodocente(Docenti, Corsi)" + "VALUES(?,?)";
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

    public static void rmvCourseTeacher(String teacher, String course) {
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
                System.out.println("Connected to the database test");
            }
            String sql = "DELETE FROM `corsodocente` WHERE corsodocente.Docenti= " + "?" + "&& corsodocente.Corsi= " + "?";
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
}
