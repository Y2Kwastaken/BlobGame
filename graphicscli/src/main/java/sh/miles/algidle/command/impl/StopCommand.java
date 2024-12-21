package sh.miles.algidle.command.impl;

import org.jspecify.annotations.NullMarked;
import sh.miles.algidle.HeadlessGame;
import sh.miles.algidle.MainCommandLine;
import sh.miles.algidle.command.Command;
import sh.miles.algidle.entity.Player;

@NullMarked
public class StopCommand implements Command {

    @Override
    public void execute(final Player player, final String name, final String[] arguments) {
        HeadlessGame.GAME.running = false;
        MainCommandLine.running = false;
        System.out.println("Shutting Down");
    }
}
