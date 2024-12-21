package sh.miles.algidle;

import sh.miles.algidle.command.CommandRegistrar;
import sh.miles.algidle.entity.Player;

import java.util.Scanner;

public class MainCommandLine {

    public static final HeadlessGame GAME = HeadlessGame.GAME;
    public static volatile boolean running = true;

    public static void main(String[] args) {
        GAME.start();
        final Player player = GAME.tickLoop.player;
        final Scanner scanner = new Scanner(System.in);
        while (running) {
            final String commandInput = scanner.nextLine();
            CommandRegistrar.INSTANCE.dispatch(player, commandInput);
        }
    }

}
