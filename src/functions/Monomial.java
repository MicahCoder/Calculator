package functions;

import java.util.function.DoubleFunction;

public class Monomial implements Function {
    private final DoubleFunction<Double> function;
    private String name = "f";
    private String var = "x";
    private final double coefficient;
    private final double power;

    // List of coeefiicients, starting by the highest order
    public Monomial(double coefficient, double power) {
        this.coefficient = coefficient;
        this.power = power;
        function = (x) -> coefficient * Math.pow(x, power);
    }

    public Monomial(String functionName, String varName, double coefficient, double power) {
        this(coefficient, power);
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
        return doubleToString(coefficient, "p") + var + (power == 1 ? "" : "^{" + doubleToString(power, "p") + "}");
    }

    @Override
    public String toString() {
        return name + "(" + var + ") = " + toTex();
    }

    @Override
    public Function prime() {
        return new Monomial(name + "'", var, coefficient * power, power - 1);
    }
}
