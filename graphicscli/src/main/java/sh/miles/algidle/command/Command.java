package sh.miles.algidle.command;

import org.jspecify.annotations.NullMarked;
import sh.miles.algidle.entity.Player;

@NullMarked
@FunctionalInterface
public interface Command {
    void execute(final Player player, final String name, final String[] arguments);
}
