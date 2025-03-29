package sh.miles.blobs.client;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntSet;
import sh.miles.blobs.entity.Entity;
import sh.miles.blobs.entity.EntityType;
import sh.miles.blobs.entity.animation.EntityAnimationType;
import sh.miles.blobs.entity.components.AttackComponent;
import sh.miles.blobs.entity.components.EntityComponents;
import sh.miles.blobs.entity.components.HealthComponent;
import sh.miles.blobs.entity.components.MovementComponent;
import sh.miles.blobs.entity.components.PositionComponent;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.SPACE;
import static com.badlogic.gdx.Input.Keys.W;

public class MockInput implements InputProcessor {

    private final IntSet keyDown = new IntSet();
    public int region = 0;

    public Entity player = new Entity()
            .set(EntityComponents.ENTITY_TYPE, EntityType.HUMAN)
            .set(EntityComponents.POSITION, new PositionComponent(BlobsRender.GAME_WIDTH / 2, (float) (BlobsRender.GAME_HEIGHT / 1.75)))
            .set(EntityComponents.MOVEMENT, new MovementComponent(
                    Vector2.Zero.cpy(),
                    Vector2.Zero.cpy(),
                    1F
            ))
            .set(EntityComponents.HEALTH, new HealthComponent(10.0F, 10.0F))
            .set(EntityComponents.ATTACK, new AttackComponent(1.0F, 35.0F, 35, -1, false))
            .set(EntityComponents.ANIMATION, EntityAnimationType.IDLE_SOUTH);
    public Entity enemy = new Entity()
            .set(EntityComponents.ENTITY_TYPE, EntityType.SLIME)
            .set(EntityComponents.POSITION, new PositionComponent((float) (BlobsRender.GAME_WIDTH / 1.75), (float) (BlobsRender.GAME_HEIGHT / 2)))
            .set(EntityComponents.MOVEMENT, new MovementComponent(
                    Vector2.Zero.cpy(),
                    Vector2.Zero.cpy(),
                    1F
            ))
            .set(EntityComponents.HEALTH, new HealthComponent(1.0F, 1.0F))
            .set(EntityComponents.ANIMATION, EntityAnimationType.IDLE_EAST)
            .set(EntityComponents.ATTACK, new AttackComponent(5.0F, 35.0F, 120, -1, false));

    public void tick() {
        final var iter = keyDown.iterator();
        while (iter.hasNext) {
            keyDown(iter.next());
        }
    }

    @Override
    public boolean keyDown(final int keycode) {
        keyDown.add(keycode);
        final var movement = enemy.get(EntityComponents.MOVEMENT).builder();
        final var vec = movement.velocity;
        if (keycode == W) {
            vec.y = 1F;
        } else if (keycode == A) {
            vec.x = -1F;
        } else if (keycode == S) {
            vec.y = -1F;
        } else if (keycode == D) {
            vec.x = 1F;
        } else if (keycode == SPACE) {
            final var attack = enemy.get(EntityComponents.ATTACK);
            if (attack != null) {
                enemy.set(EntityComponents.ATTACK, attack.withIsAttacking(true));
            }
            keyDown.remove(keycode);
            return true;
        }
        enemy.set(EntityComponents.MOVEMENT, movement.build());
        return true;
    }

    @Override
    public boolean keyUp(final int keycode) {
        keyDown.remove(keycode);
        return false;
    }

    @Override
    public boolean keyTyped(final char character) {
        return false;
    }

    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer, final int button) {
        return false;
    }

    @Override
    public boolean touchUp(final int screenX, final int screenY, final int pointer, final int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(final int screenX, final int screenY, final int pointer, final int button) {
        return false;
    }

    @Override
    public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(final int screenX, final int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(final float amountX, final float amountY) {
        return false;
    }
}
