package sh.miles.blobs.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import sh.miles.blobs.BlobsGame;
import sh.miles.blobs.GameRunner;
import sh.miles.blobs.client.atlas.BlobsTextures;
import sh.miles.blobs.client.atlas.TextureAtlas;
import sh.miles.blobs.client.entity.animation.EntityAnimationSystem;
import sh.miles.blobs.client.render.Renders;

public class BlobsRender extends ApplicationAdapter {
    public static float GAME_WIDTH = 500;
    public static float GAME_HEIGHT = 500;

    private GameRunner runner;
    private MockInput input;
    private BlobsGame game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;

    @Override
    public void create() {
        this.runner = GameRunner.GAME;
        this.runner.game = new BlobsGame(true, () -> {
            Gdx.app.postRunnable(() -> {
                this.input.tick();
                EntityAnimationSystem.update(this.runner.game.entities);
            });
        });
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        this.viewport = new FillViewport(GAME_WIDTH * aspectRatio, GAME_HEIGHT, camera);
        this.viewport.apply();
        camera.position.set(GAME_WIDTH / 2, GAME_HEIGHT / 2, 0);

        this.input = new MockInput();
        this.runner.start();
        this.runner.game.entities.add(input.entity);

        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void resize(final int width, final int height) {
        viewport.update(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.begin();
        batch.setProjectionMatrix(this.camera.combined);
        Renders.entities(batch, this.runner.game.entities);
        batch.end();
    }

    @Override
    public void dispose() {
        this.runner.stop();
        BlobsTextures.CHARACTER.dispose();
        BlobsTextures.SLIME.dispose();
    }
}
