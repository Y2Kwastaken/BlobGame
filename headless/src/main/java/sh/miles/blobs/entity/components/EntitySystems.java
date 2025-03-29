package sh.miles.blobs.entity.components;

import com.badlogic.gdx.math.Vector2;
import sh.miles.blobs.BlobsGame;
import sh.miles.blobs.GameRunner;
import sh.miles.blobs.entity.Entity;
import sh.miles.blobs.entity.animation.EntityAnimationType;
import sh.miles.blobs.util.GameMath;

import static sh.miles.blobs.entity.components.EntityComponents.ANIMATION;
import static sh.miles.blobs.entity.components.EntityComponents.ATTACK;
import static sh.miles.blobs.entity.components.EntityComponents.DISPOSE;
import static sh.miles.blobs.entity.components.EntityComponents.HEALTH;
import static sh.miles.blobs.entity.components.EntityComponents.MOVEMENT;
import static sh.miles.blobs.entity.components.EntityComponents.POSITION;

public final class EntitySystems {

    public static void dispose(Entity entity) {
        final var dispose = entity.get(DISPOSE);
        if (dispose == null) return;

        final var buffer = dispose.buffer();
        if (buffer == 0) {
            entity.set(DISPOSE, dispose.withDispose(true));
            return;
        }

        entity.set(ANIMATION, EntityAnimationType.DIE_SOUTH);
        entity.set(DISPOSE, dispose.withBuffer(buffer - 1));
    }

    public static void health(Entity entity) {
        final var health = entity.get(HEALTH);
        if (health == null) return;

        if (health.health() <= 0) {
            entity.setIfUnset(DISPOSE, new DisposeComponent(false, 35)); // explicit 35 tick buffer for now
            return;
        }

        float nextHealth = health.health();
        if (nextHealth > health.maxHealth()) {
            nextHealth = health.maxHealth();
        }

        entity.set(HEALTH, health.withHealth(nextHealth));
    }

    public static void attack(Entity entity) {
        final var attackComponent = entity.get(ATTACK);
        if (attackComponent == null || !attackComponent.isAttacking()) return;
        final var attack = attackComponent.builder();

        if (attack.ticksLeft == -1) {
            attack.ticksLeft = attack.attackTicks;
        }

        final var movement = entity.get(EntityComponents.MOVEMENT);
        entity.set(ANIMATION, EntityAnimationType.from(EntityAnimationType.ATTACK, movement.lastVelocity()));

        attack.ticksLeft--;
        if (attack.ticksLeft == -1) {
            attack.isAttacking = false;
        }

        if (attack.ticksLeft + 1 != attack.attackTicks) {
            entity.set(ATTACK, attack.build());
            return;
        }

        final BlobsGame game = GameRunner.GAME.game;
        final var position = entity.get(POSITION).toVec2();

        for (final Entity target : game.entities) {
            final var targetPosition = target.get(POSITION);
            if (target == entity || targetPosition == null) continue;
            if (GameMath.isInCircle(position.x, position.y, attack.attackRadius, targetPosition.x(), targetPosition.y())) {
                final var targetHealth = target.get(HEALTH);
                if (targetHealth == null) continue;
                target.set(HEALTH, targetHealth.withHealth(targetHealth.health() - attack.damage));
            }
        }

        entity.set(ATTACK, attack.build());
    }

    public static void movement(Entity entity) {
        final var movementComponent = entity.get(MOVEMENT);
        if (movementComponent == null || entity.get(DISPOSE) != null) return;
        final var movement = movementComponent.builder();

        final var attack = entity.get(ATTACK);
        if (attack != null && attack.isAttacking()) return;

        final var velocity = movement.velocity;
        if (velocity.isZero(0.00001F)) {
            entity.set(ANIMATION, EntityAnimationType.from(EntityAnimationType.IDLE, movement.lastVelocity));
            return;
        }

        final var position = entity.get(POSITION).toVec2();
        if (Math.abs(velocity.x) == Math.abs(velocity.y)) {
            velocity.scl( 0.707F);
        }
        position.mulAdd(velocity, movement.speed);
        entity.set(ANIMATION, EntityAnimationType.from(EntityAnimationType.WALK, velocity));

        entity.set(POSITION, new PositionComponent(position.x, position.y));
        movement.lastVelocity = velocity.cpy();
        movement.velocity = velocity.scl(0.005F, 0.005F);
        entity.set(MOVEMENT, movement.build());
    }
}
