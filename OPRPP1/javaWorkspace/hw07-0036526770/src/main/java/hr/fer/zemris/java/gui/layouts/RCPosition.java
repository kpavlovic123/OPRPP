package hr.fer.zemris.java.gui.layouts;

public class RCPosition {
    private int row;
    private int column;

    public RCPosition(int row, int column) {
        if (row < 1 || row > 5 || column < 1 || column > 7)
            throw new CalcLayoutException();
        if (row == 1 && column > 1 && column < 6)
            throw new CalcLayoutException();
        this.row = row;
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RCPosition))
            return false;
        RCPosition r = (RCPosition) o;
        if (row == r.row && column == r.column)
            return true;
        else
            return false;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public static RCPosition parse(String text) {
        text = text.replace("/s+", "");
        String[] pair = text.split(",");
        if (pair[0].matches("[0-9]+") && pair[1].matches("[0-9]+"))
            return new RCPosition(Integer.parseInt(pair[0]), Integer.parseInt(pair[1]));
        else
            throw new IllegalArgumentException();
    }
}
