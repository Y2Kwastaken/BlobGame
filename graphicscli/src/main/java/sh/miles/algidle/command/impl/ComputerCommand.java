package sh.miles.algidle.command.impl;

import org.jspecify.annotations.NullMarked;
import sh.miles.algidle.command.Command;
import sh.miles.algidle.computer.Algorithm;
import sh.miles.algidle.computer.Computer;
import sh.miles.algidle.entity.Player;
import sh.miles.algidle.registry.Registries;
import sh.miles.algidle.utils.collection.registry.Holder;
import sh.miles.algidle.utils.collection.registry.RegistryKey;

@NullMarked
public class ComputerCommand implements Command {
    @Override
    public void execute(final Player player, final String name, final String[] arguments) {
        if (arguments.length < 2) {
            System.out.println("computer <add:remove:peek:upgrade> <algorithm>");
            return;
        }

        final String function = arguments[0];
        final Holder<Algorithm> holder = Registries.ALGORITHMS.get(RegistryKey.base(arguments[1]));
        if (!holder.isPresent()) {
            System.out.println("Invalid algorithm " + arguments[1]);
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
                var amount = 1;
                if (arguments.length == 3) {
                    amount = Integer.parseInt(arguments[2]);
                }
                for (final Computer computer : player.getComputers(algorithm)) {
                    computer.upgradeAlgorithm(amount);
                    System.out.printf("Upgraded %s computer by %d%n", computer.getAlgorithm().name(), amount);
                }
            }
            default -> System.out.println("Unknown function of computer: " + function);
        }
    }
}
