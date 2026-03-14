package sh.miles.blobs.entity.component;

import com.badlogic.gdx.math.Vector2;
import sh.miles.blobs.common.attribute.Copyable;

public class VelocityComponent implements Copyable<VelocityComponent> {
    public Vector2 velocity;
    public float friction;

    public VelocityComponent(Vector2 velocity, float friction) {
        this.velocity = velocity;
        this.friction = friction;
    }

    @Override
    public VelocityComponent copy() {
        return new VelocityComponent(this.velocity.cpy(), this.friction);
    }
}
