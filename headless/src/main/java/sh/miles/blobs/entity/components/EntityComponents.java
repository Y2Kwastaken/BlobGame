package sh.miles.blobs.entity.components;

import com.badlogic.gdx.math.Vector2;
import sh.miles.blobs.component.DataComponent;
import sh.miles.blobs.component.DataComponentType;
import sh.miles.blobs.entity.EntityType;
import sh.miles.blobs.entity.animation.EntityAnimationType;

public class EntityComponents {
    public static final DataComponentType<EntityType> ENTITY_TYPE = new DataComponentType<>(EntityType.class, true);
    public static final DataComponentType<Float> HEALTH = new DataComponentType<>(Float.class, true);
    public static final DataComponentType<Vector2> POSITION = new DataComponentType<>(Vector2.class, true);
    public static final DataComponentType<Vector2> VELOCITY = new DataComponentType<>(Vector2.class, false);
    public static final DataComponentType<Vector2> LAST_VELOCITY = new DataComponentType<>(Vector2.class, false);
    public static final DataComponentType<Float> MOVEMENT_SPEED = new DataComponentType<>(Float.class, true);
    public static final DataComponentType<EntityAnimationType> ANIMATION = new DataComponentType<>(EntityAnimationType.class, false);
}
