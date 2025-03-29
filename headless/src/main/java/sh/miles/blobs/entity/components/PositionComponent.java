package sh.miles.blobs.entity.components;

import com.badlogic.gdx.math.Vector2;

public record PositionComponent(float x, float y) {
    public Vector2 toVec2() {
        return new Vector2(x, y);
    }
}
