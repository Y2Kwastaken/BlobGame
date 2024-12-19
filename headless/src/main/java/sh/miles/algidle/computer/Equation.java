package sh.miles.algidle.computer;

import java.util.function.Function;

public record Equation(String name, Function<Integer, Integer> time) {
}
