package sh.miles.algidle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sh.miles.algidle.assets.Screens;
import sh.miles.algidle.registry.Registries;

public class MainGraphicalInterface extends Game {

    private static MainGraphicalInterface INSTANCE;
    public static SpriteBatch BATCH;

    private SpriteBatch batch;
    private Texture image;

    @Override
    public void create() {
        INSTANCE = this;
        BATCH = new SpriteBatch();
        image = new Texture("libgdx.png");
        AssetRegistries.initialize();

        setScreen((Screen) Registries.getOrThrow(AssetRegistries.SCREENS).get(Screens.LOADING).unwrap());
    }

    public static void setCurrentScreen(Screen screen) {
        INSTANCE.setScreen(screen);
    }
}
