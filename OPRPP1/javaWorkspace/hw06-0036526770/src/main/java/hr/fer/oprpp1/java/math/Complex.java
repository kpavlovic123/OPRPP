package hr.fer.oprpp1.java.math;

import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.atan;

public class Complex {
    final private double re;
    final private double im;
    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex ONE_NEG = new Complex(-1, 0);
    public static final Complex IM = new Complex(0, 1);
    public static final Complex IM_NEG = new Complex(0, -1);

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public Complex() {
        this(0, 0);
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return re;
    }

    // returns module of complex number
    public double module() {
        return sqrt(re*re + im*im);
    }

    // returns this*c
    public Complex multiply(Complex c) {
        return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
    }

    // returns this/c
    public Complex divide(Complex c) {
        double newRe = (re * c.re + im * c.im) / (c.re*c.re + c.im*c.im);
        double newIm = (im * c.re - re * c.im) / (c.re*c.re+ c.im*c.im);
        return new Complex(newRe, newIm);
    }

    // returns this+c
    public Complex add(Complex c) {
        return new Complex(re + c.re, im + c.im);
    }

    // returns this-c
    public Complex sub(Complex c) {
        return new Complex(re - c.re, im - c.im);
    }

    // returns -this
    public Complex negate() {
        return new Complex(-re, -im);
    }

    // returns this^n, n is non-negative integer
    public Complex power(int n) {
        if (n==0)
            return new Complex(1,0);
        Complex z = new Complex(1,1);
        for(int i = 0;i<n;i++){
            z = z.multiply(this);
        }
        return z;
    }

    // returns n-th root of this, n is positive integer
    public List<Complex> root(int n) {
        List<Complex> list = new ArrayList<>();
        double angle = atan(im / re);
        double r = pow(module(), 1 / n);
        for (int i = 0; i < n; i++) {
            double newRe = r * cos((angle + 2 * Math.PI * i) / n);
            double newIm = r * sin((angle + 2 * Math.PI * i) / n);
            list.add(new Complex(newRe, newIm));
        }
        return list;
    }

    @Override
    public String toString() {
        String symbol = "";
        if (im >= 0) {
            symbol = "+i";
        } else {
            symbol = "-i";
        }
        return Double.toString(re) + symbol + Double.toString(Math.abs(im));
    }
}