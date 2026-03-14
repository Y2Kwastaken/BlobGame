package sh.miles.blobs.entity.component;

import com.badlogic.gdx.math.Vector2;
import sh.miles.blobs.common.attribute.Copyable;
import sh.miles.blobs.common.math.BoundingBox;

import java.util.Objects;

public class HitboxComponent implements Copyable<HitboxComponent> {
    public final Vector2 offset;
    public BoundingBox bounds;

    public HitboxComponent(float x, float y, float width, float height, float offsetX, float offsetY) {
        this.offset = new Vector2(offsetX, offsetY);
        this.bounds = new BoundingBox(x, y, width, height);
    }

    private HitboxComponent(Vector2 offset, BoundingBox bounds) {
        this.offset = offset;
        this.bounds = bounds;
    }

    public BoundingBox getBoundsWithOffset() {
        return new BoundingBox(bounds.x + offset.x, bounds.y + offset.y, bounds.width, bounds.height);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final HitboxComponent that)) return false;
        return Objects.equals(offset, that.offset) && Objects.equals(bounds, that.bounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset, bounds);
    }

    @Override
    public String toString() {
        return "HitboxComponent{" +
            "offset=" + offset +
            ", bounds=" + bounds +
            '}';
    }

    @Override
    public HitboxComponent copy() {
        return new HitboxComponent(this.offset.cpy(), this.bounds.copy());
    }
}
