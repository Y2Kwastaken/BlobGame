package sh.miles.algidle.command.impl;

import org.jspecify.annotations.NullMarked;
import sh.miles.algidle.command.Command;
import sh.miles.algidle.computer.Algorithm;
import sh.miles.algidle.computer.Computer;
import sh.miles.algidle.entity.Player;
import sh.miles.algidle.registry.BuiltInRegistries;
import sh.miles.algidle.registry.Registries;
import sh.miles.algidle.utils.collection.registry.Holder;
import sh.miles.algidle.utils.collection.registry.Registry;
import sh.miles.algidle.utils.collection.registry.RegistryKey;

import java.util.stream.Collectors;

@NullMarked
public class ComputerCommand implements Command {
    @Override
    public void execute(final Player player, final String name, final String[] arguments) {
        if (arguments.length < 2) {
            System.out.println("computer <add:remove:peek:upgrade> <algorithm> <:::amount>");
            return;
        }

        final String function = arguments[0];
        final Registry<Algorithm> registry = Registries.getOrThrow(Registries.ALGORITHMS);
        final Holder<Algorithm> holder = registry.get(RegistryKey.base(arguments[1]));
        if (!holder.isPresent()) {
            System.out.println("Invalid algorithm " + arguments[1]);
            System.out.println("Valid Algorithms " + registry.keySet().stream().map(RegistryKey::location).collect(Collectors.joining(",")));
            return;
        }
        final Algorithm algorithm = holder.unwrap();

        switch (function) {
            case "add" -> {
                player.addComputer(new Computer(algorithm, player));
            }
            case "remove" -> {
                player.removeComputer(algorithm);
            }
            case "peek" -> {
                System.out.println("PEEKING COMPUTERS");
                System.out.println("==================================================");
                for (final Computer computer : player.getComputers(algorithm)) {
                    System.out.println(computer);
                }
                System.out.println("==================================================");
            }
            case "upgrade" -> {
                if (arguments.length < 4) {
                    System.out.println("computer <upgrade> <algorithm> <amount> <computer:algorithm>");
                    return;
                }
                var amount = 1;
                amount = Integer.parseInt(arguments[2]);
                if (arguments[3].equals("computer")) {
                    for (final Computer computer : player.getComputers(algorithm)) {
                        System.out.println(computer.getComputerUpgradeCost(computer.getComputerLevel(), computer.getTimeComplexity()));
                        if (player.getBalance().compareTo(computer.getComputerUpgradeCost(computer.getComputerLevel(), computer.getTimeComplexity())) < 0) {
                            System.out.println("Not enough money to upgrade" + "\n" + "You have (" + player.getBalance() + ") and the computer upgrade costs (" + computer.getComputerUpgradeCost(computer.getComputerLevel(), computer.getTimeComplexity()) + ")");
                            return;
                        }
                    }

                    for (final Computer computer : player.getComputers(algorithm)) {
                        computer.upgradeComputer(amount);
                        System.out.printf("Upgraded %s computer by %d%n", computer.getAlgorithm().name(), amount);
                    }
                } else if (arguments[3].equals("algorithm")) {
                    for (final Computer computer : player.getComputers(algorithm)) {
                        System.out.println(computer.getAlgorithmUpgradeCost(computer.getAlgorithmLevel(), computer.getTimeComplexity()));
                        if (player.getBalance().compareTo(computer.getAlgorithmUpgradeCost(computer.getAlgorithmLevel(), computer.getTimeComplexity())) < 0) {
                            System.out.println("Not enough money to upgrade" + "\n" + "You have (" + player.getBalance() + ") and the computer upgrade costs (" + computer.getAlgorithmUpgradeCost(computer.getAlgorithmLevel(), computer.getTimeComplexity()) + ")");
                            return;
                        }
                    }

                    for (final Computer computer : player.getComputers(algorithm)) {
                        computer.upgradeAlgorithm(amount);
                        System.out.printf("Upgraded %s computer by %d%n", computer.getAlgorithm().name(), amount);
                    }
                }
            }

            default -> System.out.println("Unknown function of computer: " + function);
        }
    }
}
