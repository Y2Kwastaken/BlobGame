package sh.miles.blobs.entity;

import sh.miles.blobs.component.ComponentType;
import sh.miles.blobs.entity.component.EntityComponents;
import sh.miles.blobs.entity.component.VelocityComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class Entity {
    private static int nextId = 0;

    public final int id;
    private final Map<ComponentType<?>, Object> components;

    private Entity() {
        this.id = nextId++;
        this.components = new HashMap<>();
    }

    public <T> void set(ComponentType<T> component, T value) {
        this.components.put(component, value);
    }

    public <T> T get(ComponentType<T> component) {
        return (T) this.components.get(component);
    }

    public void tick() {
        if (get(EntityComponents.ENTITY_TYPE) == null) return; // can't tick entity typeless entities
        VelocityComponent.update(this);
    }

    public static Entity create(EntityType type, Consumer<Entity> editor) {
        final var entity = new Entity();
        entity.set(EntityComponents.ENTITY_TYPE, type);
        editor.accept(entity);
        return entity;
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
