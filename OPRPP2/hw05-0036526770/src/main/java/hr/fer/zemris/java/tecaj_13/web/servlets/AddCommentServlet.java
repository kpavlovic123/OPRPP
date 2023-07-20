package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

@WebServlet("/servleti/comment")
public class AddCommentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("current.user.nick")==null){
            resp.sendRedirect("/blog");
        }
        else{
            var d = DAOProvider.getDAO();
            String beId = req.getParameter("beId");
            String text = req.getParameter("text");
            BlogEntry be = d.getEntity(Long.parseLong(beId), BlogEntry.class);
            BlogComment comment = new BlogComment();
            BlogUser u = d.getEntity((Long)req.getSession().
            getAttribute("current.user.id"), BlogUser.class);
            comment.setBlogEntry(be);
            comment.setMessage(text);
            comment.setPostedOn(new Date());
            comment.setUsersEMail(u.getEmail());
            be.getComments().add(comment);
            d.persistEntity(comment);
            req.setAttribute("blogEntry", be);
            req.setAttribute("BlogUser", be.getCreator());
            req.getRequestDispatcher("/WEB-INF/pages/Prikaz.jsp").forward(req, resp);
        }
    }
    
}
