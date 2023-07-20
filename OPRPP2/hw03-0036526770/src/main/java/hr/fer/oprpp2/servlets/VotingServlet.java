package hr.fer.oprpp2.servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/glasanje")
public class VotingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
        Map<Integer,String> map = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName));) {
            String line;
            while((line = reader.readLine())!=null){
                String[] values = line.split("\t");
                int id = Integer.valueOf(values[0]);
                String name = values[1];
                map.put(id, name);
            }
        } catch (Exception e) {
            System.err.println(e.getCause()+" "+e.getMessage());
        }
        req.setAttribute("map", map);
        // Pošalji ih JSP-u...
        req.getRequestDispatcher("/WEB-INF/jsp/votingIndex.jsp").forward(req, resp);
    }

}
