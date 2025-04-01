package sh.miles.blobs.client.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import sh.miles.blobs.client.asset.SpriteSheet;
import sh.miles.blobs.client.asset.Textures;
import sh.miles.blobs.level.TileType;
import sh.miles.blobs.util.GDXAdapter;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.StreamSupport;

public final class ClientTileType {

    private final TileType type;
    private final TiledMapTile tile;
    private final UnaryOperator<TiledMapTile> tileCopier;

    private ClientTileType(final TileType type, final TiledMapTile tile, final UnaryOperator<TiledMapTile> tileCopier) {
        this.type = type;
        this.tile = tile;
        this.tileCopier = tileCopier;
    }

    public TileType getType() {
        return type;
    }

    public TiledMapTile getTile() {
        return this.tileCopier.apply(this.tile);
    }

    public ClientTileType create(TileType type, TiledMapTile tile, UnaryOperator<TiledMapTile> tileCopier) {
        return new ClientTileType(type, tile, tileCopier);
    }

    public static class Parser implements Json.Serializer<ClientTileType> {

        private static final UnaryOperator<TiledMapTile> STATIC = (toCopy) -> new StaticTiledMapTile((StaticTiledMapTile) toCopy);
        private static final UnaryOperator<TiledMapTile> ANIMATED = (toCopy) -> {
            final var animated = (AnimatedTiledMapTile) toCopy;
            return new AnimatedTiledMapTile(IntArray.with(animated.getAnimationIntervals()), GDXAdapter.copyFrom(animated.getFrameTiles()));
        };

        private final SpriteSheet sheet;
        private final Texture texture;

        public Parser(SpriteSheet sheet) {
            this.sheet = sheet;
            this.texture = Textures.getFromId(sheet.id());
        }

        @Override
        public void write(final Json json, final ClientTileType object, final Class knownType) {
            throw new IllegalArgumentException("Write Not Implemented");
        }

        @Override
        public ClientTileType read(final Json json, final JsonValue jsonData, final Class type) {
            final var tileType = TileType.valueOf(jsonData.get("tile_type").asString().toUpperCase());
            final var animation = jsonData.get("animation");
            final TiledMapTile tile;
            final UnaryOperator<TiledMapTile> copier;
            final String animationType = animation.getString("type");
            if (animationType.equals("static")) {
                tile = read(animation);
                copier = STATIC;
            } else if (animationType.equals("cycle")) {
                final float interval = animation.getFloat("interval");
                final List<StaticTiledMapTile> contents = StreamSupport.stream(animation.get("steps").spliterator(), false)
                        .map(this::read)
                        .toList();
                tile = new AnimatedTiledMapTile(interval, GDXAdapter.copyFrom(contents));
                copier = ANIMATED;
            } else {
                throw new IllegalArgumentException("No known tile animation type " + animationType);
            }

            return new ClientTileType(tileType, tile, copier);
        }

        private StaticTiledMapTile read(JsonValue data) {
            return new StaticTiledMapTile(sheet.createRegion(this.texture, data.getInt("x"), data.getInt("y")));
        }
    }
}
