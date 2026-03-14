package sh.miles.blobs.client.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import sh.miles.blobs.client.asset.entity.EntityAnimations;
import sh.miles.blobs.client.asset.level.ClientTileType;
import sh.miles.blobs.api.entity.EntityType;
import sh.miles.blobs.api.level.TileType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class Assets {

    private static final Json JSON = new Json();
    public static final AssetMap<String, SpriteSheet> SHEETS;
    public static final AssetMap<TileType, ClientTileType> CLIENT_TILE_TYPES;
    public static final AssetMap<EntityType, EntityAnimations> ENTITY_ANIMATIONS;

    static {
        JSON.setSerializer(SpriteSheet.class, new SpriteSheet.Parser());
        SHEETS = parse("sheets.json", SpriteSheet[].class, SpriteSheet::id);
        final var sheet = SHEETS.get("tiles");
        JSON.setSerializer(ClientTileType.class, new ClientTileType.Parser(sheet));
        JSON.setSerializer(EntityAnimations.class, new EntityAnimations.Parser(SHEETS));
        CLIENT_TILE_TYPES = parse(sheet.json(), ClientTileType[].class, ClientTileType::getType);
        ENTITY_ANIMATIONS = aggregateParse("entities/entities.json", EntityAnimations.class, EntityAnimations::getType);
    }

    private static void parse(String path) {
        JSON.fromJson(SpriteSheet[].class, Gdx.files.internal(path));
    }

    private static <K, V> AssetMap<K, V> parse(String path, Class<V[]> type, Function<V, K> keyGetter) {
        final var result = JSON.fromJson(type, Gdx.files.internal(path));
        final var assetMap = new AssetMap<K, V>();
        for (final V value : result) {
            assetMap.put(keyGetter.apply(value), value);
        }

        return assetMap;
    }

    private static <K, V> AssetMap<K, V> aggregateParse(String aggregate, Class<V> type, Function<V, K> keyGetter) {
        final String[] aggregateFiles = JSON.fromJson(String[].class, Gdx.files.internal(aggregate));
        final var assetMap = new AssetMap<K, V>();
        for (final String file : aggregateFiles) {
            final V value = JSON.fromJson(type, Gdx.files.internal(file));
            assetMap.put(keyGetter.apply(value), value);
        }

        return assetMap;
    }

    public static class AssetMap<K, V> {
        private final Map<K, V> assets = new HashMap<>();

        private void put(K key, V value) {
            assets.put(key, value);
        }

        public V get(K key) {
            final var got = this.assets.get(key);
            if (got == null) {
                throw new IllegalArgumentException("no value with key " + key);
            }
            return got;
        }

        public void forEach(BiConsumer<? super K, ? super V> action) {
            assets.forEach(action);
        }
    }

}
