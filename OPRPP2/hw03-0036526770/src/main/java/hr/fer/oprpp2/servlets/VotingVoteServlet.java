package hr.fer.oprpp2.servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VotingVoteServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Zabiljezi glas...
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
        String id = req.getParameter("id");
        Map<String,String> map = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = reader.readLine())!=null){
                    String values[] = line.split("\t");
                    map.put(values[0], values[1]);
            }
            if(map.get(id)!=null){
                String votes = map.get(id);
                int v = Integer.valueOf(votes);
                map.put(id, String.valueOf(v));
            }
            else{
                map.put(id,"1");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            map.forEach((k,v)->{
               try {
                    writer.write(k+"\t"+v+"\n");

               } catch (Exception e) {
                // TODO: handle exception
               }
            });
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        

        // Kad je gotovo, pošalji redirect pregledniku I dalje NE generiraj odgovor
        resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
    }
    
}
