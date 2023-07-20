package hr.fer.oprpp1.java.math;
public class ComplexRootedPolynomial {
    final private Complex constant;
    final private Complex[] roots;
    // constructor
    public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
        this.constant = constant;
        this.roots = roots;
    }
    // computes polynomial value at given point z
    public Complex apply(Complex z) {
        Complex result = constant;
        for(var c : roots){
            result = result.multiply(z.sub(c));
        }
        return result;
    }
    // converts this representation to ComplexPolynomial type
    public ComplexPolynomial toComplexPolynom() {
        Complex[] factors = new Complex[roots.length+1];
        Complex[] copy = new Complex[roots.length+1];
        factors[0] = constant;
        for(int i = 0;i<roots.length;i++){
            System.arraycopy(factors, 0, copy, 0, i+1);
            factors[0] = factors[0].multiply(roots[i]);
            for(int j = 0;j<i;j++){
                factors[j+1] = factors[j+1].multiply(roots[i]).add(copy[j]); 
            }
            factors[i+1] = copy[i];
        }
        return new ComplexPolynomial(factors);
    }
    @Override
    public String toString() {
        String expression = "("+constant.toString()+")";
        for(var c : roots){
            expression += "*(z-("+c.toString()+"))";
        }
        return expression;
    }
    // finds index of closest root for given complex number z that is within
    // treshold; if there is no such root, returns -1
    // first root has index 0, second index 1, etc
    public int indexOfClosestRootFor(Complex z, double treshold) {
        int index = -1;
        double minDistance = Double.MAX_VALUE;
        for(int i = 0;i<roots.length;i++){
            double distance = z.sub(roots[i]).module();
            if(distance<=treshold && distance < minDistance){
                minDistance = distance;
                index = i;
            }
        }
        return index;
    }
    }