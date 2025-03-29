package sh.miles.blobs.entity.components;

import com.badlogic.gdx.math.Vector2;

public record MovementComponent(Vector2 velocity, Vector2 lastVelocity, float speed) {

    @Override
    public Vector2 velocity() {
        return velocity;
    }

    @Override
    public Vector2 lastVelocity() {
        return lastVelocity;
    }

    public MovementComponent withVelocity(Vector2 velocity, boolean updateLastVelocity) {
        return new MovementComponent(velocity, updateLastVelocity ? this.velocity : this.lastVelocity, this.speed);
    }

    public MovementComponent withLastVelocity(Vector2 lastVelocity) {
        return new MovementComponent(this.velocity, lastVelocity, this.speed);
    }

    public MovementComponent withSpeed(float speed) {
        return new MovementComponent(this.velocity, this.lastVelocity, speed);
    }

    public Builder builder() {
        return new Builder(this);
    }

    public static class Builder {

        public Vector2 velocity;
        public Vector2 lastVelocity;
        public float speed;

        public Builder() {
        }

        public Builder(MovementComponent component) {
            velocity = component.velocity;
            lastVelocity = component.lastVelocity;
            speed = component.speed;
        }

        public MovementComponent build() {
            return new MovementComponent(this.velocity, this.lastVelocity, this.speed);
        }
    }
}
