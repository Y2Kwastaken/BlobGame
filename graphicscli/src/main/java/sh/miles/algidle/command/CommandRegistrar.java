package sh.miles.algidle.command;

import sh.miles.algidle.command.impl.ComputerCommand;
import sh.miles.algidle.command.impl.PlayerCommand;
import sh.miles.algidle.command.impl.StopCommand;
import sh.miles.algidle.command.impl.TPSCommand;
import sh.miles.algidle.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistrar {

    public static final CommandRegistrar INSTANCE = new CommandRegistrar();

    private final Map<String, Command> commands = new HashMap<>();

    private CommandRegistrar() {
        commands.put("stop", new StopCommand());
        commands.put("computer", new ComputerCommand());
        commands.put("player", new PlayerCommand());
        commands.put("tps", new TPSCommand());
    }

    public void register(String name, Command command) {
        this.commands.put(name, command);
    }

    public void dispatch(Player player, String name, String[] arguments) {
        final Command toDispatch = this.commands.get(name);
        if (toDispatch == null) {
            return;
        }

        toDispatch.execute(player, name, arguments);
    }

    public void dispatch(Player player, String input) {
        final String[] split = input.split(" ");
        if (split.length == 1) {
            dispatch(player, split[0], new String[0]);
            return;
        }

        final String[] arguments = new String[split.length - 1];
        System.arraycopy(split, 1, arguments, 0, arguments.length);

        dispatch(player, split[0], arguments);
    }

}
