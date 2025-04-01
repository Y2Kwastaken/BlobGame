package sh.miles.blobs.client.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public final class Textures {

    public static final Texture TILES = new Texture(Gdx.files.internal("tiles/tiles.png"));

    private Textures() {
    }

    public static Texture getFromId(String id) {
        try {
            return (Texture) Textures.class.getDeclaredField(id.toUpperCase()).get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dispose() {
        TILES.dispose();
    }

}
