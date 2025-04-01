package sh.miles.blobs.client.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public record SpriteSheet(String id, String rawPath, int width, int height, int spriteSize) {

    public String json() {
        return rawPath + "json";
    }

    public String png() {
        return rawPath + "png";
    }

    public TextureRegion createRegion(Texture texture, int x, int y) {
        return new TextureRegion(texture, x * spriteSize, y * spriteSize, spriteSize, spriteSize);
    }

    public static SpriteSheet fromAbsolutes(String id, String path, int imageWidth, int imageHeight, int spriteWidthHeight) {
        return new SpriteSheet(id, path, imageWidth / spriteWidthHeight, imageHeight / spriteWidthHeight, spriteWidthHeight);
    }

    public static class Parser implements Json.Serializer<SpriteSheet> {
        @Override
        public void write(final Json json, final SpriteSheet object, final Class knownType) {
            throw new IllegalArgumentException("Write Not Implemented");
        }

        @Override
        public SpriteSheet read(final Json json, final JsonValue jsonData, final Class type) {
            final var id = jsonData.get("id").asString();
            final var path = jsonData.get("path").asString();
            final var spriteSize = jsonData.get("sprite_size").asInt();
            final var width = jsonData.get("sheet_width").asInt();
            final var height = jsonData.get("sheet_height").asInt();
            return SpriteSheet.fromAbsolutes(id, path, width, height, spriteSize);
        }
    }
}
