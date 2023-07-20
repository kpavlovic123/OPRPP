package hr.fer.oprpp2.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/glasanje-rezultati")
public class VotingResultsServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Pročitaj rezultate iz /WEB-INF/glasanje-rezultati.txt
            String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
        // Napravi datoteku ako je potrebno; inače je samo pročitaj...
        // ...
        // Pošalji ih JSP-u...
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }
    
}
