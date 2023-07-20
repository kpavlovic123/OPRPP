package hr.fer.oprpp2.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TrigonometricServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int a = req.getParameter("a") == null? 0 : Integer.valueOf(req.getParameter("a"));
        int b = req.getParameter("b") == null? 0 : Integer.valueOf(req.getParameter("b"));
        if(a>b){
            int tmp = a;
            a = b;
            b = tmp;
        }
        else if(b > a + 720){
            b = a + 720;
        }
        double sin[] = new double[b-a];
        double cos[] = new double[b-a];

        for (int i = a;i<b;i++){
            double angle = (((double)i)%360)/360*(2*Math.PI); 
            sin[i-a] = Math.sin(angle);
            cos[i-a] = Math.cos(angle);
        }   
        req.setAttribute("sin", sin);
        req.setAttribute("cos", cos);
        req.setAttribute("a", a);
        req.setAttribute("b", b);
        req.getRequestDispatcher("/WEB-INF/jsp/trigonometric.jsp").forward(req, resp);
    }
}
    

