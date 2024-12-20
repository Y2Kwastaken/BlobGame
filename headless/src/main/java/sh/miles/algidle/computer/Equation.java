package sh.miles.algidle.computer;

import java.math.BigInteger;
import java.util.function.Function;

public record Equation(String name, Function<BigInteger, BigInteger> time) {
}
