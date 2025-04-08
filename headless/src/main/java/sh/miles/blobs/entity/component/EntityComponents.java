package sh.miles.blobs.entity.component;

import sh.miles.blobs.component.ComponentType;
import sh.miles.blobs.entity.EntityType;
import sh.miles.blobs.level.Level;
import sh.miles.blobs.level.position.Position;

public final class EntityComponents {
    public static final ComponentType<EntityType> ENTITY_TYPE = new ComponentType<>(EntityType.class, true);
    public static final ComponentType<Level> LEVEL = new ComponentType<>(Level.class, true);
    public static final ComponentType<Position> POSITION = new ComponentType<>(Position.class, true);
    public static final ComponentType<Float> MOVEMENT_SPEED = new ComponentType<>(Float.class, true);
    public static final ComponentType<VelocityComponent> VELOCITY = new ComponentType<>(VelocityComponent.class, true);
    public static final ComponentType<CombatComponent> COMBAT = new ComponentType<>(CombatComponent.class, true);
    public static final ComponentType<HealthComponent> HEALTH = new ComponentType<>(HealthComponent.class, true);
}
