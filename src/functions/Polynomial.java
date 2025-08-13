package functions;

import java.util.function.DoubleFunction;

public class Polynomial implements Function {
    public final int order;
    private final DoubleFunction<Double> function;
    public final double[] coefficients;
    private String name = "f";
    private String var = "x";

    // List of coeefiicients, starting by the highest order
    public Polynomial(double... coefficient) {
        coefficients = coefficient;
        order = coefficients.length - 1;
        function = (x) -> {
            double sum = 0;
            for (int i = 0; i <= order; i++) {
                sum += coefficients[i] * Math.pow(x, order - i);
            }
            return sum;
        };
    }

    public Polynomial(String functionName, String varName, double... coefficient) {
        this(coefficient);
        this.name = functionName;
        this.var = varName;
    }

    @Override
    public DoubleFunction<Double> getFunction() {
        return function;
    }

    @Override
    public double apply(double x) {
        return function.apply(x);
    }

    @Override
    public String getVar() {
        return var;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toTex() {
        String out = "";
        for (int n = 0; n <= order; n++) {
            if (coefficients[n] == 0) {
                continue;
            }
            String coefficient = doubleToString(coefficients[n], "p");

            if ((order - n == 1)) {
                out += (n == 0 ? "" : " + ") + coefficient + var;
                continue;
            }
            if (n == order) {
                out += (order == 0 ? "" : " + ") + doubleToString(coefficients[n], "s");
                continue;
            }
            out += (n == 0 ? "" : " + ") + coefficient + var + "^" + (order - n);
        }
        return out;
    }

    public String toString() {
        return name + "(" + var + ") = " + toTex();
    }

    @Override
    public Function prime() {
        double[] primes = new double[coefficients.length - 1];
        for (int i = 0; i < coefficients.length - 1; i++) {
            primes[i] = coefficients[i] * (coefficients.length - i - 1);
        }
        return new Polynomial(name + "'", var, primes);
    }

}
