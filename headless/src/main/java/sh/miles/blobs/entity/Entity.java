package sh.miles.blobs.entity;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import sh.miles.blobs.api.Identifier;
import sh.miles.blobs.api.component.ComponentHolder;
import sh.miles.blobs.api.component.ComponentType;
import sh.miles.blobs.api.dto.game.GameEntityDTO;
import sh.miles.blobs.api.entity.EntityType;
import sh.miles.blobs.common.attribute.Copyable;
import sh.miles.blobs.common.math.BoundingBox;
import sh.miles.blobs.common.math.Position;
import sh.miles.blobs.entity.component.CombatComponent;
import sh.miles.blobs.entity.component.HitboxComponent;
import sh.miles.blobs.entity.component.OrientationComponent;
import sh.miles.blobs.entity.component.VelocityComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@NullMarked
public class Entity implements ComponentHolder, Comparable<Entity> {
    private static int nextId = 0;

    public final int id;
    private final Map<Identifier, Object> components;

    public Entity() {
        this.id = nextId++;
        this.components = new HashMap<>();
    }

    public GameEntityDTO.EntitySnapshotDTO nextSnapshot() {
        EntityType type = get(EntityComponents.ENTITY_TYPE);
        Position pos = get(EntityComponents.POSITION);
        VelocityComponent vel = getOrNull(EntityComponents.VELOCITY);
        HitboxComponent hitboxComponent = getOrNull(EntityComponents.HITBOX);
        CombatComponent combat = getOrNull(EntityComponents.COMBAT);
        OrientationComponent orientation = getOrNull(EntityComponents.ORIENTATION);
        BoundingBox hitbox = null;
        if (hitboxComponent != null) {
            hitbox = hitboxComponent.getBoundsWithOffset();
        }

        return new GameEntityDTO.EntitySnapshotDTO(
            id,
            get(EntityComponents.ENTITY_TYPE),
            pos.x(), pos.y(),
            vel == null ? 0.0f : vel.velocity.x, vel == null ? 0.0f : vel.velocity.y,
            hitbox == null ? 0.0f : hitbox.x, hitbox == null ? 0.0f : hitbox.y,
            hitbox == null ? 0.0f : hitbox.width, hitbox == null ? 0.0f : hitbox.height,
            combat != null && combat.attackTimeLeft != 0,
            orientation != null,
            orientation == null ? 0.0f : orientation.facingX,
            orientation == null ? 0.0f : orientation.facingY
        );
    }

    @Override
    public <T> void set(final ComponentType<T> component, final T data) {
        components.put(component.id(), data);
    }

    @Override
    public <T> boolean has(final ComponentType<T> component) {
        return this.components.containsKey(component.id());
    }

    @Override
    public <T> T get(final ComponentType<T> component) throws IllegalArgumentException {
        final Object obj = this.components.get(component.id());
        if (obj == null) {
            throw new IllegalArgumentException("No component '%s' on entity '%d'".formatted(component.id(), id));
        }


        return component.type().cast(obj);
    }

    @Override
    public @Nullable <T> T getOrNull(final ComponentType<T> component) {
        Object obj = this.components.get(component.id());
        return obj == null ? null : component.type().cast(obj);
    }

    @Override
    public <T> T getOrDefault(final ComponentType<T> component, final Supplier<T> defaultValue) {
        final Object obj = this.components.get(component.id());
        if (obj == null) {
            return defaultValue.get();
        }

        return component.type().cast(obj);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<T> remove(final ComponentType<T> type) {
        return (Optional<T>) Optional.ofNullable(this.components.remove(type.id()));
    }

    @Override
    public void copyTo(final ComponentHolder other) {
        for (final Map.Entry<Identifier, Object> entry : this.components.entrySet()) {
            var value = entry.getValue();
            if (value instanceof Copyable<?> copyable) {
                value = copyable.copy();
            }

            other.set(EntityComponents.getById(entry.getKey()), value);
        }
    }

    @Override
    public int compareTo(final Entity o) {
        return Integer.compare(this.id, o.id);
    }
}
