package sh.miles.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.function.Consumer;

public class SpriteManager {
    public static int CHARACTERS = 0;

    private static final AtlasDimensions CHARACTER_DIMENSIONS = new AtlasDimensions(32, 32, 288, 480);

    private final Array<SimpleAtlas> atlases;

    SpriteManager() {
        this.atlases = new Array<>();
        atlases.add(new SimpleAtlas("characters/player.png", CHARACTER_DIMENSIONS));
    }

    public TextureRegion get(int atlas, int texture) {
        return atlases.get(atlas).region(texture);
    }

    public void use(int atlas, int texture, Consumer<TextureRegion> sprite) {
        sprite.accept(atlases.get(atlas).region(texture));
    }

    public void dispose() {
        for (final SimpleAtlas atlas : atlases) {
            atlas.dispose();
        }
    }

    private static class SimpleAtlas {

        private final Texture texture;
        private final Array<TextureRegion> sprites;

        SimpleAtlas(String path, AtlasDimensions dimensions) {
            this.texture = new Texture(Gdx.files.internal("sprites/knight.png"));
            this.sprites = new Array<>();
            for (int y = 0; y < dimensions.fileHeight; y += dimensions.height) {
                for (int x = 0; x < dimensions.fileWidth; x += dimensions.width) {
                    sprites.add(new TextureRegion(texture, x, y, dimensions.width, dimensions.height));
                }
            }
        }

        TextureRegion region(int index) {
            return sprites.get(index);
        }

        public void dispose() {
            this.texture.dispose();
        }
    }

    private record AtlasDimensions(int width, int height, int fileWidth, int fileHeight) {
    }
}
