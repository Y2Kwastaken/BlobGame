package sh.miles.algidle.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import sh.miles.algidle.MainGraphicalInterface;

public class MainMenuScreen implements Screen {

    private final SpriteBatch batch = MainGraphicalInterface.BATCH;

    @Override
    public void show() {

    }

    @Override
    public void render(final float delta) {
        ScreenUtils.clear(Color.BLACK);
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
