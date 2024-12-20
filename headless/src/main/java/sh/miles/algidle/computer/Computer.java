package sh.miles.algidle.computer;

public record Computer(String string, Equation equation, int constant, BigO timeComplexity) {
    public Computer withName(String string) {
        return new Computer(string, equation, constant, timeComplexity);
    }

    public Computer withEquation(Equation equation) {
        return new Computer(string, equation, constant, timeComplexity);
    }

    public Computer withConstant(int constant) {
        return new Computer(string, equation, constant, timeComplexity);
    }

    public Computer withTimeComplexity(BigO timeComplexity) {
        return new Computer(string, equation, constant, timeComplexity);
    }
}
