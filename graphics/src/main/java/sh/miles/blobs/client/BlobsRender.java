package sh.miles.blobs.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import sh.miles.blobs.BlobGame;
import sh.miles.blobs.GameRunner;
import sh.miles.blobs.client.asset.Assets;
import sh.miles.blobs.client.asset.Textures;
import sh.miles.blobs.client.input.Controls;
import sh.miles.blobs.client.level.ClientLevel;
import sh.miles.blobs.client.level.LevelRenderer;
import sh.miles.blobs.level.Level;
import sh.miles.blobs.level.TileType;

public class BlobsRender extends ApplicationAdapter {
    public static float GAME_WIDTH = 800;
    public static float GAME_HEIGHT = 600;
    public static float UNIT = 1 / 16F;
    public static float UNIT_WIDTH = GAME_WIDTH * UNIT;
    public static float UNIT_HEIGHT = GAME_HEIGHT * UNIT;

    private Controls controls;
    private SpriteBatch batch;
    private LevelRenderer levelRenderer;
    private OrthographicCamera camera;
    private Viewport viewport;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        this.viewport = new FillViewport(UNIT_WIDTH * aspectRatio, UNIT_HEIGHT, camera);
        this.viewport.apply(true);

        this.levelRenderer = new LevelRenderer(this.batch, this.camera, 1 / 16f);
        this.levelRenderer.setLevel(GameRunner.GAME.getLevel());
        final var level = this.levelRenderer.getLevel();
        for (int y = 0; y < level.height; y++) {
            for (int x = 0; x < level.width; x++) {
                level.setType(x, y, TileType.GRASS);
            }
        }

        this.controls = new Controls(this);
        Gdx.input.setInputProcessor(controls);
        GameRunner.GAME.addPreTickHook(() -> {
            this.controls.tickInput();
        });
        GameRunner.RUNNER.start();
    }

    @Override
    public void resize(final int width, final int height) {
        viewport.update(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(this.camera.combined);
        this.levelRenderer.render();
        camera.update();
        if (levelRenderer.getLevel().getType(0, 1) != TileType.STONE) {
            this.levelRenderer.getLevel().setType(0, 1, TileType.STONE);
        }
    }

    @Override
    public void dispose() {
        GameRunner.RUNNER.stop();

        Textures.dispose();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
