package sh.miles.algidle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import org.jspecify.annotations.NullMarked;
import sh.miles.algidle.utils.collection.registry.RegistryKey;
import sh.miles.algidle.MainGraphics;
import sh.miles.algidle.animation.NaiveAnimation;
import sh.miles.algidle.texture.ResourcePack;

import java.util.List;

@NullMarked
public class LoadingScreen implements Screen {

    private final MainGraphics game;
    private final SpriteBatch batch;
    private final Texture texture = new Texture("libgdx.png");
    private final BitmapFont font = new BitmapFont();
    private final NaiveAnimation animation;

    public LoadingScreen(final MainGraphics game) {
        this.game = game;
        this.batch = game.getBatch();
        this.animation = new NaiveAnimation(Gdx.graphics.getDisplayMode().refreshRate).edit((animation) -> {
            final float width = Gdx.graphics.getWidth();
            final float height = Gdx.graphics.getHeight();

            animation.addFrame(1, (batch, delta) -> {
                ScreenUtils.clear(Color.BLACK);
                batch.begin();
                batch.draw(texture, (width - texture.getWidth()) / 2, (height - texture.getHeight()) / 2);
                batch.end();
            });

            animation.addFrame(1, (batch, delta) -> {
                ScreenUtils.clear(Color.BLACK);
                batch.begin();
                batch.setColor(Color.GREEN);
                font.draw(batch, "Starting AlgorithmOS...", 10, height - 10);
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
                    font.draw(batch, load.get(finalI), 10, height - (int) (10 * ((finalI + 2) * 1.75)));
                    batch.end();
                });
            }

            animation.addFrame(1, (batch, delta) -> {
                batch.begin();
                font.draw(batch, "Loading ...", 10, height - (int) (10 * ((load.size() + 2) * 1.75)));
                batch.end();
            });

            animation.addFrame(1.0, (batch, delta) -> {
                game.setScreen(new GameplayScreen(game));
            });
        });
    }

    @Override
    public void show() {
        this.font.setColor(Color.GREEN);
        ResourcePack.DEFAULT.playSound(RegistryKey.base("loading_screen"), 1.0F);
    }

    @Override
    public void render(final float delta) {
        game.getViewport().apply();
        batch.setProjectionMatrix(game.getCamera().combined);
        this.animation.render(this.batch, delta);
    }

    @Override
    public void resize(final int width, final int height) {
        game.getViewport().update(width, height, true);
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
