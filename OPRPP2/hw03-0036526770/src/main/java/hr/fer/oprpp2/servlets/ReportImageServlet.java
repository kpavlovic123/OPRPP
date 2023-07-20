package hr.fer.oprpp2.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import java.awt.BasicStroke;

public class ReportImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        resp.setContentType("image/png");

        OutputStream outputStream = resp.getOutputStream();

        JFreeChart chart = getChart();
        int width = 500;
        int height = 350;
        ChartUtils.writeChartAsPNG(outputStream, chart, width, height);

        outputStream.close();

    }

    public JFreeChart getChart() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.setValue("Linux", 10);
        dataset.setValue("Windows", 15);
        dataset.setValue("Mac", 7);

        boolean legend = true;
        boolean tooltips = false;
        boolean urls = false;

        JFreeChart chart = ChartFactory.createPieChart("OS usage", dataset, legend, tooltips, urls);

        chart.setBorderStroke(new BasicStroke(5.0f));
        chart.setBorderVisible(true);

        return chart;
    }

}
