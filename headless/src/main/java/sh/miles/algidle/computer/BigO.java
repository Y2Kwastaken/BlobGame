package sh.miles.algidle.computer;

import sh.miles.algidle.math.BigMath;

import java.math.BigInteger;
import java.util.function.Function;

public enum BigO {
    CONSTANT("O(1)", (BigInteger value) -> BigInteger.ONE),
    LOG_N("O(log n)", BigMath::log2),
    LINEAR("O(n)", (BigInteger value) -> value),
    N_LOG_N("O(nlog n", (BigInteger value) -> value.multiply(BigMath.log2(value))),
    N_SQUARED("O(n^2)", (BigInteger value) -> value.pow(2)),
    N_CUBED("O(n^3)", (BigInteger value) -> value.pow(3)),
    TWO_N("O(2^n)", (BigInteger value) -> BigInteger.TWO.pow(value.intValue())),
    FACTORIAL("O(n!)", BigMath::factorial);

    private final String name;
    private final Function<BigInteger, BigInteger> operation;

    BigO(String name, Function<BigInteger, BigInteger> operation) {
        this.name = name;
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public BigInteger operate(final BigInteger number) {
        return this.operation.apply(number);
    }
}
