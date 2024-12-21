package sh.miles.algidle.computer;

import org.jspecify.annotations.NullMarked;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NullMarked
public record Algorithm(String name, BigO minComplexity, BigO maxComplexity, AlgorithmRuntime runtime) {

    public Algorithm(String name, BigO minComplexity, BigO maxComplexity) {
        this(name, minComplexity, maxComplexity, AlgorithmRuntime.BASE);
    }

    @FunctionalInterface
    public interface AlgorithmRuntime {
        AlgorithmRuntime BASE = (BigDecimal upgradeValue, int constant, BigO timeFunction) -> {
            return BigDecimal.valueOf(1.0 / Math.pow(Math.E, timeFunction.operate(upgradeValue).divide(BigDecimal.valueOf(constant), 4, RoundingMode.HALF_UP).doubleValue()) * (constant - 1.0));
        }

        BigDecimal compute(BigDecimal upgradeValue, int constant, BigO timeFunction);
    }
}
