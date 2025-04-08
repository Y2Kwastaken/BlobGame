package sh.miles.blobs.level.position;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public record Position(float x, float y) {

    public Position withX(float x) {
        return new Position(x, this.y);
    }

    public Position withY(float y) {
        return new Position(this.x, y);
    }

    public Position apply(Vector2 vector2) {
        return new Position(this.x + vector2.x, this.y + vector2.y);
    }

    public Vector2 toVector2() {
        return new Vector2(x, y);
    }

    public Vector3 toVector3() {
        return new Vector3(x, y, 0.0f);
    }

    public static Position fromVector2(Vector2 vector) {
        return new Position(vector.x, vector.y);
    }

}
