import functions.Composite;
import functions.Function;
import static functions.Function.EXP;
import static functions.Function.SIN;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // DoubleFunction<Double> f = x -> Math.sqrt(4 - x * x);
        // double a = -1;
        // double b = 1;
        // int steps = 100000;

        // writeToTexFile("src/outputTex/output.tex", Calculator.evaluateIntegralTex(
        // new Composite("f", "x", ETOX, new Monomial(-1, 2)),
        // a, b, steps, "l"));
        Function f = new Composite(EXP, SIN);
        System.out.println(f.toString() + "\n" + f.prime().toString());
        System.out.println(f.prime().apply(5.0));
        writeToTexFile("src/outputTex/output.tex", "$$" + f.toString() + "$$ \\\\ $$" + f.prime().toString() + "$$");
        System.out.println("Done");
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