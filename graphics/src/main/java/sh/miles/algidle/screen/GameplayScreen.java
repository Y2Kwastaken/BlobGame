package sh.miles.algidle.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.jspecify.annotations.NullMarked;
import sh.miles.algidle.MainGraphics;

@NullMarked
public class GameplayScreen implements Screen {

    private final MainGraphics game;
    private final SpriteBatch batch;
    private final Viewport viewport;
    public GameplayScreen(final MainGraphics game) {
        this.game = game;
        this.batch = game.getBatch();
        this.viewport = game.getViewport();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(final float delta) {
        viewport.apply();
        ScreenUtils.clear(Color.BLACK);
        batch.setProjectionMatrix(viewport.getCamera().combined);
    }

    @Override
    public void resize(final int width, final int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
