package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.geom.AffineTransform;
import java.util.Arrays;

public class BarChartComponent extends JComponent {
    BarChart bc;

    BarChartComponent(BarChart bc) {
        this.bc = bc;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        var p = getParent().getSize();
        var fm = g2.getFontMetrics();
        int defaultGap = 36;
        int leftGap = defaultGap + fm.stringWidth(String.valueOf(bc.maxY)) + fm.getAscent();
        int bottomGap = defaultGap + fm.getAscent() * 2;

        g2.setColor(Color.gray);
        g2.drawLine(leftGap, 0, leftGap, p.height - bottomGap);
        g2.drawLine(leftGap, p.height - bottomGap, p.width, p.height - bottomGap);
        g2.setColor(Color.BLACK);

        AffineTransform at = new AffineTransform();
        at.rotate(Math.PI / 2);
        g2.setTransform(at);
        g2.setColor(Color.BLACK);
        /* g2.drawString(bc.descY, defaultGap / 3 + fm.getAscent(), (p.height-fm.stringWidth(bc.descY))/2); */
        g2.drawString(bc.descY, p.height/2, -p.width/2);
        at.rotate(-Math.PI/2);
        g2.setTransform(at);

        /* int numOfGaps = (bc.maxY - bc.minY) / bc.g;
        int yGap = (p.height - bottomGap) / numOfGaps;
        int y0 = p.height - bottomGap + fm.getAscent() / 2;
        for (int i = 0; i < numOfGaps + 1; i++) {
            String text = String.valueOf(bc.minY + bc.g * i);
            g2.drawString(text, leftGap - fm.stringWidth(text), y0 + yGap * i);
        } */
    }

    public static void main(String[] args) {
        BarChart model = new BarChart(
                Arrays.asList(
                        new XYValue(1, 8), new XYValue(2, 20), new XYValue(3, 22),
                        new XYValue(4, 10), new XYValue(5, 4)),
                "Number of people in the car",
                "Frequency",
                0, // y-os kreće od 0
                22, // y-os ide do 22
                2);
        JFrame frame = new JFrame();
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.add(new BarChartComponent(model));
        try {
            SwingUtilities.invokeAndWait(() -> {
                frame.setVisible(true);
            });
        } catch (Exception e) {
        }
    }

    public BarChart getBc() {
        return bc;
    }

    public void setBc(BarChart bc) {
        this.bc = bc;
    }
}
