package sh.miles.algidle.computer;

import com.google.common.math.BigDecimalMath;
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
            return BigDecimal.valueOf(Math.pow(Math.E, timeFunction.operate(BigDecimal.valueOf(constant)).subtract(upgradeValue).doubleValue()));
        };

        BigDecimal compute(BigDecimal upgradeValue, int constant, BigO timeFunction);
    }
}
