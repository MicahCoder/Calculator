package functions;

import java.util.function.DoubleFunction;

public interface Function {
    public static final Function SIN = new Misc("sin");
    public static final Function COS = new Misc("cos");
    public static final Function TAN = new Misc("tan");
    public static final Function ASIN = new Misc("asin");
    public static final Function ACOS = new Misc("acos");
    public static final Function ATAN = new Misc("atan");
    public static final Function CSC = new Misc("csc");
    public static final Function SEC = new Misc("sec");
    public static final Function COT = new Misc("cot");
    public static final Function LN = new Misc("ln");
    public static final Function ETOX = new Exponential("e", Math.E);

    public default DoubleFunction<Double> getFunction() {
        return (x) -> 0.0;
    }

    public default String toTex() {
        return "f(x) = 0";
    }

    public default double apply(double x) {
        return 0.0;
    }

    public default String getVar() {
        return "x";
    }

    public default String getName() {
        return "f";
    }

    public default String doubleToString(double num) {
        return (int) num == num ? Integer.toString((int) num)
                : Double.toString(num);
    }

    public default String doubleToString(double num, String mode) {
        switch (mode) {
            case "p":
                if (num == 1) {
                    return "";
                }
                if (num == -1) {
                    return "-";
                }
                return doubleToString(num);
            case "s":
                if (num == 0) {
                    return "";
                }
                return doubleToString(num);
            default:
                return doubleToString(num);
        }
    }
}
