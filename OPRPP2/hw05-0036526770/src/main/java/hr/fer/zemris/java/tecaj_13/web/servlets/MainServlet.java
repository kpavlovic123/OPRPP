package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {
    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BlogUser> userList = JPAEMProvider.getEntityManager().createQuery("Select u from BlogUser as u", BlogUser.class)
                .getResultList();
        req.setAttribute("userList", userList);
        req.getRequestDispatcher("/WEB-INF/pages/UserList.jsp").forward(req, resp);
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nick = req.getParameter("nick");
        String pw = req.getParameter("password");
        EntityManager em = JPAEMProvider.getEntityManager();
        BlogUser user = null;
        boolean error = true;
        try {
            user = (BlogUser) em.createNamedQuery("BlogUser.upit.nick", BlogUser.class)
                    .setParameter("nick", nick).getSingleResult();
        } catch (Exception e) {
        }

        if (user != null) {
            try {
                error = !user.getPasswordHash()
                        .equals(Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256")
                                .digest(pw.getBytes(StandardCharsets.UTF_8))));

            } catch (Exception e) {

            }
        }

        if (error) {
            req.setAttribute("loginError", "Login failed");
            req.getRequestDispatcher("/WEB-INF/pages/UserList.jsp").forward(req, resp);
        } else {
            var session = req.getSession();
            session.setAttribute("current.user.nick", user.getNick());
            session.setAttribute("current.user.fn", user.getFirstName());
            session.setAttribute("current.user.ln", user.getLastName());
            session.setAttribute("current.user.id", user.getId());
            resp.sendRedirect("/blog/servleti/main");
        }

    }
}
