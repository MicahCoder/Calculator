package functions;

import java.util.function.DoubleFunction;

public class Composite implements Function {
    private final DoubleFunction<Double> function;
    private String name = "f";
    private String var = "x";
    private final Function function1;
    private final Function function2;

    // List of coeefiicients, starting by the highest order
    public Composite(Function function1, Function function2) {
        this.function1 = function1;
        this.function2 = function2;
        this.name = function1.getName() + "\\circ " + function2.getName();
        this.function = (x) -> function1.apply(function2.apply(x));

    }

    public Composite(String functionName, String varName, Function function1, Function function2) {
        this(function1, function2);
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
        return function1.toTex().replace(function1.getVar(), "\\left(" + function2.toTex() + "\\right)");
    }

    @Override
    public String toString() {
        return name + "(" + var + ") = " + toTex();
    }

    @Override
    public Function prime() {
        return new Product(name + "'", var,
                function2.prime(),
                new Composite(function1.prime(), function2));
    }
}
