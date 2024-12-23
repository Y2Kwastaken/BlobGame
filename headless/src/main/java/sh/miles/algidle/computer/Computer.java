package sh.miles.algidle.computer;

import org.jspecify.annotations.NullMarked;
import sh.miles.algidle.entity.Player;
import sh.miles.algidle.utils.Ticking;
import sh.miles.algidle.utils.TimeUtils;
import sh.miles.algidle.utils.collection.registry.RegistryKey;
import sh.miles.algidle.utils.math.BigMath;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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
    private int counter = 0;
    private double temp = 0;
    private double timeElapsed = 0;
    private UUID uuid = UUID.randomUUID();

    public static final Map<RegistryKey, BigDecimal> computerStats = new HashMap<>();
    public static Statistics computerStatistics = new Statistics(computerStats);

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
        // process time changes by: ProcessTime*(1-computerlevel/100)
        this.processTime = TimeUtils.milliSecondsToTicks(this.algorithm.runtime().compute(this.algorithmLevel, CONSTANT, this.timeComplexity).multiply(BigDecimal.ONE.subtract(BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP))));
    }
    public BigDecimal getComputerUpgradeCost(int computerLevel, BigO timeFunction, int amount) {
        BigDecimal cost = BigDecimal.ZERO;
        for (int i = 0; i < amount; i++) {
            cost = cost.add(BigDecimal.valueOf(Math.pow(Math.E, computerLevel+i)).multiply(timeFunction.operate(BigDecimal.valueOf(computerLevel))));
        }
        return cost.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
    }
    // change this function
    public BigDecimal getAlgorithmUpgradeCost(BigDecimal algorithmLevel, BigO timeFunction, int amount) {
        BigDecimal cost = BigDecimal.ZERO;
        for (int i = 0; i < amount; i++) {
            cost = cost.add(BigDecimal.valueOf(Math.pow(Math.E, algorithmLevel.doubleValue()+i)).multiply(timeFunction.operate(BigDecimal.valueOf(computerLevel))));
        }
        return cost.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
    }
    // change the power of 10^x, dependent on the type of algorithm
    private static BigDecimal amountBalanceIncrease(BigO timeFunction, Algorithm algorithm, BigDecimal algorithmLevel, int computerLevel) {
        return timeFunction.operate(algorithmLevel).multiply(BigDecimal.valueOf(algorithm.algorithmMult())).multiply(BigDecimal.valueOf(computerLevel)).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
    }

    @Override
    public void tick() {
        ticked++;

        if (ticked % processTime != 0) {
            return;
        }

        this.owner.incrementBalance(amountBalanceIncrease(getTimeComplexity(), getAlgorithm(), getAlgorithmLevel(), getComputerLevel()).doubleValue());
        computerStatistics.incrementStatistic(RegistryKey.base("totalProfit"), BigDecimal.valueOf(amountBalanceIncrease(getTimeComplexity(), getAlgorithm(), getAlgorithmLevel(), getComputerLevel()).doubleValue()));
        computerStatistics.incrementStatistic(RegistryKey.base("totalProfit" + this.getAlgorithm()), BigDecimal.valueOf(amountBalanceIncrease(this.getTimeComplexity(), this.getAlgorithm(), this.getAlgorithmLevel(), this.getComputerLevel()).doubleValue()));
        computerStatistics.incrementStatistic(RegistryKey.base("totalProfit" + uuid), BigDecimal.valueOf(amountBalanceIncrease(this.getTimeComplexity(), this.getAlgorithm(), this.getAlgorithmLevel(), this.getComputerLevel()).doubleValue()));
        /*
        counter += 1;
        temp += amountBalanceIncrease(getTimeComplexity(), getAlgorithm(), getAlgorithmLevel(), getComputerLevel()).doubleValue();
        timeElapsed += TimeUtils.ticksToSeconds(this.processTime).doubleValue();
        ticked = 0;
        if (counter % 10 != 0 ) {
            return;
        }
        System.out.printf("You have made %s in %s seconds%n", BigMath.round(temp,4), BigMath.round(timeElapsed, 4));
        System.out.printf("You are making %s per second", BigMath.round(temp,4)/BigMath.round(timeElapsed, 4));
        counter = 0;
        temp = 0;
        timeElapsed = 0;
         */
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

    public UUID getUuid() { return uuid;}

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
