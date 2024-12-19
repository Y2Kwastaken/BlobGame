package sh.miles.algidle.computer;

import java.util.function.Function;

public record Computers(String string, Equation equation, int constant, BigO timeComplexity) {
    public Computers withName(String string) {
        return new Computers(string, equation, constant, timeComplexity);
    }

    public Computers withEquation(Equation equation) {
        return new Computers(string, equation, constant, timeComplexity);
    }

    public Computers withConstant(int constant) {
        return new Computers(string, equation, constant, timeComplexity);
    }

    public Computers withTimeComplexity(BigO timeComplexity) {
        return new Computers(string, equation, constant, timeComplexity);
    }

    public static void main(String[] args) {

        Function<Integer, Integer> var1 = (Integer val) -> 2;

        Computers computer = new Computers("computer1", new Equation("LinearEq", var1), 5, BigO.LINEAR);
        System.out.println(computer);
        Computers computer1 = new Computers("computer1", new Equation("LinearEq", var1 ), 5, BigO.LINEAR);
        System.out.println(computer == computer1);
    }

}
