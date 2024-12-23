package sh.miles.algidle;

import sh.miles.algidle.computer.Computer;
import sh.miles.algidle.entity.Player;
import sh.miles.algidle.utils.Ticking;
import sh.miles.algidle.utils.TimeUtils;
import sh.miles.algidle.utils.collection.registry.RegistryKey;

import java.math.BigDecimal;

public class TickLoop implements Ticking {

    public final Player player;
    private int ticked = 0;

    TickLoop() {
        this.player = new Player();
    }

    @Override
    public void tick() {
        ticked ++;
        Computer.computerStatistics.setStatistic(RegistryKey.base("totalTimeElapsed"), TimeUtils.ticksToSeconds(ticked));
        player.tick();

    }
}
