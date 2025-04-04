package sh.miles.blobs.client.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.IntSet;
import sh.miles.blobs.client.BlobsRender;

import java.util.Map;
import java.util.function.Function;

public final class Controls implements InputProcessor {

    private static final Map<Integer, Function<BlobsRender, Boolean>> actions = Map.of(
            Input.Keys.LEFT, (render) -> {
                render.getCamera().translate(-0.33F, 0.0F);
                return true;
            },
            Input.Keys.RIGHT, (render) -> {
                render.getCamera().translate(0.33F, 0.0F);
                return true;
            },
            Input.Keys.UP, (render) -> {
                render.getCamera().translate(0.0F, 0.33F);
                return true;
            },
            Input.Keys.DOWN, (render) -> {
                render.getCamera().translate(0.0F, -0.33F);
                return true;
            },
            Input.Keys.HOME, (render) -> {
                render.getCamera().position.set(BlobsRender.UNIT_WIDTH, BlobsRender.UNIT_HEIGHT * 2, 0);
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
