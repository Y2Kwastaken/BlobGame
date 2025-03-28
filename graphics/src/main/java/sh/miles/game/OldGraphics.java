package sh.miles.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class OldGraphics extends ApplicationAdapter implements InputProcessor {

    public static final float GAME_WORLD_WIDTH = 500;
    public static final float GAME_WORLD_HEIGHT = 250;

    private SpriteBatch batch;
    private Sprite sprite;
    private OrthographicCamera camera;
    private Viewport viewport;

    public OldGraphics() {
    }

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.sprite = new Sprite(new Texture("space.jpg"));
        sprite.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);

        this.camera = new OrthographicCamera();
        float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        this.viewport = new FillViewport(GAME_WORLD_WIDTH * aspectRatio, GAME_WORLD_HEIGHT, camera);
        this.viewport.apply();
        camera.position.set(GAME_WORLD_WIDTH / 2, GAME_WORLD_HEIGHT / 2, 0);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(final int width, final int height) {
        viewport.update(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.begin();
        batch.setProjectionMatrix(this.camera.combined);
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        this.sprite.getTexture().dispose();
    }

    @Override
    public boolean keyDown(final int keycode) {
        if (keycode == Keys.W) {
            camera.translate(0, 1F);
        }
        if (keycode == Keys.A) {
            camera.translate(-1F, 0);
        }
        if (keycode == Keys.S) {
            camera.translate(0, -1F);
        }
        if (keycode == Keys.D) {
            camera.translate(1F, 0);
        }
        if (keycode == Keys.R) {

        }
        return true;
    }

    @Override
    public boolean keyUp(final int keycode) {
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
        camera.translate(0, -amountY);
        return true;
    }
}
