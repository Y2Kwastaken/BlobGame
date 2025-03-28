package sh.miles.blobs.entity;

import com.badlogic.gdx.math.Vector2;
import org.jspecify.annotations.Nullable;
import sh.miles.blobs.component.DataComponentType;
import sh.miles.blobs.entity.animation.EntityAnimationType;
import sh.miles.blobs.entity.components.EntityComponents;
import sh.miles.blobs.util.Ticking;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Entity implements Ticking {
    private static int nextId = 0;

    public final int id;
    private final Map<DataComponentType<?>, Object> components;

    public Entity() {
        this.id = nextId++;
        this.components = new HashMap<>(10);
    }

    @Nullable
    public <T> T get(DataComponentType<T> type) {
        return (T) this.components.get(type);
    }

    public <T> T getOrSet(DataComponentType<T> type, Supplier<T> setter) {
        return (T) this.components.computeIfAbsent(type, (o) -> setter.get());
    }

    public <T> Entity set(DataComponentType<T> type, T data) {
        components.put(type, data);
        return this;
    }

    @Override
    public void tick() {
        tickMovement();
    }

    private void tickMovement() {
        final var velocity = get(EntityComponents.VELOCITY);
        if (velocity == Vector2.Zero) {
            set(EntityComponents.ANIMATION, EntityAnimationType.from(EntityAnimationType.IDLE, get(EntityComponents.LAST_VELOCITY)));
            return;
        }

        final var position = get(EntityComponents.POSITION);
        if (Math.abs(velocity.x) == Math.abs(velocity.y)) {
            velocity.scl(0.707F, 0.707F);
        }
        position.mulAdd(velocity, get(EntityComponents.MOVEMENT_SPEED));
        set(EntityComponents.ANIMATION, EntityAnimationType.from(EntityAnimationType.WALK, velocity));
        set(EntityComponents.LAST_VELOCITY, velocity);
        set(EntityComponents.VELOCITY, Vector2.Zero);
    }
}
