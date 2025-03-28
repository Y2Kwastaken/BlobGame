package sh.miles.blobs.client.atlas.animation;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import sh.miles.blobs.client.atlas.TextureAtlas;

import java.util.Objects;
import java.util.stream.IntStream;

public class AtlasAnimation {

    private final Animation<TextureRegion> animation;

    public AtlasAnimation(final TextureAtlas atlas, float frameDuration, int[] frames) {
        final Array<TextureRegion> keyFrames = new Array<>(frames.length);
        for (final int frame : frames) {
            keyFrames.add(atlas.region(frame));
        }
        this.animation = new Animation<>(frameDuration, keyFrames, Animation.PlayMode.LOOP);
    }

    public AtlasAnimation(TextureAtlas atlas, float frameDuration, IntStream frames) {
        this(atlas, frameDuration, frames.toArray());
    }

    public float getLength() {
        return animation.getAnimationDuration();
    }

    public TextureRegion getFrame(float stateTime) {
        return animation.getKeyFrame(stateTime);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final AtlasAnimation that)) return false;
        return Objects.equals(animation, that.animation);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(animation);
    }
}
