package functions;

import java.util.function.DoubleFunction;

public class Constant implements Function {
    private final DoubleFunction<Double> function;
    private String name = "f";
    private String valueString;
    private String var = "x";

    // List of coeefiicients, starting by the highest order
    public Constant(double value) {
        this.valueString = doubleToString(value);
        function = (x) -> value;
    }

    public Constant(String functionName, String varName, double value) {
        this(value);
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
        return valueString;
    }

    public String toString() {
        return name + "(" + var + ") = " + toTex();
    }

}
