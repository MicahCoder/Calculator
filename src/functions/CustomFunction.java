package functions;

import java.util.function.DoubleFunction;

public class CustomFunction implements Function {
    private final DoubleFunction<Double> function;
    private String name = "f";
    private String var = "x";
    private final String literal;

    // List of coeefiicients, starting by the highest order
    public CustomFunction(String literal, DoubleFunction<Double> function) {
        this.literal = literal;
        this.function = function;
    }

    public CustomFunction(String functionName, String varName, String literal, DoubleFunction<Double> function) {
        this(literal, function);
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
        return literal;
    }

    public String toString() {
        return name + "(" + var + ") = " + toTex();
    }

}
