package sh.miles.blobs.entity.animation;

import com.badlogic.gdx.math.Vector2;

public enum EntityAnimationType {
    IDLE_NORTH(0),
    IDLE_SOUTH(0),
    IDLE_EAST(0),
    IDLE_WEST(0),
    WALK_NORTH(1),
    WALK_SOUTH(1),
    WALK_EAST(1),
    WALK_WEST(1),
    ATTACK_NORTH(2),
    ATTACK_SOUTH(2),
    ATTACK_EAST(2),
    ATTACK_WEST(2),
    DIE_NORTH(3),
    DIE_SOUTH(3),
    DIE_EAST(3),
    DIE_WEST(3),
    ;
//    DEATH,
//    WEAPON_ATTACK;

    public static final int IDLE = 0;
    public static final int WALK = 1;
    public static final int ATTACK = 2;

    public final int type;

    EntityAnimationType(int type) {
        this.type = type;
    }

    public static EntityAnimationType from(int type, Vector2 vec) {
        final var vals = values();
        final int section = type * 4;
        if (vec.y > 0.0F) {
            return vals[section];
        } else if (vec.y < 0.0F) {
            return vals[section + 1];
        } else if (vec.x > 0.0F) {
            return vals[section + 2];
        } else if (vec.x < 0.0F) {
            return vals[section + 3];
        } else {
            return vals[section + 1];
        }
    }
}
