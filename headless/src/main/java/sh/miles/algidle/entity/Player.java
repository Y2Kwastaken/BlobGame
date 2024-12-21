package sh.miles.algidle.entity;

import org.jspecify.annotations.NullMarked;
import sh.miles.algidle.computer.Algorithm;
import sh.miles.algidle.computer.Computer;
import sh.miles.algidle.utils.Ticking;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NullMarked
public class Player implements Ticking {

    private final List<Computer> computers;
    private BigDecimal balance;

    public Player() {
        this.computers = new ArrayList<>();
        this.balance = BigDecimal.ZERO;
    }

    @Override
    public void tick() {
        for (final Computer computer : computers) {
            computer.tick();
        }
    }

    public void incrementBalance(double amount) {
        this.balance = this.balance.add(BigDecimal.valueOf(amount));
    }

    public void decrementBalance(double amount) {
        this.balance = this.balance.subtract(BigDecimal.valueOf(amount));
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Computer> getComputers() {
        return computers;
    }

    public List<Computer> getComputers(Algorithm algorithm) {
        final List<Computer> collector = new ArrayList<>();
        for (final Computer computer : this.computers) {
            if (computer.getAlgorithm().equals(algorithm)) {
                collector.add(computer);
            }
        }

        return computers;
    }

    public void addComputer(Computer computer) {
        this.computers.add(computer);
    }

    public void removeComputer(Algorithm algorithm) {
        this.computers.removeIf((computer) -> computer.getAlgorithm() == algorithm);
    }
}
