import functions.Composite;
import static functions.Function.*;
import functions.Monomial;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.DoubleFunction;

public class Main {
    public static void main(String[] args) {
        DoubleFunction<Double> f = x -> Math.sqrt(4 - x * x);
        double a = -50;
        double b = 50;
        int steps = 10000000;

        writeToTexFile("src/outputTex/output.tex", Calculator.evaluateIntegralTex(
                new Composite("f", "x", ETOX, new Monomial(-1, 2)),
                a, b, steps, "t"));
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