
import functions.Function;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.DoubleFunction;

public class Calculator {
    private static int sigFigs = 4;

    public enum Mode {
        TRAPEZOIDAL("Trapzeoidal Sum"),
        RIGHT("Right Reimman Sum"),
        LEFT("Left Reimman Sum"),
        T("Trapzeoidal Sum"),
        R("Right Reimman Sum"),
        L("Left Reimman Sum");

        private final String string;

        Mode(String string) {
            this.string = string;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    public static void setSigFigs(int figs) {
        sigFigs = figs;
    }

    // Integrates f(x) from a to b with a step size of dx.
    // Right Reimman Approximation
    public static double leftReimmanIntegrate(DoubleFunction<Double> f, double a, double b, double dx) {
        double out = 0.0;
        for (double x = a; x < b; x += dx) {
            double fdx = f.apply(x) * dx;
            out += Double.isNaN(fdx) || Double.isInfinite(fdx) ? 0 : fdx;
        }
        return out;
    }

    // Integrates f(x) from a to b with a step size of dx.
    // Left Reimman Approximation
    public static double rightReimmanIntegrate(DoubleFunction<Double> f, double a, double b, double dx) {
        double out = 0.0;
        for (double x = a + dx; x <= b; x += dx) {
            double fdx = f.apply(x) * dx;
            out += Double.isNaN(fdx) || Double.isInfinite(fdx) ? 0 : fdx;
        }
        return out;
    }

    // Integrates f(x) from a to b with a step size of dx.
    // Trapezoidal Approximation
    public static double trapezoidalReimmanIntegrate(DoubleFunction<Double> f, double a, double b, double dx) {
        double out = 0.0;
        for (double x = a; x <= b; x += dx) {
            double fdx = (f.apply(x) + f.apply(x + dx)) / 2 * dx;
            out += Double.isNaN(fdx) || Double.isInfinite(fdx) ? 0 : fdx;
        }
        return out;
    }

    // Let's you choose your method of integration, Defauly is LeftReimman
    public static double integrate(DoubleFunction<Double> f, double a, double b, double dx) {
        return integrate(f, a, b, dx, Mode.L);
    }

    // Mode Chooser
    public static double integrate(DoubleFunction<Double> f, double a, double b, double dx, Mode mode) {
        switch (mode) {
            case LEFT:
                return leftReimmanIntegrate(f, a, b, dx);
            case RIGHT:
                return rightReimmanIntegrate(f, a, b, dx);
            case TRAPEZOIDAL:
                return trapezoidalReimmanIntegrate(f, a, b, dx);
            case L:
                return leftReimmanIntegrate(f, a, b, dx);
            case R:
                return rightReimmanIntegrate(f, a, b, dx);
            case T:
                return trapezoidalReimmanIntegrate(f, a, b, dx);
            default:
                return leftReimmanIntegrate(f, a, b, dx);
        }
    }

    // String Chooser
    public static double integrate(DoubleFunction<Double> f, double a, double b, double dx, String mode) {
        return integrate(f, a, b, dx, stringToMode(mode));
    }

    // With steps
    public static double integrate(DoubleFunction<Double> f, double a, double b, int steps) {
        return integrate(f, a, b, (b - a) / steps);
    }

    public static double integrate(DoubleFunction<Double> f, double a, double b, int steps, Mode mode) {
        return integrate(f, a, b, (b - a) / steps, mode);
    }

    public static double integrate(DoubleFunction<Double> f, double a, double b, int steps, String mode) {
        return integrate(f, a, b, (b - a) / steps, mode);
    }

    // Error bounds
    public static double reimmanErrorBound(DoubleFunction<Double> f, double a, double b, int steps) {
        return maxAmplitude(prime(f), a, b) * Math.pow(b - a, 2) / (2 * steps);
    }

    public static double trapezoidalErrorBound(DoubleFunction<Double> f, double a, double b, int steps) {
        long denominator = (long) steps * (long) steps * (long) 12.0;
        return maxAmplitude(prime(prime(f)), a, b) * Math.pow(b - a, 3) / (denominator);
    }

    public static double errorBound(DoubleFunction<Double> f, double a, double b, int steps) {
        return errorBound(f, a, b, steps, Mode.L);
    }

    public static double errorBound(DoubleFunction<Double> f, double a, double b, int steps, Mode mode) {
        switch (mode) {
            case LEFT:
                return reimmanErrorBound(f, a, b, steps);
            case RIGHT:
                return reimmanErrorBound(f, a, b, steps);
            case TRAPEZOIDAL:
                return trapezoidalErrorBound(f, a, b, steps);
            case L:
                return reimmanErrorBound(f, a, b, steps);

            case R:
                return reimmanErrorBound(f, a, b, steps);

            case T:
                return trapezoidalErrorBound(f, a, b, steps);
            default:
                return reimmanErrorBound(f, a, b, steps);

        }

    }

    public static double errorBound(DoubleFunction<Double> f, double a, double b, int steps, String mode) {
        return errorBound(f, a, b, steps, stringToMode(mode));
    }

    // Max value on an interval
    public static double max(DoubleFunction<Double> f, double a, double b) {
        List<Double> criticalPoints = new ArrayList<Double>();
        double fx = f.apply(a);
        if (!Double.isNaN(fx)) {
            criticalPoints.add(fx);
        }
        fx = f.apply(b);
        if (!Double.isNaN(fx)) {
            criticalPoints.add(fx);
        }

        DoubleFunction<Double> fPrime = prime(f);
        for (double i = a; i < b; i += 1e-2) {
            if (fPrime.apply(i) < 1e-1) {
                // System.out.println(i + ", " + val);
                criticalPoints.add(Math.abs(f.apply(i)));
            }
        }
        return Collections.max(criticalPoints);
    }

    public static double maxAmplitude(DoubleFunction<Double> f, double a, double b) {
        List<Double> criticalPoints = new ArrayList<Double>();
        double fx = Math.abs(f.apply(a));
        if (!Double.isNaN(fx)) {
            criticalPoints.add(fx);
        }
        fx = Math.abs(f.apply(b));
        if (!Double.isNaN(fx)) {
            criticalPoints.add(fx);
        }

        DoubleFunction<Double> fPrime = prime(f);
        for (double i = a; i < b; i += 1e-2) {
            if (Math.abs(fPrime.apply(i)) < 1e-1) {
                // System.out.println(i + ", " + val);
                criticalPoints.add(Math.abs(f.apply(i)));
            }
        }
        return Collections.max(criticalPoints);
    }

    // derivatives
    public static DoubleFunction<Double> prime(DoubleFunction<Double> f, double h) {
        return x -> (f.apply(x + h) - f.apply(x - h)) / (2 * h);
    }

    public static DoubleFunction<Double> prime(DoubleFunction<Double> f) {
        return prime(f, 1e-15);
    }

    public static double derivative(DoubleFunction<Double> f, double x, double h) {
        return prime(f, h).apply(x);
    }

    public static double derivative(DoubleFunction<Double> f, double x) {
        return prime(f).apply(x);
    }

    // Full Integral Prinout with Error Bound
    public static String evaluateIntegral(DoubleFunction<Double> f, double a, double b, int steps) {
        return evaluateIntegral(f, a, b, steps, Mode.L);
    }

    public static String evaluateIntegral(DoubleFunction<Double> f, double a, double b, int steps, Mode mode) {
        return "A " + mode.toString() + " was evaluated between " + a + " and " + b + " with " + steps
                + " steps\nValue: " + integrate(f, a, b, steps, mode) + "\nError Bound: ± "
                + errorBound(f, a, b, steps, mode);

    }

    public static Mode stringToMode(String mode) {
        switch (mode) {
            case "l":
                return Mode.L;
            case "L":
                return Mode.L;
            case "left":
                return Mode.L;
            case "Left":
                return Mode.L;
            case "r":
                return Mode.R;
            case "R":
                return Mode.R;
            case "right":
                return Mode.R;
            case "Right":
                return Mode.R;
            case "t":
                return Mode.T;
            case "T":
                return Mode.T;
            case "trapezoidal":
                return Mode.T;
            case "Trapezoidal":
                return Mode.T;
            default:
                return Mode.L;
        }
    }

    public static String evaluateIntegral(DoubleFunction<Double> f, double a,
            double b, int steps, String mode) {
        return evaluateIntegral(f, a, b, steps, stringToMode((mode)));
    }

    public static String evaluateIntegralTex(Function f, double a,
            double b, int steps, Mode mode) {
        double eBound = errorBound(f.getFunction(), a, b, steps, mode);
        int eBoundOrder = (int) Math.floor(Math.log10(eBound));
        double eBoundSigFigs = Math.round(eBound / (Math.pow(10.0, eBoundOrder)) * Math.pow(10.0, sigFigs))
                / Math.pow(10.0, sigFigs);
        String A = a == (int) a
                ? Integer.toString((int) a)
                : Double.toString(a);
        String B = b == (int) b ? Integer.toString((int) b) : Double.toString(b);

        String intAToB = "\\int_{"
                + A
                + "}^{" + B + "}";
        String dx = "d" + f.getVar();
        String funcName = f.getName() + "(" + f.getVar() + ")";
        double value = integrate(f.getFunction(), a, b, steps, mode);
        double accurateVal = Math.floor(value / Math.pow(10.0, eBoundOrder + 1))
                * Math.pow(10, eBoundOrder + 1);
        return "You evaluated a " + mode.toString() + " approximation of $$" + intAToB
                + "\\left("
                + f.toTex()
                + "\\right)"
                + dx + "$$ with " + steps
                + " steps.\nThis returned$$"
                + intAToB
                + funcName
                + dx + "="
                + value
                + "$$ with an error bound of $$" + "E<|"
                + eBoundSigFigs
                + (eBoundOrder != 0 ? "\\cdot 10^{" + (eBoundOrder == 1 ? "" : Integer.toString(eBoundOrder)) + "}"
                        : "")
                + "|$$"
                + "Accurate digits are $$"
                + accurateVal
                + "$$";
    }

    public static String evaluateIntegralTex(Function f, double a,
            double b, int steps) {
        return evaluateIntegralTex(f, a, b, steps, Mode.L);
    }

    public static String evaluateIntegralTex(Function f, double a,
            double b, int steps, String mode) {
        return evaluateIntegralTex(f, a, b, steps, stringToMode(mode));
    }
}