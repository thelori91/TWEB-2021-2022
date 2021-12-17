package com.ium.repetitaiuvant.servlets;

import com.ium.repetitaiuvant.DAO.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

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
        /* SESSIONE UTENTE */
        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        HttpSession s = request.getSession();
        if (username != null)
            s.setAttribute("username", username);
        String url = response.encodeURL("hello-servlet");
        PrintWriter out = response.getWriter();
        try {
            out.println("<p>Sei collegato come: " + s.getAttribute("userName") + "</p>");
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
        } finally {
            out.close();
        }
    }

    public void destroy() {
    }
}