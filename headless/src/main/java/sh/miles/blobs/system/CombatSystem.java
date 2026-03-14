package sh.miles.blobs.system;

import sh.miles.blobs.api.dto.game.GameDebugDTO;
import sh.miles.blobs.api.event.GameEvents;
import sh.miles.blobs.common.math.BoundingBox;
import sh.miles.blobs.common.math.Position;
import sh.miles.blobs.entity.Entity;
import sh.miles.blobs.entity.EntityComponents;
import sh.miles.blobs.entity.component.CombatComponent;
import sh.miles.blobs.entity.component.HealthComponent;
import sh.miles.blobs.entity.component.OrientationComponent;
import sh.miles.blobs.level.Level;

import java.util.List;

public final class CombatSystem {

    public static final CombatSystem SYSTEM = new CombatSystem();

    public void update(List<Entity> entities, Level level) {
        for (final Entity entity : entities) {
            update(level, entity);
        }
    }

    private void update(Level level, Entity attacker) {
        final CombatComponent combat = attacker.getOrNull(EntityComponents.COMBAT);
        if (combat == null) return;

        if (combat.attackTimeLeft > 0) {
            combat.attackTimeLeft--;
        }

        if (combat.attackCooldownTicksLeft > 0) {
            combat.attackCooldownTicksLeft--;
            return;
        }

        if (!combat.willStartAttack) {
            return;
        }

        combat.willStartAttack = false;
        combat.attackTimeLeft = combat.attackDuration;
        combat.attackCooldownTicksLeft = combat.attackCooldown;

        float range = combat.attackRange;
        final OrientationComponent orientation = attacker.getOrNull(EntityComponents.ORIENTATION);
        if (orientation == null) {
            return;
        }

        float strikeRangeX = orientation.facingX * range;
        float strikeRangeY = orientation.facingY * range;
        float boxX = Math.min(0, strikeRangeX);
        float boxY = Math.min(0, strikeRangeY);

        float boxWidth = Math.abs(strikeRangeX);
        float boxHeight = Math.abs(strikeRangeY);

        float attackThickness = 0.75f;

        if (boxWidth == 0) {
            boxWidth = attackThickness;
            boxX -= attackThickness / 2f;
        }
        if (boxHeight == 0) {
            boxHeight = attackThickness;
            boxY -= attackThickness / 2f;
        }

        final Position position = attacker.get(EntityComponents.POSITION);
        final BoundingBox box = new BoundingBox(position.x() + boxX, position.y() + boxY, boxWidth, boxHeight);

        GameEvents.call(GameEvents.ATTACK_DEBUG, new GameDebugDTO.AttackBoundsDTO(attacker.id, box.x, box.y, box.width, box.height));

        final var entities = level.getEntitiesInside(box, (e) -> e.has(EntityComponents.HEALTH) && e != attacker);
        for (final Entity victim : entities) {
            HealthComponent health = victim.get(EntityComponents.HEALTH);
            health.health -= combat.attackDamage;
            if (health.health <= 0) {
                level.despawn(victim);
            }
        }
    }

    public void tryQueueAttackForNextTick(Entity entity) {
        final CombatComponent component = entity.getOrNull(EntityComponents.COMBAT);
        if (component == null || component.attackCooldownTicksLeft != 0 || component.willStartAttack) return;
        component.willStartAttack = true;
    }
}
