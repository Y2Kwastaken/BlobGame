package sh.miles.blobs.client.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import sh.miles.blobs.api.dto.client.ClientDebugDTO;
import sh.miles.blobs.api.dto.client.ClientEntityDTO;
import sh.miles.blobs.api.event.ClientEvents;
import sh.miles.blobs.api.event.GameEvents;
import sh.miles.blobs.client.GameRender;

public final class Controls implements InputProcessor {

    public int playerId = 0;

    public Controls() {
    }

    /**
     * Called every frontend render frame (or pre-tick hook). Gathers raw input and ships it to the backend.
     */
    public void tickInput() {
        float moveX = 0;
        float moveY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) moveY += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) moveY -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) moveX -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) moveX += 1;

        float length = (float) Math.sqrt(moveX * moveX + moveY * moveY);
        if (length > 0) {
            moveX /= length;
            moveY /= length;
        }

        boolean attack = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);

        GameEvents.call(ClientEvents.PLAYER_INPUT, new ClientEntityDTO.EntityInputDTO(playerId, moveX, moveY, attack));

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_7)) {
            GameRender.RDSET.showHitboxes.flip();
            GameRender.RDSET.showAttackSwing.flip();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_4)) {
            GameRender.RDSET.showLocation.flip();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_8)) {
            GameEvents.call(ClientEvents.PLAYER_DEBUG, new ClientDebugDTO.PlayerDebugDTO(playerId, true, false));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_9)) {
            GameEvents.call(ClientEvents.PLAYER_DEBUG, new ClientDebugDTO.PlayerDebugDTO(playerId, false, true));
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
