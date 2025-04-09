package sh.miles.blobs.client.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.IntSet;
import sh.miles.blobs.client.BlobsRender;
import sh.miles.blobs.entity.component.EntityComponents;

import java.util.Map;
import java.util.function.Function;

public final class Controls implements InputProcessor {

    private static final Map<Integer, Function<BlobsRender, Boolean>> actions = Map.of(
            Input.Keys.W, (render) -> {
                final var entity = render.getLevelRenderer().getLevel().getEntity(0);
                entity.set(EntityComponents.VELOCITY, entity.get(EntityComponents.VELOCITY).withVelocity((v) -> {
                    v.y = entity.get(EntityComponents.MOVEMENT_SPEED) / 10;
                    return v;
                }));
                return true;
            },
            Input.Keys.S, (render) -> {
                final var entity = render.getLevelRenderer().getLevel().getEntity(0);
                entity.set(EntityComponents.VELOCITY, entity.get(EntityComponents.VELOCITY).withVelocity((v) -> {
                    v.y = -entity.get(EntityComponents.MOVEMENT_SPEED) / 10;
                    return v;
                }));
                return true;
            },
            Input.Keys.A, (render) -> {
                final var entity = render.getLevelRenderer().getLevel().getEntity(0);
                entity.set(EntityComponents.VELOCITY, entity.get(EntityComponents.VELOCITY).withVelocity((v) -> {
                    v.x = -entity.get(EntityComponents.MOVEMENT_SPEED) / 10;
                    return v;
                }));
                return true;
            },
            Input.Keys.D, (render) -> {
                final var entity = render.getLevelRenderer().getLevel().getEntity(0);
                entity.set(EntityComponents.VELOCITY, entity.get(EntityComponents.VELOCITY).withVelocity((v) -> {
                    v.x = entity.get(EntityComponents.MOVEMENT_SPEED) / 10;
                    return v;
                }));
                return true;
            }
    );


    private final BlobsRender render;
    private final IntSet keyDown;

    public Controls(BlobsRender render) {
        this.render = render;
        this.keyDown = new IntSet();
    }

    public void tickInput() {
        final var iter = this.keyDown.iterator();
        while (iter.hasNext) {
            keyDown(iter.next());
        }
    }

    @Override
    public boolean keyDown(final int keycode) {
        keyDown.add(keycode);
        final var action = actions.get(keycode);
        if (action != null) {
            action.apply(this.render);
            return true;
        }
        return false;
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
