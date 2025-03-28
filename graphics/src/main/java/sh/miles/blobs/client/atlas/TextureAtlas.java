package sh.miles.blobs.client.atlas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import sh.miles.blobs.client.atlas.animation.AtlasAnimation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@NullMarked
public class TextureAtlas {

    private final Texture texture;
    private final TextureAtlasData atlasData;
    private final Array<TextureRegion> regions;
    private final Map<Object, AtlasAnimation> animations;

    public TextureAtlas(String path, TextureAtlasData atlasData) {
        this.texture = new Texture(Gdx.files.internal(path));
        this.atlasData = atlasData;
        this.regions = new Array<>();
        this.animations = new HashMap<>(5);
        for (int y = 0; y < atlasData.fileHeight(); y += atlasData.height()) {
            for (int x = 0; x < atlasData.fileWidth(); x += atlasData.width()) {
                regions.add(new TextureRegion(texture, x, y, atlasData.width(), atlasData.height()));
            }
        }
    }

    public TextureAtlas modify(int insert, int row, Consumer<TextureRegion> mod) {
        int y = row * atlasData.height();
        int width = 0;
        for (int x = 0; x < atlasData.fileWidth(); x += atlasData.width()) {
            final var region = new TextureRegion(texture, x, y, atlasData.width(), atlasData.height());
            mod.accept(region);
            regions.insert(insert, region);
            width++;
        }

        return this;
    }

    public TextureAtlas addAnimation(Object key, AtlasAnimation animation) {
        this.animations.put(key, animation);
        return this;
    }

    @Nullable
    public AtlasAnimation getAnimation(Object key) {
        return this.animations.get(key);
    }

    @Nullable
    public TextureRegion region(int index) {
        return regions.get(index);
    }

    public int getRegionAmount() {
        return this.regions.size;
    }

    public void dispose() {
        this.texture.dispose();
    }

}
