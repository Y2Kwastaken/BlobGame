package sh.miles.algidle.computer;

import org.jspecify.annotations.NullMarked;
import sh.miles.algidle.entity.Player;
import sh.miles.algidle.utils.Ticking;
import sh.miles.algidle.utils.TimeUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Algorithm Algorithm constant Algorithm level Algorithm Time Takes // refresh this every time an upgrade is applied to
 * the computer Computer level Random (a, b) -> variance on time
 */
@NullMarked
public class Computer implements Ticking {
    public static final int CONSTANT = 3;

    private final Algorithm algorithm;
    private final Player owner;
    private BigDecimal algorithmLevel;
    private int computerLevel;
    private int processTime; // ticks
    private BigO timeComplexity;

    private int ticked = 0;

    public Computer(final Algorithm algorithm, final Player owner) {
        this.algorithm = algorithm;
        this.owner = owner;
        this.algorithmLevel = BigDecimal.valueOf(1);
        this.computerLevel = 1;
        this.timeComplexity = algorithm.maxComplexity();
        this.processTime = TimeUtils.milliSecondsToTicks(algorithm.runtime().compute(algorithmLevel, CONSTANT, timeComplexity));
    }

    public void upgradeAlgorithm(int amount) {
        this.algorithmLevel = algorithmLevel.add(BigDecimal.valueOf(0.1).multiply(BigDecimal.valueOf(amount)));
        this.processTime = TimeUtils.milliSecondsToTicks(this.algorithm.runtime().compute(this.algorithmLevel, CONSTANT, this.timeComplexity));
    }

    public void upgradeComputer(int amount) {
        this.computerLevel = computerLevel + amount;
        // make a function to change processtime with new comp level
        this.processTime = TimeUtils.milliSecondsToTicks(this.algorithm.runtime().compute(this.algorithmLevel, CONSTANT, this.timeComplexity));
    }

    public BigDecimal getComputerUpgradeCost(int computerLevel, BigO timeFunction) {
        // update function to depend on amount of upgrades wishing to be purchased
        return BigDecimal.valueOf(Math.pow(Math.E, computerLevel)).multiply(timeFunction.operate(BigDecimal.valueOf(computerLevel)));
    }

    public BigDecimal getAlgorithmUpgradeCost(BigDecimal algorithmLevel, BigO timeFunction) {
        // change this function
        return BigDecimal.valueOf(Math.pow(Math.E, algorithmLevel.doubleValue())).multiply(timeFunction.operate(BigDecimal.valueOf(computerLevel)));
    }

    private BigDecimal amountBalanceIncrease(BigO timeFunction, Algorithm algorithm, BigDecimal algorithmLevel, int computerLevel) {
        return timeFunction.operate(algorithmLevel).multiply(BigDecimal.valueOf(algorithm.algorithmMult())).multiply(BigDecimal.valueOf(computerLevel));
    }

    @Override
    public void tick() {
        ticked++;

        if (ticked % processTime != 0) {
            return;
        }

        this.owner.incrementBalance(amountBalanceIncrease(getTimeComplexity(), getAlgorithm(), getAlgorithmLevel(), getComputerLevel()).doubleValue());
        ticked = 0;
    }

    public Player getOwner() {
        return owner;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public BigO getTimeComplexity() { return timeComplexity;}

    public int getComputerLevel() { return computerLevel;}

    public BigDecimal getAlgorithmLevel() { return algorithmLevel;}

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final Computer computer)) return false;
        return computerLevel == computer.computerLevel && processTime == computer.processTime && ticked == computer.ticked && Objects.equals(algorithm, computer.algorithm) && Objects.equals(owner, computer.owner) && Objects.equals(algorithmLevel, computer.algorithmLevel) && timeComplexity == computer.timeComplexity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(algorithm, owner, algorithmLevel, computerLevel, processTime, timeComplexity, ticked);
    }

    @Override
    public String toString() {
        return "Computer{" +
                "algorithm=" + algorithm +
                ", owner=" + owner +
                ", algorithmLevel=" + algorithmLevel +
                ", computerLevel=" + computerLevel +
                ", processTime=" + processTime +
                ", timeComplexity=" + timeComplexity +
                ", ticked=" + ticked +
                '}';
    }
}
