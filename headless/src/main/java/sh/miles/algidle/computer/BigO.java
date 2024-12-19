package sh.miles.algidle.computer;

import sh.miles.algidle.utils.ExtendedMath;

import java.util.function.Function;

public enum BigO {
    CONSTANT("O(1)", (Integer value) -> 1),
    LOGN("O(log n)", (Integer value) -> (int) Math.log(value)),
    LINEAR("O(n)", (Integer value) -> value),
    NLOGN("O(nlog n", (Integer value) -> value*((int) Math.log(value))),
    NSQUARED("O(n^2)", (Integer value) -> (int) Math.pow(value, 2)),
    TWON("O(2^n)", (Integer value) -> (int) Math.pow(2, value)),
    NFACTORIAL("O(n!)", (Integer value) -> ExtendedMath.factorial(value));

    private final String name;
    private final Function<Integer, Integer> operation;


    BigO(String name, Function<Integer, Integer> operation) {
        this.name = name;
        this.operation = operation;
    }
}
