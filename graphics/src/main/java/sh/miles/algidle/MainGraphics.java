package sh.miles.algidle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.jspecify.annotations.NonNull;
import sh.miles.algidle.screen.LoadingScreen;

public class MainGraphics extends Game {

    private SpriteBatch batch;
    private ScreenViewport viewport;
    private Camera camera;

    public MainGraphics() {
    }

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.viewport = new ScreenViewport(camera);
        viewport.apply();
        setScreen(new LoadingScreen(this));
    }

    @NonNull
    public SpriteBatch getBatch() {
        if (this.batch == null) throw new IllegalStateException("Attempted to fetch SpriteBatch before #create call");
        return this.batch;
    }

    @NonNull
    public ScreenViewport getViewport() {
        if (this.viewport == null) throw new IllegalStateException("Attempted to fetch Viewport before #create call");
        return viewport;
    }

    public Camera getCamera() {
        if (this.camera == null) throw new IllegalStateException("Attempted to fetch Camera before #create call");
        return camera;
    }
}
