package sh.miles.blobs.entity;

import sh.miles.blobs.api.Identifier;
import sh.miles.blobs.api.component.ComponentType;
import sh.miles.blobs.api.entity.EntityType;
import sh.miles.blobs.common.math.Position;
import sh.miles.blobs.entity.component.CombatComponent;
import sh.miles.blobs.entity.component.HealthComponent;
import sh.miles.blobs.entity.component.HitboxComponent;
import sh.miles.blobs.entity.component.OrientationComponent;
import sh.miles.blobs.entity.component.VelocityComponent;
import sh.miles.blobs.level.Level;

import java.util.HashMap;
import java.util.Map;

import static sh.miles.blobs.api.Identifier.base;

public class EntityComponents {
    public static final ComponentType<CombatComponent> COMBAT = new ComponentType<>(base("combat"), CombatComponent.class, true);
    public static final ComponentType<EntityType> ENTITY_TYPE = new ComponentType<>(base("entity_type"), EntityType.class, true);
    public static final ComponentType<HealthComponent> HEALTH = new ComponentType<>(base("health"), HealthComponent.class, true);
    public static final ComponentType<HitboxComponent> HITBOX = new ComponentType<>(base("hitbox"), HitboxComponent.class, true);
    public static final ComponentType<Level> LEVEL = new ComponentType<>(base("level"), Level.class, true);
    public static final ComponentType<Float> MOVEMENT_SPEED = new ComponentType<>(base("movement_speed"), Float.class, true);
    public static final ComponentType<OrientationComponent> ORIENTATION = new ComponentType<>(base("orientation"), OrientationComponent.class, true);
    public static final ComponentType<Position> POSITION = new ComponentType<>(base("position"), Position.class, true);
    public static final ComponentType<VelocityComponent> VELOCITY = new ComponentType<>(base("velocity"), VelocityComponent.class, true);

    private static final Map<Identifier, ComponentType<?>> COMPONENTS = new HashMap<>();

    static {
        COMPONENTS.put(COMBAT.id(), COMBAT);
        COMPONENTS.put(ENTITY_TYPE.id(), ENTITY_TYPE);
        COMPONENTS.put(HEALTH.id(), HEALTH);
        COMPONENTS.put(HITBOX.id(), HITBOX);
        COMPONENTS.put(LEVEL.id(), LEVEL);
        COMPONENTS.put(MOVEMENT_SPEED.id(), MOVEMENT_SPEED);
        COMPONENTS.put(ORIENTATION.id(), ORIENTATION);
        COMPONENTS.put(POSITION.id(), POSITION);
        COMPONENTS.put(VELOCITY.id(), VELOCITY);
    }

    public static ComponentType<Object> getById(Identifier id) {
        return (ComponentType<Object>) COMPONENTS.get(id);
    }
}
