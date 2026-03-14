package sh.miles.blobs.network.local;

import sh.miles.blobs.api.dto.client.ClientDebugDTO;
import sh.miles.blobs.api.dto.client.ClientEntityDTO;
import sh.miles.blobs.api.entity.EntityType;
import sh.miles.blobs.api.event.ClientEvents;
import sh.miles.blobs.api.event.GameEvents;
import sh.miles.blobs.common.math.Position;
import sh.miles.blobs.entity.Entity;
import sh.miles.blobs.entity.EntityComponents;
import sh.miles.blobs.entity.component.HealthComponent;
import sh.miles.blobs.entity.component.OrientationComponent;
import sh.miles.blobs.entity.component.VelocityComponent;
import sh.miles.blobs.level.Level;
import sh.miles.blobs.system.CombatSystem;

public class GameLocalListener {

    public static void registerAllLocalListeners(Level level) {
        GameEvents.INSTANCE.registerListener(ClientEvents.PLAYER_INPUT, (input) -> handlePlayerInput(level, input));
        GameEvents.INSTANCE.registerListener(ClientEvents.PLAYER_DEBUG, (input) -> handlePlayerDebug(level, input));
    }

    public static void handlePlayerInput(Level level, ClientEntityDTO.EntityInputDTO input) {
        Entity player = level.getEntity(input.id());
        if (player == null) return;

        if (input.moveX() != 0 || input.moveY() != 0) {
            OrientationComponent orientation = player.getOrNull(EntityComponents.ORIENTATION);
            if (orientation != null) {
                orientation.facingX = input.moveX();
                orientation.facingY = input.moveY();
            }
        }

        float distancePerTick = player.get(EntityComponents.MOVEMENT_SPEED) / 50f;
        VelocityComponent velo = player.get(EntityComponents.VELOCITY);
        velo.velocity.x = input.moveX() * distancePerTick;
        velo.velocity.y = input.moveY() * distancePerTick;

        if (input.isAttacking()) {
            CombatSystem.SYSTEM.tryQueueAttackForNextTick(player);
        }
    }

    public static void handlePlayerDebug(Level level, ClientDebugDTO.PlayerDebugDTO debug) {
        Entity player = level.getEntity(debug.id());
        if (player == null) return;

        final Position position = player.get(EntityComponents.POSITION);
        if (debug.spawnSlime()) {
            level.spawnEntity(EntityType.SLIME, position.x(), position.y(), (entity) -> {
                entity.set(EntityComponents.HEALTH, new HealthComponent(1.0f));
            });
        }

        if (debug.cloneSelf()) {
            level.spawnEntity(EntityType.HUMANOID, position.x(), position.y(), player::copyTo);
        }
    }

}
