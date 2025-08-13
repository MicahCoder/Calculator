package functions;

import java.util.function.DoubleFunction;

public class Sum implements Function {
    private final DoubleFunction<Double> function;
    private String name = "f";
    private String var = "x";
    private final Function[] functions;

    // List of coeefiicients, starting by the highest order
    public Sum(Function... functions) {
        this.functions = functions;
        this.function = (x) -> {
            double sum = 0;
            for (Function function : functions) {
                sum += function.apply(x);
            }
            return sum;
        };

    }

    public Sum(String functionName, String varName, Function... functions) {
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
        String out = functions[0].toTex();
        for (int i = 1; i < functions.length; i++) {
            out += "+" + functions[i].toTex();
        }
        return out;
    }

    public String toString() {
        return name + "(" + var + ") = " + toTex();
    }

    @Override
    public Function prime() {
        Function[] primes = new Function[functions.length];
        for (int i = 0; i < functions.length; i++) {
            primes[i] = functions[i].prime();
        }
        return new Sum(name + "'", var, primes);
    }

}
