package com.ium.repetitaiuvant.servlets;

import com.ium.repetitaiuvant.DAO.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.*;

@WebServlet(name = "signInServlet", value = "/signIn-servlet")
public class SignInServlet extends HttpServlet {

    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
        ServletContext ctx = conf.getServletContext();
        String url = ctx.getInitParameter("DB-URL");
        String user = ctx.getInitParameter("user");
        String pwd = ctx.getInitParameter("psw");
        DAO.initDAO(url, user, pwd);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("uname");
        String password = request.getParameter("password");
        HttpSession s = request.getSession();
        String jsessionID = s.getId();
        if (username != null && password != null) {
            s.setAttribute("username", username);
            s.setAttribute("password", password);
        }
        String url = response.encodeURL("signIn-servlet");
        PrintWriter out = response.getWriter();
        System.out.println(username);
        System.out.println(password);
        if (!DAO.existsUser(username)) {
            out.println(" Error User doesn't exists");
        } else if (!DAO.logInFunction(username, password)) {
            out.println(" Error Password is not correct! ");
        } else {
            out.println(" Log in Successfully! ");
            out.println(" Nice to see you again " + username);
        }

        String azione = request.getParameter("action");
        if (azione != null && azione.equals("logOut")) {
            s.invalidate();
            out.println(" Log Out Successfully");
        } else {
            out.print("Stato della sessione: ");
            if (s.isNew())
                out.println(" nuova sessione ");
            else out.println(" vecchia sessione ");
            out.println("ID di sessione: " + s.getId());
            out.println(" Invalida <a href=\"" + url + "?action=invalida\"> la sessione</a></p>");
            out.println("Ricarica <a href=\"" + url + "\"> la pagina</a></p>");
        }

        /*
            String azione = request.getParameter("action");
            out.println("<p>URL: " + url + "</p>");
            if (azione != null && azione.equals("logout")) {
                s.invalidate();
                out.println("<p>Sessione invalidata!</p>");
                out.println("<p>Ricarica <a href=\"" + url + "\"> la pagina</a></p>");
            } else {
                out.print("<p>Stato della sessione: ");
                if (s.isNew())
                    out.println(" nuova sessione</p>");
                else out.println(" vecchia sessione</p>");
                out.println("<p>ID di sessione: " + s.getId() + "</p>");
                out.println("<p>Data di creazione: " + new Date(s.getCreationTime()) + "</p>");
                out.println("<p>Max inactive time interval (in secondi): "
                        + s.getMaxInactiveInterval() + "</p>");
                out.println("<p>Log out <a href=\"" + url + "?action=logout\"> la sessione</a></p>");
                out.println("<p>Ricarica <a href=\"" + url + "\"> la pagina</a></p>");
            }
            out.println("</body>");
            out.println("</html>");
        } catch (Exception ex) {
            out.println(" Unable to contact server ");
        }*/
    }

    public void destroy() {
    }
}