package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

    String nick;
    String id;
    BlogUser user;
    String[] l;

    private void handlePath(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        String path = req.getPathInfo();
        if (path == null || path.equals("/"))
            throw new Exception();
        l = path.split("/");
        nick = l[1];
        try {
            user = JPAEMProvider.getEntityManager().createNamedQuery("BlogUser.upit.nick", BlogUser.class)
                    .setParameter("nick", nick).getSingleResult();
        } catch (Exception e) {
            user = null;
        }

        if (user == null) {
            throw new Exception();
        } 
    }
    private void handleNew(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        System.out.println("handleNew");
        req.getRequestDispatcher("/WEB-INF/pages/NewEntry.jsp").forward(req, resp);
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        System.out.println("handleEdit");
        String beId = req.getParameter("beId");
        BlogEntry be = DAOProvider.getDAO().getBlogEntry(Long.parseLong(beId));
        req.setAttribute("blogEntry", be);
        req.getRequestDispatcher("/WEB-INF/pages/EditEntry.jsp").forward(req, resp);
    }

    private void showEntry(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        System.out.println("showEntry");
        BlogEntry be = DAOProvider.getDAO().getBlogEntry(Long.parseLong(id));
        if(be==null)
            resp.sendRedirect("/blog");
        else{
            req.setAttribute("blogEntry", be);
            req.getRequestDispatcher("/WEB-INF/pages/Prikaz.jsp").forward(req, resp);
        }
    }

    private void checkAuthorization(String currentUserNick) throws Exception{
                if (currentUserNick == null || !currentUserNick.equals(nick)) {
                    throw new Exception();
                }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        try {
            handlePath(req,resp);
        } catch (Exception e) {
            resp.sendRedirect("/blog");
            return;
        }
        
        req.setAttribute("BlogUser", user);
        if (l.length == 2) {
            List<BlogEntry> entryList = user.getBlogEntries();
            req.setAttribute("BlogUser.entryList", entryList);
            req.getRequestDispatcher("/WEB-INF/pages/Authors.jsp").forward(req, resp);
        } 
        else {
            id = l[2];
            if (id.equals("new") || id.equals("edit")) {
                String currentUserNick = (String) req.getSession().getAttribute("current.user.nick");
                try {
                    checkAuthorization(currentUserNick);
                } catch (Exception e) {
                    resp.sendRedirect("/blog/");
                    return;
                }
                if(id.equals("new"))
                    handleNew(req,resp);
                else
                    handleEdit(req,resp);
            }
            else
                showEntry(req, resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            handlePath(req, resp);    
        } catch (Exception e) {
            resp.sendRedirect("/blog");
            return;
        }
        try {
            String currentUserNick = (String) req.getSession().getAttribute("current.user.nick");
            checkAuthorization(currentUserNick);
        } catch (Exception e) {
            resp.sendRedirect("/blog/");
            return;
        }
        if(id.equals("new")){
            String text = req.getParameter("text");
            String title = req.getParameter("title");
            BlogEntry be = new BlogEntry();
            be.setText(text);
            be.setTitle(title);
            be.setCreator(user);
            be.setCreatedAt(new Date());
            be.setLastModifiedAt(new Date());
            DAOProvider.getDAO().persistEntity(be);
            user.getBlogEntries().add(be);
            DAOProvider.getDAO().persistEntity(user);
        }
        if(id.equals("edit")){
            String text = req.getParameter("text");
            String title = req.getParameter("title");
            String beId = req.getParameter("beId");
            BlogEntry be = DAOProvider.getDAO().getBlogEntry(Long.parseLong(beId));
            be.setText(text);
            be.setTitle(title);
            be.setLastModifiedAt(new Date());
            DAOProvider.getDAO().persistEntity(be);
        }
        resp.sendRedirect("/blog/servleti/author/"+nick);
    }

    
}
