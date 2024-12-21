package sh.miles.algidle.command.impl;

import org.jspecify.annotations.NullMarked;
import sh.miles.algidle.command.Command;
import sh.miles.algidle.entity.Player;

@NullMarked
public class PlayerCommand implements Command {
    @Override
    public void execute(final Player player, final String name, final String[] arguments) {
        if (arguments.length != 1) {
            System.out.println("player <balance>");
            return;
        }

        System.out.println("Balance: " + player.getBalance());
    }
}
