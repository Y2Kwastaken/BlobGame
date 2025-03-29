package sh.miles.blobs.entity.components;

import sh.miles.blobs.component.DataComponentType;
import sh.miles.blobs.entity.EntityType;
import sh.miles.blobs.entity.animation.EntityAnimationType;

public class EntityComponents {
    public static final DataComponentType<EntityType> ENTITY_TYPE = new DataComponentType<>(EntityType.class, true);
    public static final DataComponentType<HealthComponent> HEALTH = new DataComponentType<>(HealthComponent.class, true);
    public static final DataComponentType<PositionComponent> POSITION = new DataComponentType<>(PositionComponent.class, true);
    public static final DataComponentType<MovementComponent> MOVEMENT = new DataComponentType<>(MovementComponent.class, true);
    public static final DataComponentType<AttackComponent> ATTACK = new DataComponentType<>(AttackComponent.class, true);
    public static final DataComponentType<EntityAnimationType> ANIMATION = new DataComponentType<>(EntityAnimationType.class, false);
    public static final DataComponentType<DisposeComponent> DISPOSE = new DataComponentType<>(DisposeComponent.class, false);
}
