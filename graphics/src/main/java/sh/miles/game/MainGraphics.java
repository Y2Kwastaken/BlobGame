package sh.miles.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainGraphics extends ApplicationAdapter {
    // 100 150 369 308
    public static float GAME_WIDTH = 500;
    public static float GAME_HEIGHT = 500;

    private float posx = GAME_WIDTH / 2;
    private float posy = GAME_HEIGHT / 2;

    private SpriteManager sprites;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.sprites = new SpriteManager();
        float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        this.viewport = new FillViewport(GAME_WIDTH * aspectRatio, GAME_HEIGHT, camera);
        this.viewport.apply();
        camera.position.set(GAME_WIDTH / 2, GAME_HEIGHT / 2, 0);
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
        for (int i = 0; i < 30; i++) {
            final int finalI = i;
            this.sprites.use(0, 0, (sprite) -> {
                batch.draw(sprite, 100 + (finalI * 15), GAME_HEIGHT / 2);
            });
        }
        batch.end();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
    }

}
