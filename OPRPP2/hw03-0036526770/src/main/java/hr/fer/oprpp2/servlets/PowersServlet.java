package hr.fer.oprpp2.servlets;

import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PowersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String filename = "tablica.xls";

        String aParam = req.getParameter("a");
        String bParam = req.getParameter("b");
        String nParam = req.getParameter("n");

        if (aParam == null || bParam == null || nParam == null) {
            resp.getWriter().println("Incorrect parameters");
            return;
        }

        int a = Integer.valueOf(aParam);
        int b = Integer.valueOf(bParam);
        int n = Integer.valueOf(nParam);

        if (a < -100 || a > 100 || b < -100 || b > 100 || n < 1 || n > 5) {
            resp.getWriter().println("Incorrect parameters");
            return;
        }

        if (a > b) {
            int tmp = b;
            b = a;
            a = tmp;
        }

        try {

            
            HSSFWorkbook hwb = new HSSFWorkbook();

            for (int i = 1; i <= n; i++) {
                HSSFSheet sheet = hwb.createSheet(String.valueOf(a + " " + b + " " + i));

                HSSFRow head = sheet.createRow(0);
                head.createCell(0).setCellValue("value");
                head.createCell(1).setCellValue("nth power");

                for (int j = a; j <= b; j++) {
                    HSSFRow row = sheet.createRow(j - a + 1);
                    row.createCell(0).setCellValue(j);
                    row.createCell(1).setCellValue(Math.pow(j, i));
                }
            };

            resp.setHeader("Content-Type", "application/vnd.ms-excel");
            resp.setHeader("Content-Disposition", "attachment; filename="+filename);
 
            hwb.write(resp.getOutputStream());   
            hwb.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}