package hr.fer.oprpp1.java.fractals;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.*;
import hr.fer.oprpp1.java.math.*;

public class Newton {
    private static List<Complex> roots;

    private static Complex parseComplex(String input) {
        String re = "";
        String im = "";
        String s = "";
        String regex = "[\\+\\-]?i?[0-9]*\\.?[0-9]*";
        input = input.toLowerCase().replaceAll("\\s+", "");
        int n = 0;
        for (int i = 0; i < input.length(); i++) {
            s += input.charAt(i);
            if (s.matches(regex)) {
                continue;
            } else {
                if (n == 0) {
                    re = s.substring(0, s.length() - 1);
                    n++;
                    s = "";
                    i--;
                } else {
                    return null;
                }
            }
        }

        if (s.contains("i")) {
            if (n == 0) {
                re = "0";
            }
            im = s;
        } else {
            re = s;
            im = "0";
        }
        if (im.contains("i")) {
            if (im.endsWith("i")) {
                im = im.replace("i", "1");
            } else {
                im = im.replace("i", "");
            }
        }
        return new Complex(Double.parseDouble(re), Double.parseDouble(im));
    }

    private static void getInput() {
        System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
        System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
        Scanner scan = new Scanner(System.in);
        roots = new ArrayList<>();
        while (true) {
            System.out.printf("Root %d> ", roots.size() + 1);
            String input = scan.nextLine().trim();
            if (input.equals("done")) {
                if (roots.size() > 1)
                    break;
                else {
                    System.out.println("Atleast 2 roots are needed.");
                    continue;
                }
            }
            else if(input.equals("")){
                continue;
            }
            var c = parseComplex(input);
            if (c == null) {
                System.out.println("Incorrect input. Try again.");
                continue;
            }
            System.out.println(c);
            roots.add(c);
        }
        scan.close();
    }

    public static class MojProducer implements IFractalProducer {
        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
            System.out.println("Image of fractal will appear shortly. Thank you.");
            ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(1, 0),roots.toArray(new Complex[roots.size()])); 
            ComplexPolynomial cp = crp.toComplexPolynom();
            ComplexPolynomial cpDerived = cp.derive();
            int m = cp.order() + 1;
            int offset = 0;
            short[] data = new short[width * height];
            int maxIter = 16;
            for (int y = 0; y < height; y++) {
                if (cancel.get())
                    break;
                for (int x = 0; x < width; x++) {
                    double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
                    double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
                    Complex zold = new Complex(cre, cim);
                    double module = 0;
                    int iters = 0;
                    do {
                        Complex numerator = cp.apply(zold);
                        Complex denominator = cpDerived.apply(zold);
                        Complex fraction = numerator.divide(denominator);
                        Complex znew = zold.sub(fraction);
                        module = znew.sub(zold).module();
                        iters++;
                        zold = znew;
                    } while (module > 0.001 && iters < maxIter);
                    data[offset] = (short) (crp.indexOfClosestRootFor(zold, 0.7) + 1);
                    offset++;
                }
            }
            observer.acceptResult(data, (short) m, requestNo);
        }
    }

    public static void main(String[] args) {
        getInput();
        FractalViewer.show(new MojProducer());
    }

}
