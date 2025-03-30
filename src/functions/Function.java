package functions;

import java.util.function.DoubleFunction;

public interface Function {

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
