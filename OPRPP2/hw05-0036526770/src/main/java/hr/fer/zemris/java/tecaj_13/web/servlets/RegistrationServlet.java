package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

@WebServlet("servleti/registration")
public class RegistrationServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/pages/Registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String fn = req.getParameter("firstname");
        String ln = req.getParameter("lastname");
        String email = req.getParameter("email");
        String nick = req.getParameter("nick");
        String pw = req.getParameter("password");

        EntityManager em = JPAEMProvider.getEntityManager();
        BlogUser user;
        try {
            user = em.createNamedQuery("BlogUser.upit.nick", BlogUser.class).setParameter("nick", nick).getSingleResult();
        } catch (Exception e) {
            user = null;
        }
        
        if(user != null){
            req.setAttribute("registrationError", "Nickname already in use!");
            req.getRequestDispatcher("/WEB-INF/pages/Registration.jsp").forward(req, resp);
        }
        else{
            try{
                pw = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256")
                .digest(pw.getBytes(StandardCharsets.UTF_8)));
            } catch (Exception e) {
                
            }
            user = new BlogUser();
            user.setNick(nick);
            user.setFirstName(fn);
            user.setLastName(ln);
            user.setEmail(email);
            user.setPasswordHash(pw);
            DAOProvider.getDAO().persistEntity(user);
            req.getSession().setAttribute("current.user.nick", nick);
            req.getSession().setAttribute("current.user.fn", fn);   
            req.getSession().setAttribute("current.user.ln", ln);
            req.getSession().setAttribute("current.user.id", user.getId());
            resp.sendRedirect("/blog/servleti/main");
        }
    }
    
    

}
