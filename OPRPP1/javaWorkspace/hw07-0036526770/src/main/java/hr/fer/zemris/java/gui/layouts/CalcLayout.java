package hr.fer.zemris.java.gui.layouts;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CalcLayout implements LayoutManager2 {

    private List<Component> componentList;
    private List<RCPosition> positionList;
    private int gap;

    public CalcLayout() {
        this(0);
    }

    private Dimension getSize(int mode) {
        int width = 0;
        int height = 0;
        for (int i = 0; i < componentList.size(); i++) {
            Component c = componentList.get(i);
            Dimension d;
            if(mode == 1)
                d = c.getPreferredSize();
            else{
                d = c.getMinimumSize();
            }
            if(d.height > height)
                height = d.height;
            if(d.width > width)
                width = d.width;
        }
        height = 5 * height + 4 * gap;
        width = 7 * width + 6 * gap;
        return new Dimension(width,height);   
    }

    public CalcLayout(int gap) {
        this.gap = gap;
        componentList = new ArrayList<>();
        positionList = new ArrayList<>();
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        int index = componentList.indexOf(comp);
        positionList.remove(index);
        componentList.remove(index);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Dimension d = getSize(1);
        Insets inset = parent.getInsets();
        return new Dimension(d.width + inset.left + inset.right, d.height + inset.top + inset.bottom);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        Dimension d = getSize(2);
        Insets inset = parent.getInsets();
        return new Dimension(d.width + inset.left + inset.right, d.height + inset.top + inset.bottom);
    }

    @Override
    public void layoutContainer(Container parent) {
        Insets inset = parent.getInsets();
        int width = parent.getWidth() - inset.right - inset.left;
        int height = parent.getHeight() - inset.top - inset.bottom;
        int x = inset.left;
        int y = inset.top;
        double cHeight = (height - 4 * gap) / 5;
        double cWidth = (width - 6 * gap) / 7;
        for (int i = 0; i < componentList.size(); i++) {
            Component c = componentList.get(i);
            RCPosition p = positionList.get(i);
            if (p.getRow() == 1 && p.getColumn() == 1) {
                c.setBounds(x, y, (int)(cWidth * 5 + 4 * gap), (int) cHeight);
                continue;
            }
            int newX = x + (int) ((p.getColumn() - 1) * cWidth) + (p.getColumn() - 1) *
                    gap;
            int newY = y + (int) ((p.getRow() - 1) * cHeight) + (p.getRow() - 1) * gap;
            double wLeftover = cWidth % 1;
            double hLeftover = cHeight % 1;
            int newCWidth = (int) cWidth;
            int newCHeight = (int) cHeight;
            if (wLeftover * (double) p.getColumn() > 1.0)
                newCWidth++;
            if (hLeftover * (double) p.getRow() > 1.0)
                newCWidth++;
            c.setBounds(newX, newY, newCWidth, newCHeight);
        }

    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        RCPosition c;
        if (comp == null || constraints == null)
            throw new NullPointerException();
        else if (constraints instanceof String) {
            c = RCPosition.parse((String) constraints);
        } else if (constraints instanceof RCPosition) {
            c = (RCPosition) constraints;
        } else
            throw new IllegalArgumentException();
        if (positionList.contains(constraints))
            throw new CalcLayoutException();
        componentList.add(comp);
        positionList.add(c);
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {
    }
}
