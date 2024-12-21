package sh.miles.algidle.computer;

import org.jspecify.annotations.NullMarked;
import sh.miles.algidle.utils.Ticking;
import sh.miles.algidle.utils.TimeUtils;

import java.math.BigDecimal;

/**
 * Algorithm Algorithm constant Algorithm level Algorithm Time Takes // refresh this every time an upgrade is applied to
 * the computer Computer level Random (a, b) -> variance on time
 */
@NullMarked
public class Computer implements Ticking {
    public static final int CONSTANT = 3;

    private final Algorithm algorithm;
    private BigDecimal algorithmLevel;
    private int computerLevel;
    private int processTime; // ticks
    private BigO timeComplexity;

    private int ticked = 0;
    private int money = 0; // change this later

    public Computer(final Algorithm algorithm) {
        this.algorithm = algorithm;
        this.algorithmLevel = BigDecimal.valueOf(1);
        this.computerLevel = 1;
        this.timeComplexity = algorithm.maxComplexity();
        this.processTime = TimeUtils.milliSecondsToTicks(algorithm.runtime().compute(algorithmLevel, CONSTANT, timeComplexity));
    }

    public void upgradeAlgorithm(int amount) {
        this.algorithmLevel = algorithmLevel.add(BigDecimal.valueOf(0.1).multiply(BigDecimal.valueOf(amount)));
        this.processTime = TimeUtils.milliSecondsToTicks(this.algorithm.runtime().compute(this.algorithmLevel, CONSTANT, this.timeComplexity));
    }

    @Override
    public void tick() {
        if (ticked == Integer.MAX_VALUE) {
            ticked = 0;
        }

        ticked++;

        if (ticked % processTime != 0) {
            return;
        }
    }

}
