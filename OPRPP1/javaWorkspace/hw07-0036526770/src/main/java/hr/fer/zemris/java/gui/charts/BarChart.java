package hr.fer.zemris.java.gui.charts;

import java.util.List;

public class BarChart {
    List<XYValue> l;
    String descX, descY;
    int minY,maxY,g;
    public BarChart(List<XYValue> l,String descX,String descY,int minY,int maxY,int g){
        l.forEach((v)->{
            if(v.getY()<minY)
                throw new IllegalArgumentException();
        });
        this.l = l;
        this.descX = descX;
        this.descY = descY;
        this.minY = minY;
        if(maxY-minY % g != 0)
            this.maxY = maxY - (maxY-minY%g) + g;
        else
            this.maxY = maxY;
        this.g = g;
    }

}
