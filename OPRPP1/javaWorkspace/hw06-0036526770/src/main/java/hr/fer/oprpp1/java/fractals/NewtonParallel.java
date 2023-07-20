package hr.fer.oprpp1.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.oprpp1.java.math.Complex;
import hr.fer.oprpp1.java.math.ComplexPolynomial;
import hr.fer.oprpp1.java.math.ComplexRootedPolynomial;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;

public class NewtonParallel {
    private static List<Complex> roots;
    private static int w = 0;
    private static int t = 0;
    private static ComplexRootedPolynomial crp;
    private static ComplexPolynomial cp;
    private static ComplexPolynomial cpDerived;

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
            } else if (input.equals("")) {
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

    public static void parseConfiguration(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String s = args[i];
            if (s.startsWith("--workers=")) {
                if (w != 0) {
                    throw new IllegalArgumentException("Only one command for number of workers is allowed");
                }

                String wS = s.substring(s.indexOf("=") + 1);
                w = Integer.parseInt(wS);
            } else if (s.startsWith("--tracks=")) {
                if (t != 0) {
                    throw new IllegalArgumentException("Only one command for number of tracks is allowed");
                }
                String tS = s.substring(s.indexOf("=") + 1);
                t = Integer.parseInt(tS);
            } else if (s.equals("-w")) {
                if (w != 0) {
                    throw new IllegalArgumentException("Only one command for number of workers is allowed");
                }
                w = Integer.parseInt(args[i + 1]);
                i++;
            } else if (s.equals("-t")) {
                if (t != 0) {
                    throw new IllegalArgumentException("Only one command for number of tracks is allowed");
                }
                t = Integer.parseInt(args[i + 1]);
                i++;
            }
        }

        if (w == 0) {
            w = Runtime.getRuntime().availableProcessors();
        }

        if (t == 0) {
            t = Runtime.getRuntime().availableProcessors() * 4;
        }

    }

    public static class PosaoIzracuna implements Runnable {
        double reMin;
        double reMax;
        double imMin;
        double imMax;
        int width;
        int height;
        int yMin;
        int yMax;
        int m;
        short[] data;
        AtomicBoolean cancel;
        public static PosaoIzracuna NO_JOB = new PosaoIzracuna();

        private PosaoIzracuna() {
        }

        public PosaoIzracuna(double reMin, double reMax, double imMin,
                double imMax, int width, int height, int yMin, int yMax,
                int m, short[] data, AtomicBoolean cancel) {
            super();
            this.reMin = reMin;
            this.reMax = reMax;
            this.imMin = imMin;
            this.imMax = imMax;
            this.width = width;
            this.height = height;
            this.yMin = yMin;
            this.yMax = yMax;
            this.m = m;
            this.data = data;
            this.cancel = cancel;
        }

        @Override
        public void run() {
            int offset = yMin*width;
            int maxIter = 16;
            for (int y = yMin; y <= yMax; y++) {
                if (cancel.get())
                    break;
                for (int x = 0; x < width; x++) {
                    double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
                    double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
                    Complex zold = new Complex(cre, cim);
                    double module = 0;
                    int iters = 0;
                    do {
                        Complex numerator;
                        Complex denominator;
                        synchronized(cp){
                            numerator = cp.apply(zold);
                            denominator = cpDerived.apply(zold);
                        }
                        Complex fraction = numerator.divide(denominator);
                        Complex znew = zold.sub(fraction);
                        module = znew.sub(zold).module();
                        iters++;
                        zold = znew;
                    } while (module > 0.001 && iters < maxIter);
                    synchronized(data){
                        data[offset] = (short) (crp.indexOfClosestRootFor(zold, 0.7) + 1);
                    }
                    offset++;
                }

            }
        }
    }

    public static class MojProducer implements IFractalProducer {
        @Override
        public void produce(double reMin, double reMax, double imMin, double imMax,
                int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
            int m = cp.order() + 1;
            short[] data = new short[width * height];
            if (t > height) {
                t = height;
            }
            final int brojTraka = t;
            int brojYPoTraci = height / brojTraka;

            System.out.printf("Broj workera: %d | broj traka: %d\n", w, t);

            final BlockingQueue<PosaoIzracuna> queue = new LinkedBlockingQueue<>();

            Thread[] radnici = new Thread[w];
            for (int i = 0; i < radnici.length; i++) {
                radnici[i] = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            PosaoIzracuna p = null;
                            try {
                                p = queue.take();
                                if (p == PosaoIzracuna.NO_JOB)
                                    break;
                            } catch (InterruptedException e) {
                                continue;
                            }
                            p.run();
                        }
                    }
                });
            }
            for (int i = 0; i < radnici.length; i++) {
                radnici[i].start();
            }

            for (int i = 0; i < brojTraka; i++) {
                int yMin = i * brojYPoTraci;
                int yMax = (i + 1) * brojYPoTraci - 1;
                if (i == brojTraka - 1) {
                    yMax = height - 1;
                }
                PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m,
                        data, cancel);
                while (true) {
                    try {
                        queue.put(posao);
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }
            for (int i = 0; i < radnici.length; i++) {
                while (true) {
                    try {
                        queue.put(PosaoIzracuna.NO_JOB);
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }

            for (int i = 0; i < radnici.length; i++) {
                while (true) {
                    try {
                        radnici[i].join();
                        break;
                    } catch (InterruptedException e) {
                    }
                }
            }

            System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
            observer.acceptResult(data, (short) m, requestNo);
        }
    }

    public static void main(String[] args) {
        parseConfiguration(args);
        getInput();
        crp = new ComplexRootedPolynomial(new Complex(1, 1),
                roots.toArray(new Complex[roots.size()]));
        cp = crp.toComplexPolynom();
        cpDerived = cp.derive();
        FractalViewer.show(new MojProducer());
    }
}