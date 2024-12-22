package sh.miles.algidle.animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.jspecify.annotations.NullMarked;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@NullMarked
public final class NaiveAnimation {

    private final Map<Integer, RenderStep> frames;
    private int fps;
    private int nextFrameAt = 0;
    private int currentFrame = 0;

    public NaiveAnimation(int framesPerSecond) {
        this.frames = new HashMap<>();
        this.fps = framesPerSecond;
    }

    public NaiveAnimation edit(Consumer<NaiveAnimation> edit) {
        edit.accept(this);
        return this;
    }

    public void addFrame(double seconds, RenderStep step) {
        this.frames.put(this.nextFrameAt, step);
        this.nextFrameAt += (int) (seconds * Math.max(1, this.fps));
    }

    public void render(final SpriteBatch batch, final double delta) {
        final RenderStep renderStep = frames.get(currentFrame);
        if (renderStep != null) {
            renderStep.render(batch, delta);
        }

        this.currentFrame++;
    }

    public void reset() {
        this.currentFrame = 0;
    }

    public void setFps(final int fps) {
        this.fps = fps;
    }

    @NullMarked
    public interface RenderStep {
        void render(final SpriteBatch batch, final double delta);
    }
}
