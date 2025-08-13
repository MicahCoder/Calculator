package functions;

import java.util.function.DoubleFunction;

public class Exponential implements Function {
    private final DoubleFunction<Double> function;
    private String name = "f";
    private final double base;
    private String baseString;
    private String var = "x";

    // List of coeefiicients, starting by the highest order
    public Exponential(double base) {
        this.base = base;
        this.baseString = doubleToString(base);
        function = (x) -> Math.pow(base, x);
    }

    public Exponential(String functionName, String varName, double base) {
        this(base);
        this.name = functionName;
        this.var = varName;
    }

    public Exponential(String functionName, String varName, String baseName, double base) {
        this(base);
        this.baseString = baseName;
        this.name = functionName;
        this.var = varName;
    }

    public Exponential(String baseName, double base) {
        this(base);
        this.baseString = baseName;
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
        return baseString + "^{" + var + "}";
    }

    @Override
    public String toString() {
        return name + "(" + var + ") = " + toTex();
    }

    @Override
    public Function prime() {
        return new Product(name + "'", var, new Constant("\\ln{" + doubleToString(base) + "}", Math.log(base)),
                new Exponential(name + "'", var, base));
    }
}
