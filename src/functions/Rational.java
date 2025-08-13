package functions;

import java.util.function.DoubleFunction;

public class Rational implements Function {
    private final DoubleFunction<Double> function;
    private String name = "f";
    private String var = "x";
    private final Function numerator;
    private final Function denominator;

    // List of coeefiicients, starting by the highest order
    public Rational(Function numerator, Function denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.function = (x) -> numerator.apply(x) / denominator.apply(x);

    }

    public Rational(String functionName, String varName, Function numerator, Function denominator) {
        this(numerator, denominator);
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
        return "\\frac{" + numerator.toTex() + "}{" + denominator.toTex() + "}";
    }

    public String toString() {
        return name + "(" + var + ") = " + toTex();
    }

    @Override
    public Function prime() {
        return new Rational(name + "'", var,
                new Sum(new Product(denominator, numerator.prime()),
                        new Product(new Constant(-1), numerator, denominator.prime())),
                new Composite(new Monomial(1, 2), denominator));
    }

}
