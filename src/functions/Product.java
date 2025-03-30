package functions;

import java.util.function.DoubleFunction;

public class Product implements Function {
    private final DoubleFunction<Double> function;
    private String name = "f";
    private String var = "x";
    private final Function[] functions;

    // List of coeefiicients, starting by the highest order
    public Product(Function... functions) {
        this.functions = functions;
        this.function = (x) -> {
            double product = 1;
            for (Function function : functions) {
                product *= function.apply(x);
            }
            return product;
        };

    }

    public Product(String functionName, String varName, Function... functions) {
        this(functions);
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
        String out = "(" + functions[0].toTex() + ")";
        for (int i = 1; i < functions.length; i++) {
            out += "(" + functions[i].toTex() + ")";
        }
        return out;
    }

    public String toString() {
        return name + "(" + var + ") = " + toTex();
    }

}
