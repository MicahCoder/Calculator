package functions;

import java.util.function.DoubleFunction;

public class Braces implements Function {
    private final DoubleFunction<Double> function;
    private String name = "f";
    private String var = "x";
    private final String latexForm;

    // List of coeefiicients, starting by the highest order
    public Braces(String funcName) {
        switch (funcName) {
            case "abs" -> {
                this.function = (x) -> Math.abs(x);
                this.latexForm = "\\left|" + var + "\\right|";
            }
            case "floor" -> {
                this.function = (x) -> Math.floor(x);
                this.latexForm = "\\left\\lfloor " + var + "\\right\\rfloor";
            }
            case "ceil" -> {
                this.function = (x) -> Math.ceil(x);
                this.latexForm = "\\left\\lceil " + var + "\\right\\rceil";
            }
            default -> {
                this.function = (x) -> Math.abs(x);
                this.latexForm = "|" + var + "|";
            }
        }

    }

    public Braces(String functionName, String varName, String funcName) {
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
        return latexForm;
    }

    @Override
    public String toString() {
        return name + "(" + var + ") = " + toTex();
    }

    @Override
    public Function prime() {
        throw new UnsupportedOperationException("Braces function does not have a derivative.");
    }
}
