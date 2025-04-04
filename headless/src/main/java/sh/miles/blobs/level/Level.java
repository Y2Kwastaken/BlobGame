package sh.miles.blobs.level;

import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import org.jspecify.annotations.Nullable;
import sh.miles.blobs.Constants;
import sh.miles.blobs.util.ArrayUtils;
import sh.miles.blobs.util.tile.ServerCell;

import java.util.Random;

public final class Level {

    private static final Random RANDOM = new Random();

    public final int width;
    public final int height;
    private final TiledMapTileLayer[] layers;
    public int currentLayer;

    public Level(int width, int height, int layers) {
        this.width = width;
        this.height = height;
        this.currentLayer = 0;
        this.layers = new TiledMapTileLayer[layers];
        ArrayUtils.fill(this.layers, () -> new TiledMapTileLayer(width, height, Constants.TILE_WIDTH, Constants.TILE_HEIGHT));
    }

    public void tick() {
        setType(RANDOM.nextInt(0, width + 1), RANDOM.nextInt(0, height + 1), TileType.id(RANDOM.nextInt(0, 3)));
    }

    public void setType(int x, int y, TileType type) {
        final var layer = this.layers[this.currentLayer];
        final var cell = layer.getCell(x, y);
        if (cell instanceof ServerCell serverCell) {
            serverCell.setId(type.ordinal());
            return;
        }

        layer.setCell(x, y, new ServerCell(type.ordinal()));
    }

    @Nullable
    public TileType getType(int x, int y) {
        final var layer = this.layers[this.currentLayer];
        final var cell = layer.getCell(x, y);
        if (cell instanceof ServerCell serverCell) {
            return TileType.id(serverCell.getId());
        }

        return null;
    }

    public void addLayers(MapLayers layers) {
        for (final TiledMapTileLayer layer : this.layers) {
            layers.add(layer);
        }
    }
}
