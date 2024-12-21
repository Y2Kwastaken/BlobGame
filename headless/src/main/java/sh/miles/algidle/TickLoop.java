package sh.miles.algidle;

import sh.miles.algidle.entity.Player;
import sh.miles.algidle.utils.Ticking;

public class TickLoop implements Ticking {

    public final Player player;

    TickLoop() {
        this.player = new Player();
    }

    @Override
    public void tick() {
        player.tick();
    }
}
