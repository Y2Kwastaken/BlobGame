package sh.miles.algidle.computer;

import org.jspecify.annotations.NullMarked;

import java.math.BigDecimal;

/**
 * Algorithm Algorithm constant Algorithm level Algorithm Time Takes // refresh this every time an upgrade is applied to
 * the computer Computer level Random (a, b) -> variance on time
 */
@NullMarked
public class Computer {
    public static final int CONSTANT = 3;

    private final Algorithm algorithm;
    private BigDecimal algorithmLevel;
    private int computerLevel;
    private BigDecimal processTime; // ms
    private BigO timeComplexity;

    public Computer(final Algorithm algorithm) {
        this.algorithm = algorithm;
        this.algorithmLevel = BigDecimal.ONE;
        this.computerLevel = 1;
        this.timeComplexity = algorithm.maxComplexity();
        this.processTime = algorithm.runtime().compute(algorithmLevel, CONSTANT, timeComplexity);
    }


    public void upgradeAlgorithimLevel() {
        algorithmLevel = algorithmLevel.add(BigDecimal.valueOf(0.1));
    }

    public void upgradeComputerLevel() {
        computerLevel = computerLevel + 1;
    }


}
