package sh.miles.blobs.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class BlobsRender extends ApplicationAdapter {
    public static float GAME_WIDTH = 800;
    public static float GAME_HEIGHT = 600;

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Texture texture;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        this.viewport = new FillViewport(GAME_WIDTH * aspectRatio, GAME_HEIGHT, camera);
        this.viewport.apply();
        camera.position.set(0, 0, 0);

        this.texture = new Texture(Gdx.files.internal("tiles/tile_grass.png"));
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
        batch.begin();
        batch.draw(this.texture, 0, 0);
        batch.setProjectionMatrix(this.camera.combined);
        batch.end();
    }

    @Override
    public void dispose() {
    }
}
