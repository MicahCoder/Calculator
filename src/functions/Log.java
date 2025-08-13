package functions;

import java.util.function.DoubleFunction;

public class Log implements Function {
    private final DoubleFunction<Double> function;
    private String name = "f";
    private final double base;
    private String baseString;
    private String var = "x";

    // List of coeefiicients, starting by the highest order
    public Log(double base) {
        this.base = base;
        this.baseString = doubleToString(base);
        function = (x) -> Math.log(x) / Math.log(base);
    }

    public Log(String functionName, String varName, double base) {
        this(base);
        this.name = functionName;
        this.var = varName;
    }

    public Log(String functionName, String varName, String baseName, double base) {
        this(base);
        this.baseString = baseName;
        this.name = functionName;
        this.var = varName;
    }

    public Log(String baseName, double base) {
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
        return "\\log_{" + baseString + "}{" + var + "}";
    }

    public String toString() {
        return name + "(" + var + ") = " + toTex();
    }

    @Override
    public Function prime() {
        return new Rational(name + "'", var, new Constant(1),
                new Product(new Constant("\\ln{" + doubleToString(base) + "}",
                        Math.log(base)), new Monomial(1, 1)));
    }
}
