package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import static java.lang.Math.*;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;
import java.awt.event.*;

public class Calculator extends JFrame {
    CalcModelImpl calc;

    public Calculator() {
        calc = new CalcModelImpl();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        InitGui();
        setSize(600, 300);
    }

    public void InitGui() {
        Container cp = getContentPane();
        cp.setLayout(new CalcLayout(5));
        JLabel l = new JLabel();
        l.setBackground(Color.YELLOW);
        l.setOpaque(true);
        calc.addCalcValueListener(calc -> {
            l.setText(calc.toString());
        });
        cp.add(l, "1,1");

        cp.add(new CalcOpButton("=", e -> {
            if (calc.isActiveOperandSet()) {
                DoubleBinaryOperator op = calc.getPendingBinaryOperation();
                Double o1 = calc.getActiveOperand();
                Double o2 = calc.getValue();
                Double result = op.applyAsDouble(o1, o2);
                calc.clearActiveOperand();
                calc.setPendingBinaryOperation(null);
                calc.setValue(result);
            } else {
                calc.setValue(calc.getValue());
            }
            calc.freezeValue(null);
        }), "1,6");

        cp.add(new CalcOpButton("clr", e -> {
            calc.clear();
            l.setText("");
        }), "1,7");

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                JButton b = new JButton(Integer.toString(x + (6 - y * 3) + 1));
                int x2 = x;
                int y2 = y;
                b.addActionListener(e -> {
                    calc.insertDigit(x2 + (6 - y2 * 3) + 1);
                });
                cp.add(b, new RCPosition(y + 2, x + 3));
            }
        }
        cp.add(new CalcOpButton("reset", e -> {
            calc.clearAll();
            l.setText("");
        }), "2,7");
        cp.add(new CalcOpButton("push", e -> {
        }), "3,7");
        cp.add(new CalcOpButton("pop", e -> {
        }), "4,7");

        cp.add(new CalcOpButton("0", e -> {
            calc.insertDigit(0);
        }), "5,3");
        cp.add(new CalcOpButton("+/-", e -> {
            calc.swapSign();
        }), "5,4");
        cp.add(new CalcOpButton(".", e -> {
            calc.insertDecimalPoint();
        }), "5,5");

        cp.add(new CalcOpButton("/", (left, right) -> left / right), "2,6");
        cp.add(new CalcOpButton("*", (left, right) -> left * right), "3,6");
        cp.add(new CalcOpButton("-", (left, right) -> left - right), "4,6");
        cp.add(new CalcOpButton("+", (left, right) -> left + right), "5,6");
        cp.add(new CalcOpButton("x^n", (left, right) -> pow(left, right)), "5,1");

        JCheckBox inv = new JCheckBox();
        inv.setText("Inv");

        cp.add(inv, "5,7");

        cp.add(new CalcOpButton("1/x", e -> {
            calc.setValue(1 / calc.getValue());
        }), "2,1");

        cp.add(new CalcOpButton("sin", "arcsin", e -> {
            if (inv.isSelected())
                calc.setValue(asin(calc.getValue()));
            else
                calc.setValue(sin(calc.getValue()));
        }, inv), "2,2");

        cp.add(new CalcOpButton("log", "10^n", e -> {
            if (inv.isSelected())
                calc.setValue(pow(10, calc.getValue()));
            else
                calc.setValue(log10(calc.getValue()));
        }, inv), "3,1");

        cp.add(new CalcOpButton("cos", "arccos", e -> {
            if (inv.isSelected())
                calc.setValue(acos(calc.getValue()));
            else
                calc.setValue(cos(calc.getValue()));
        }, inv), "3,2");

        cp.add(new CalcOpButton("ln", "e^n", e -> {
            if (inv.isSelected())
                calc.setValue(pow(Math.E, calc.getValue()));
            else
                calc.setValue(log(calc.getValue()));
        }, inv), "4,1");

        cp.add(new CalcOpButton("tan", "arctan", e -> {
            if (inv.isSelected())
                calc.setValue(atan(calc.getValue()));
            else
                calc.setValue(tan(calc.getValue()));
        }, inv), "4,2");

        cp.add(new CalcOpButton("ctg", "arcctg", e -> {
            if (inv.isSelected())
                calc.setValue(atan(1 / calc.getValue()));
            else
                calc.setValue(1 / tan(calc.getValue()));
        }, inv), "5,2");
    }

    private class CalcOpButton extends JButton {

        CalcOpButton(String name, ActionListener l) {
            setName(name);
            setText(name);
            addActionListener(l);
        }

        CalcOpButton(String name, String nameInv, ActionListener l, JCheckBox inv) {
            setName(name);
            setText(name);
            addActionListener(l);
            inv.addActionListener(e -> {
                setText(getText() == name ? nameInv : name);
            });
        }

        CalcOpButton(String name, DoubleBinaryOperator op) {
            setText(name);
            addActionListener(e -> {
                double result;
                if (!calc.isActiveOperandSet()) {
                    calc.setActiveOperand(calc.getValue());
                    result = calc.getValue();
                } else {
                    double op1 = calc.getActiveOperand();
                    double op2 = calc.getValue();
                    result = calc.getPendingBinaryOperation().applyAsDouble(op1, op2);
                    calc.setActiveOperand(result);

                }
                calc.setValue(result);
                calc.setPendingBinaryOperation(op);
                calc.clear();
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator c = new Calculator();
            c.setVisible(true);
        });

    }
}
