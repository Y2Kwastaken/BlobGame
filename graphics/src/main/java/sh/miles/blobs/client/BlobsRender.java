package sh.miles.blobs.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import sh.miles.blobs.client.asset.Assets;
import sh.miles.blobs.client.level.ClientLevel;
import sh.miles.blobs.level.TileType;

public class BlobsRender extends ApplicationAdapter {
    public static float GAME_WIDTH = 800;
    public static float GAME_HEIGHT = 600;

    private SpriteBatch batch;
    private ClientLevel level;
    private OrthographicCamera camera;
    private Viewport viewport;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        this.viewport = new FillViewport(GAME_WIDTH * aspectRatio, GAME_HEIGHT, camera);
        this.viewport.apply();
        camera.position.set(GAME_WIDTH / 2, GAME_HEIGHT / 2, 0);

        this.level = new ClientLevel("first", this.batch, this.camera, 1);
    }

    @Override
    public void resize(final int width, final int height) {
        viewport.update(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(this.camera.combined);
        this.level.render();
        batch.begin();
        batch.end();
    }

    @Override
    public void dispose() {
    }
}
