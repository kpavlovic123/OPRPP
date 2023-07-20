package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

public class CalcModelImpl implements CalcModel {

    private boolean editable = true;
    private boolean positive = true;
    private String rep = "";
    private double value = 0;
    private String freezeValue;
    private Double activeOperand;
    private DoubleBinaryOperator pendingOperation;
    private List<CalcValueListener> listeners = new ArrayList<>();

    @Override
    public void addCalcValueListener(CalcValueListener l) {
        listeners.add(l);
    }

    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        listeners.remove(l);
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public void setValue(double value) {
        this.value = value;
        if (Double.isFinite(value)) {
            if (value >= 0) {
                rep = Double.toString(value);
                positive = true;
            } else {
                rep = Double.toString(-1 * value);
                positive = false;
            }
        } else if (Double.isNaN(value)) {
            rep = "NaN";
            positive = true;
        } else if (Double.isInfinite(value)) {
            rep = "Infinity";
            if (value == Double.POSITIVE_INFINITY)
                positive = true;
            else
                positive = false;
        }
        freezeValue = rep;
        editable = false;
        for(var l : listeners){
            l.valueChanged(this);
        }
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public void clear() {
        value = 0;
        rep = "";
        positive = true;
        editable = true;
        for(var l : listeners){
            l.valueChanged(this);
        }
    }

    @Override
    public void clearAll() {
        value = 0;
        rep = "";
        positive = true;
        activeOperand = null;
        pendingOperation = null;
        freezeValue = null;
        editable = true;
        for(var l : listeners){
            l.valueChanged(this);
        }
    }

    @Override
    public void swapSign() throws CalculatorInputException {
        if (!isEditable())
            throw new CalculatorInputException();
        positive = !positive;
        value = value * (-1);
        freezeValue = null;
        for(var l : listeners){
            l.valueChanged(this);
        }
    }

    @Override
    public void insertDecimalPoint() throws CalculatorInputException {
        if (!isEditable() || rep.contains(".") || rep.equals(""))
            throw new CalculatorInputException();
        rep += ".";
        freezeValue = null;
        for(var l : listeners){
            l.valueChanged(this);
        }
    }

    @Override
    public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
        if (!isEditable())
            throw new CalculatorInputException();
        if (rep.equals("0") && digit == 0) {
        } else if (digit != 0 && rep.startsWith("0") && !rep.contains(".")) {
            rep = Integer.toString(digit);
        } else {
            String tmp = rep + Integer.toString(digit);
            if (tmp.replace("\\.", "").length() > 308)
                throw new CalculatorInputException();
            double tmpValue = Double.parseDouble(tmp);
            value = positive ? tmpValue : -1 * tmpValue;
            rep = tmp;
        }
        freezeValue = null;
        for(var l : listeners){
            l.valueChanged(this);
        }
    }

    @Override
    public boolean isActiveOperandSet() {
        return activeOperand == null ? false : true;
    }

    @Override
    public double getActiveOperand() throws IllegalStateException {
        if (!isActiveOperandSet())
            throw new IllegalStateException();
        return activeOperand;
    }

    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = activeOperand;
    }

    @Override
    public void clearActiveOperand() {
        activeOperand = null;
    }

    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return pendingOperation;
    }

    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        pendingOperation = op;
    }

    public void freezeValue(String value) {
        freezeValue = value;
    }

    @Override
    public String toString() {
        if (freezeValue != null) {
            return positive ? freezeValue : "-"+freezeValue;
        } else if (rep.equals("")) {
            return positive ? "0" : "-0";
        } else
            return positive ? rep : "-" + rep;
    }
}
