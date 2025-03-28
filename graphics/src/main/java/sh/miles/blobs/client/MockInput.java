package sh.miles.blobs.client;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntSet;
import sh.miles.blobs.client.atlas.BlobsTextures;
import sh.miles.blobs.component.DataComponentType;
import sh.miles.blobs.entity.Entity;
import sh.miles.blobs.entity.EntityType;
import sh.miles.blobs.entity.animation.EntityAnimationType;
import sh.miles.blobs.entity.components.EntityComponents;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.S;
import static com.badlogic.gdx.Input.Keys.V;
import static com.badlogic.gdx.Input.Keys.W;

public class MockInput implements InputProcessor {

    private final IntSet keyDown = new IntSet();
    public int region = 0;

    public Entity entity = new Entity()
            .set(EntityComponents.ENTITY_TYPE, EntityType.HUMAN)
            .set(EntityComponents.POSITION, new Vector2(BlobsRender.GAME_WIDTH / 2, BlobsRender.GAME_HEIGHT / 2))
            .set(EntityComponents.VELOCITY, Vector2.Zero)
            .set(EntityComponents.LAST_VELOCITY, Vector2.Zero)
            .set(EntityComponents.MOVEMENT_SPEED, 1F)
            .set(EntityComponents.ANIMATION, EntityAnimationType.IDLE_SOUTH);

    public void tick() {
        final var iter = keyDown.iterator();
        while (iter.hasNext) {
            keyDown(iter.next());
        }
    }

    @Override
    public boolean keyDown(final int keycode) {
        if (keycode == Input.Keys.N) {
            var values = EntityAnimationType.values();
            int next = entity.get(EntityComponents.ANIMATION).ordinal() + 1;
            if (next >= values.length) {
                next = 0;
            }

            entity.set(EntityComponents.ANIMATION, values[next]);
            return true;
        }

        keyDown.add(keycode);
        final var vec = entity.get(EntityComponents.VELOCITY).cpy();
        if (keycode == W) {
            vec.y = 1F;
        } else if (keycode == A) {
            vec.x = -1F;
        } else if (keycode == S) {
            vec.y = -1F;
        } else if (keycode == D) {
            vec.x = 1F;
        }
        entity.set(EntityComponents.VELOCITY, vec);

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
