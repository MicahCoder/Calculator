package functions;

import java.util.function.DoubleFunction;

public class CustomFunction implements Function {
    private final DoubleFunction<Double> function;
    private String name = "f";
    private String var = "x";
    private final String literal;
    private final Function derivative;

    // List of coeefiicients, starting by the highest order
    public CustomFunction(String literal, DoubleFunction<Double> function) {
        this.literal = literal;
        this.function = function;
        this.derivative = null;
    }

    public CustomFunction(String functionName, String varName, String literal, DoubleFunction<Double> function) {
        this(literal, function);
        this.name = functionName;
        this.var = varName;
    }

    public CustomFunction(String literal, DoubleFunction<Double> function, Function derivative) {
        this.literal = literal;
        this.function = function;
        this.derivative = derivative;
    }

    public CustomFunction(String functionName, String varName, String literal,
            DoubleFunction<Double> function,
            Function derivative) {
        this(literal, function, derivative);
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

    @Override
    public Function prime() {
        if (derivative == null) {
            throw new UnsupportedOperationException("Derivative not defined for this custom function.");
        }
        return derivative;
    }
}
