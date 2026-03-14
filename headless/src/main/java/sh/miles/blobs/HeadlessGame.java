package sh.miles.blobs;

import com.badlogic.gdx.math.Vector2;
import sh.miles.blobs.api.entity.EntityType;
import sh.miles.blobs.api.event.GameEvents;
import sh.miles.blobs.api.level.TileType;
import sh.miles.blobs.common.Unit;
import sh.miles.blobs.common.attribute.Lifecycle;
import sh.miles.blobs.entity.EntityComponents;
import sh.miles.blobs.entity.component.CombatComponent;
import sh.miles.blobs.entity.component.HealthComponent;
import sh.miles.blobs.entity.component.VelocityComponent;
import sh.miles.blobs.level.Level;
import sh.miles.blobs.network.local.GameLocalListener;

public class HeadlessGame implements Lifecycle {

    private Level level;

    @Override
    public void start() {
        GameEvents.call(GameEvents.START, Unit.INSTANCE);
        this.level = new Level(100, 100, (tiles) -> {
            int grass = TileType.GRASS.ordinal();
            for (int i = 0; i < tiles.length; i++) {
                int[] row = tiles[i];
                for (int i1 = 0; i1 < row.length; i1++) {
                    row[i1] = grass;
                }
            }
        });

        this.level.spawnEntity(EntityType.HUMANOID, 2, 2, (player) -> {
            player.set(EntityComponents.MOVEMENT_SPEED, 10f);
            player.set(EntityComponents.VELOCITY, new VelocityComponent(new Vector2(), 0.2f));
            player.set(EntityComponents.COMBAT, new CombatComponent(1.0f, 1.0f, 20, 0, 100, 0, false));
            player.set(EntityComponents.HEALTH, new HealthComponent(1.0f, 1.0f));
        });

        GameLocalListener.registerAllLocalListeners(level);
    }

    @Override
    public void tick() {
        GameEvents.call(GameEvents.TICK_START, Unit.INSTANCE);
        this.level.tick();
        GameEvents.call(GameEvents.TICK_END, Unit.INSTANCE);
    }

    @Override
    public void stop() {
        GameEvents.call(GameEvents.STOP, Unit.INSTANCE);
    }
}
