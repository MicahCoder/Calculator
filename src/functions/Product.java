package functions;

import java.util.Arrays;
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

    @Override
    public String toString() {
        return name + "(" + var + ") = " + toTex();
    }

    @Override
    public Function prime() {
        if (functions.length == 2) {
            return new Sum(name + "'", var, new Product(functions[0].prime(), functions[1]),
                    new Product(functions[0], functions[1].prime()));
        }
        Function smallerProd = new Product(Arrays.copyOf(functions, functions.length - 1));
        Function last = functions[functions.length - 1];
        return new Sum(name + "'", var, new Product(smallerProd.prime(), last), new Product(smallerProd, last.prime()));
    }

}
