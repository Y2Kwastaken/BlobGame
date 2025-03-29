package sh.miles.blobs.entity;

import org.jspecify.annotations.Nullable;
import sh.miles.blobs.component.DataComponentType;
import sh.miles.blobs.entity.components.EntitySystems;
import sh.miles.blobs.util.Ticking;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    public <T> Entity set(DataComponentType<T> type, T data) {
        components.put(type, data);
        return this;
    }

    public <T> void setIfUnset(DataComponentType<T> type, T data) {
        components.computeIfAbsent(type, (k) -> data);
    }

    @Override
    public void tick() {
        EntitySystems.dispose(this);
        EntitySystems.health(this);
        EntitySystems.attack(this);
        EntitySystems.movement(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final Entity entity)) return false;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
