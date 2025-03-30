import functions.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.DoubleFunction;

public class Main {
    public static void main(String[] args) {
        DoubleFunction<Double> f = x -> Math.sqrt(4 - x * x);
        double a = 0.0;
        double b = 2;
        int steps = 10000000;

        // System.out.println("f(x) = sqrt(4-x^2)");
        // System.out.println(Calculator.evaluateIntegral(f, a, b, steps, "R"));
        Function polynomial = new Polynomial(1.5, 0, 0, 5);
        Function gofx = new CustomFunction("\\sqrt{4-x^2}", (x) -> Math.sqrt(4 - x * x));
        Function eToX = new Exponential("e", Math.E);
        Function monomial = new Monomial(1.5, 0);

        Function product = new Product(gofx, eToX);
        // System.out.println(polynomial);
        // System.out.println(polynomial.toTex());
        writeToTexFile("src/outputTex/output.tex",
                Calculator.evaluateIntegralTex(new Sum(monomial, new Rational(polynomial, product)), a, b, steps, "l"));
    }

    public static void writeToFile(String filePath, String text) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(text);
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public static void writeToTexFile(String filePath, String text) {
        writeToFile(filePath, "\\documentclass{article}\n\\begin{document}\n" + text + "\n\\end{document}");
    }
}