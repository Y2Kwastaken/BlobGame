package sh.miles.blobs.entity.component;

import com.badlogic.gdx.math.Vector2;
import sh.miles.blobs.entity.Entity;
import sh.miles.blobs.util.gdx.DeepVector2;

import java.util.function.UnaryOperator;

public record VelocityComponent(DeepVector2 velocity, float friction, VelocityDegradationMethod degrade) {
    public static VelocityDegradationMethod NATURAL_DEGRADE = (component) -> {
        final DeepVector2 vector2 = component.velocity().modify((v) -> {
            if (Math.abs(v.y) < 0.001f) v.y = 0;
            if (Math.abs(v.x) < 0.001f) v.x = 0;
            v.scl((float) Math.sqrt(component.friction));
        });
        return new VelocityComponent(vector2, component.friction(), component.degrade());
    };

    public VelocityComponent withVelocity(UnaryOperator<Vector2> velocityOperation) {
        return new VelocityComponent(new DeepVector2(velocityOperation.apply(velocity.vector())), friction, degrade);
    }

    public static void update(Entity entity) {
        final var level = entity.get(EntityComponents.LEVEL);
        if (level == null) return;
        final var position = entity.get(EntityComponents.POSITION);
        if (position == null) return;
        final var velocity = entity.get(EntityComponents.VELOCITY);
        if (velocity == null) return;

        final var vector = velocity.velocity.vector();
        if (Math.abs(vector.x) > 0 && Math.abs(vector.y) > 0) {
            vector.scl((float) (1f / Math.sqrt(2f)));
        }

        final var newPosition = position.apply(vector);
        if (!level.isInside(newPosition)) return;

        entity.set(EntityComponents.POSITION, newPosition);
        entity.set(EntityComponents.VELOCITY, velocity.degrade.degrade(velocity));
    }

    @FunctionalInterface
    public interface VelocityDegradationMethod {
        VelocityComponent degrade(VelocityComponent component);
    }
}
