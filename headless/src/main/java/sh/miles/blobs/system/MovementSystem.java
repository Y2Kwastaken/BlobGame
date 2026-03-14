package sh.miles.blobs.system;

import com.badlogic.gdx.math.Vector2;
import sh.miles.blobs.common.math.Position;
import sh.miles.blobs.entity.Entity;
import sh.miles.blobs.entity.EntityComponents;
import sh.miles.blobs.entity.component.VelocityComponent;
import sh.miles.blobs.level.Level;

import java.util.List;

public final class MovementSystem {

    public static final MovementSystem SYSTEM = new MovementSystem();

    public void update(List<Entity> entities, Level level) {
        for (Entity entity : entities) {
            if (!entity.has(EntityComponents.VELOCITY)) continue;
            update(entity, level);
        }
    }

    private void update(Entity entity, Level level) {
        VelocityComponent velocityComponent = entity.get(EntityComponents.VELOCITY);
        Position position = entity.get(EntityComponents.POSITION);

        var combat = entity.getOrNull(EntityComponents.COMBAT);
        if (combat != null && combat.attackTimeLeft > 0) return;

        Vector2 velocity = velocityComponent.velocity;
        Vector2 stepVector = new Vector2(velocity);

        if (Math.abs(stepVector.x) > 0 && Math.abs(stepVector.y) > 0) {
            stepVector.scl((float) (1f / Math.sqrt(2f)));
        }

        Position newPosition = position.apply(stepVector);
        var hitboxComponent = entity.get(EntityComponents.HITBOX);
        var hitbox = hitboxComponent.bounds;

        hitbox.x = newPosition.x();
        hitbox.y = newPosition.y();

        if (level.isInside(hitboxComponent.getBoundsWithOffset())) {
            entity.set(EntityComponents.POSITION, newPosition);
        } else {
            hitbox.x = position.x();
            hitbox.y = position.y();
        }

        if (Math.abs(velocity.y) < 0.001f) velocity.y = 0;
        if (Math.abs(velocity.x) < 0.001f) velocity.x = 0;

        velocity.scl((float) Math.sqrt(velocityComponent.friction));
    }


}
