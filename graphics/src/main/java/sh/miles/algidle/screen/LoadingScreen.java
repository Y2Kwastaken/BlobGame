package sh.miles.algidle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import sh.miles.algidle.AssetRegistries;
import sh.miles.algidle.MainGraphicalInterface;
import sh.miles.algidle.animation.NaiveAnimation;
import sh.miles.algidle.assets.Screens;
import sh.miles.algidle.assets.Sounds;
import sh.miles.algidle.registry.Registries;
import sh.miles.algidle.utils.collection.registry.Registry;

import java.util.List;

public class LoadingScreen implements Screen {

    private final SpriteBatch batch = MainGraphicalInterface.BATCH;
    private final Texture texture = new Texture("libgdx.png");
    private final BitmapFont font = new BitmapFont();
    private final NaiveAnimation animation = new NaiveAnimation(Gdx.graphics.getDisplayMode().refreshRate).edit((animation) -> {
        animation.addFrame(1, (batch, delta) -> {
            ScreenUtils.clear(Color.BLACK);
            batch.begin();
            batch.draw(texture, 140, 210);
            batch.end();
        });

        animation.addFrame(1, (batch, delta) -> {
            ScreenUtils.clear(Color.BLACK);
            batch.begin();
            batch.setColor(Color.GREEN);
            font.draw(batch, "Starting AlgorithmOS...", 10, Gdx.graphics.getHeight() - 10);
            batch.end();
        });

        final List<String> load = List.of(
                "[ OK ] Started LSB: Record successful boot for GRUB.",
                "[ OK ] Started Apply Kernel Variables.",
                "[ OK ] Mounted Kernel Debug System.",
                "[ OK ] Started Load/Save Random Seed.",
                "[ OK ] Reached Target Local File Systems",
                "      Mounting /boot",
                "[ OK ] Mounted /boot",
                "      Starting switch connections",
                "[ OK ] Started switch connections",
                "[ OK ] Started Network Manager.",
                "      Starting AlgorithmOS",
                "      Starting Connection to foreign machines.",
                "[ OK ] Started Connection to foreign machines.",
                "      Starting read of foreign machine's boot log",
                "[ OK ] Verified success of foreign machine's boot",
                "[ OK ] Started Algorithm OS",
                "      Booting to AlgorithmOS",
                "      Starting algorithm services",
                "      Starting transaction services",
                "      Starting hardware monitoring services",
                "[ OK ] Started algorithms.service",
                "[ OK ] Started transactions.service",
                "[ OK ] Started hardware_monitor.service",
                "[ OK ] Booted Algorithm OS"
        );
        for (int i = 0; i < load.size(); i++) {
            final int finalI = i;
            animation.addFrame(0.2348, (batch, delta) -> {
                batch.begin();
                font.draw(batch, load.get(finalI), 10, Gdx.graphics.getHeight() - (int) (10 * ((finalI + 2) * 1.75)));
                batch.end();
            });
        }

        animation.addFrame(2, (batch, delta) -> {
            batch.begin();
            font.draw(batch, "Loading ...", 10, Gdx.graphics.getHeight() - (int) (10 * ((load.size() + 2) * 1.75)));
            batch.end();
        });

        animation.addFrame(1.0, (batch, delta) -> {
            MainGraphicalInterface.setCurrentScreen((Screen) Registries.getOrThrow(AssetRegistries.SCREENS).get(Screens.MAIN).unwrap());
        });
    });

    @Override
    public void show() {
        font.setColor(Color.GREEN);
        ((Registry<Sound>) (Object) Registries.getOrThrow(AssetRegistries.SOUNDS)).get(Sounds.LOADING_SOUND).unwrap().play(1.0f);
    }

    @Override
    public void render(final float delta) {
        this.animation.render(batch, delta);
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
        batch.dispose();
        texture.dispose();
    }
}
