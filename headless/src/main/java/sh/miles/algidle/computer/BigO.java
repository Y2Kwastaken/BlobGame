package sh.miles.algidle.computer;

import sh.miles.algidle.math.BigMath;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;

public enum BigO {
    CONSTANT("O(1)", (BigDecimal value) -> BigDecimal.ONE),
    LOG_N("O(log n)", BigMath::log2),
    LINEAR("O(n)", (BigDecimal value) -> value),
    N_LOG_N("O(nlog n)", (BigDecimal value) -> value.multiply(BigMath.log2(value))),
    N_SQUARED("O(n^2)", (BigDecimal value) -> value.pow(2)),
    N_CUBED("O(n^3)", (BigDecimal value) -> value.pow(3)),
    TWO_N("O(2^n)", (BigDecimal value) -> BigDecimal.TWO.pow(value.intValue())),
    FACTORIAL("O(n!)", BigMath::factorial);

    private final String name;
    private final Function<BigDecimal, BigDecimal> operation;

    BigO(String name, Function<BigDecimal, BigDecimal> operation) {
        this.name = name;
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public BigDecimal operate(final BigDecimal number) {
        return this.operation.apply(number);
    }
}
