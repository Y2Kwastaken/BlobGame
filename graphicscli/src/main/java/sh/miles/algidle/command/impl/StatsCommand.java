package sh.miles.algidle.command.impl;

import org.jspecify.annotations.NullMarked;
import sh.miles.algidle.command.Command;
import sh.miles.algidle.computer.Algorithm;
import sh.miles.algidle.computer.Statistics;
import sh.miles.algidle.entity.Player;
import sh.miles.algidle.computer.Computer;
import sh.miles.algidle.utils.collection.registry.RegistryKey;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@NullMarked
public class StatsCommand implements Command {
    @Override
    public void execute(final Player player, final String name, final String[] arguments) {
        long totalSecs = Computer.computerStatistics.getStatistic(RegistryKey.base("totalTimeElapsed")).longValue();

        System.out.println("Total Profit: " + Computer.computerStatistics.getStatistic(RegistryKey.base("totalProfit")));
        System.out.println("Total Profit (by algorithm):");
        for (final Algorithm algorithm : player.getComputers().stream().map(computer -> computer.getAlgorithm()).collect(Collectors.toSet())) {
            System.out.println("\tTotal Profit from " + algorithm.name() + ": " + Computer.computerStatistics.getStatistic(RegistryKey.base("totalProfit" + algorithm)));

        }
        System.out.println("Total Profit (by computer):");
        int index = 1;
        for (final Computer computer : player.getComputers()) {
            System.out.println("\tTotal Profit from computer " + index + ": " + Computer.computerStatistics.getStatistic(RegistryKey.base("totalProfit" + computer.getUuid())));
            index++;
        }
        System.out.println(
            "---------------------------"
        + "\n"
            + "Total upgrades purchased: " + Computer.computerStatistics.getStatistic(RegistryKey.base("totalUpgradesPurchased"))
        + "\n"
            + "Total computer upgrades purchased: " + Computer.computerStatistics.getStatistic(RegistryKey.base("totalComputerUpgradesPurchased"))
        + "\n"
            + "Total algorithm upgrades purchased: " + Computer.computerStatistics.getStatistic(RegistryKey.base("totalAlgorithmUpgradesPurchased"))
        + "\n"
            + "---------------------------"
        + "\n"
            + "Total Time Elapsed: " + totalSecs / 3600 + "hours " + totalSecs % 3600 / 60 + "mins " + totalSecs % 60 +"secs"
        );


    }
}
