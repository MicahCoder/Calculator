package functions;

import java.util.function.DoubleFunction;

public class Misc implements Function {
    private final DoubleFunction<Double> function;
    private String name = "f";
    private String var = "x";
    private final String funcName;
    private final String latexForm;

    // List of coeefiicients, starting by the highest order
    public Misc(String funcName) {
        this.funcName = funcName;
        switch (funcName) {
            case "sin":
                this.function = (x) -> Math.sin(x);
                this.latexForm = "\\sin";
                break;
            case "cos":
                this.function = (x) -> Math.cos(x);
                this.latexForm = "\\cos";
                break;
            case "tan":
                this.function = (x) -> Math.tan(x);
                this.latexForm = "\\tan";
                break;
            case "asin":
                this.function = (x) -> Math.asin(x);
                this.latexForm = "\\sin^{-1}";
                break;
            case "acos":
                this.function = (x) -> Math.acos(x);
                this.latexForm = "\\cos^{-1}";
                break;
            case "atan":
                this.function = (x) -> Math.atan(x);
                this.latexForm = "\\tan^{-1}";
                break;
            case "csc":
                this.function = (x) -> 1 / Math.sin(x);
                this.latexForm = "\\csc";
                break;
            case "sec":
                this.function = (x) -> 1 / Math.cos(x);
                this.latexForm = "\\sec";
                break;
            case "cot":
                this.function = (x) -> 1 / Math.tan(x);
                this.latexForm = "\\cot";
                break;
            case "ln":
                this.function = (x) -> Math.log(x);
                this.latexForm = "\\ln";
                break;
            case "sqrt":
                this.function = (x) -> Math.sqrt(x);
                this.latexForm = "\\sqrt";
                break;
            case "exp":
                this.function = (x) -> Math.exp(x);
                this.latexForm = "e^";
                break;
            default:
                this.function = (x) -> Math.sin(x);
                this.latexForm = "\\sin";
                ;
                break;
        }

    }

    public Misc(String functionName, String varName, String funcName) {
        this(funcName);
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
        return latexForm + "{" + var + "}";
    }

    public String toString() {
        return name + "(" + var + ") = " + toTex();
    }

    @Override
    public Function prime() {
        switch (funcName) {
            case "sin":
                return COS;
            case "cos":
                return new Composite(name + "'", var, new Constant(-1), SIN);
            case "tan":
                return new Composite(name + "'", var, new Monomial(1, 2), SEC);
            case "asin":
                return new Rational(name + "'", var, new Constant(1), new Composite(SQRT, new Polynomial(-1, 0, 1)));
            case "acos":
                return new Rational(name + "'", var, new Constant(-1), new Composite(SQRT, new Polynomial(-1, 0, 1)));
            case "atan":
                return new Rational(name + "'", var, new Constant(1),
                        new Sum(new Monomial(1, 2), new Constant(1)));
            case "csc":
                return new Product(name + "'", var, new Constant(-1), CSC, COT);
            case "sec":
                return new Product(name + "'", var, SEC, TAN);
            case "cot":
                return new Product(name + "'", var, new Constant(-1), new Composite(new Monomial(1, 2), CSC));
            case "ln":
                return new Rational(name + "'", var, new Constant(1), new Monomial(1, 1));
            case "sqrt":
                return new Rational(name + "'", var, new Constant(1), new Product(new Constant(2), SQRT));
            case "exp":
                return new Misc(name + "'", var, "exp");
            default:
                return new Misc("sin").prime();
        }
    }
}
