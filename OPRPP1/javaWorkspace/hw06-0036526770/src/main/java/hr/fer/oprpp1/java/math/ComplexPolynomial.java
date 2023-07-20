package hr.fer.oprpp1.java.math;

public class ComplexPolynomial {
    final private Complex[] factors;

    // constructor
    public ComplexPolynomial(Complex... factors) {
        this.factors = factors;
    }

    // returns order of this polynom; eg. For (7+2i)z^3+2z^2+5z+1 returns 3
    public short order() {
        return (short) (factors.length - 1);
    }

    // computes a new polynomial this*p
    public ComplexPolynomial multiply(ComplexPolynomial p) {
        Complex[] newFactors = new Complex[factors.length + p.factors.length - 1];
        for (int i = 0; i < factors.length; i++) {
            for (int j = 0; i < p.factors.length; j++) {
                int index = i + j;
                Complex newFactor = factors[i].multiply(p.factors[j]);
                if (newFactors[index] == null) {
                    newFactors[index] = newFactor;
                } else {
                    newFactors[index] = newFactors[index].add(newFactor);
                }
            }
        }
        return new ComplexPolynomial(newFactors);
    }

    // computes first derivative of this polynomial; for example, for
    // (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
    public ComplexPolynomial derive() {
        Complex[] newFactors = new Complex[factors.length - 1];
        for (int i = 0; i < newFactors.length; i++) {
            newFactors[i] = factors[i + 1].multiply(new Complex(i + 1, 0));
        }
        return new ComplexPolynomial(newFactors);
    }

    // computes polynomial value at given point z
    public Complex apply(Complex z) {
        Complex result = new Complex();
        for (int i = 0; i < factors.length; i++) {
            result = result.add(factors[i].multiply(z.power(i)));
        }
        return result;
    }

    @Override
    public String toString() {
        String expression = "";
        for (int i = factors.length - 1; i >= 0; i--) {
            expression+="("+factors[i].toString()+")*z^" + Integer.toString(i)+"+";
        }
        expression = expression.substring(0, expression.length() - 5);
        return expression;
    }

    public static void main(String[] args) {
        ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
                new Complex(2, 0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG);
        ComplexPolynomial cp = crp.toComplexPolynom();
        System.out.println(crp);
        System.out.println(cp);
        System.out.println(cp.derive());
    }
}