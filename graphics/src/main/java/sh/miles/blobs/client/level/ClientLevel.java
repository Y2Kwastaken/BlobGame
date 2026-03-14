package sh.miles.blobs.client.level;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import sh.miles.blobs.common.Constants;
import sh.miles.blobs.api.dto.game.GameLevelDTO;
import sh.miles.blobs.api.event.GameEvents;
import sh.miles.blobs.api.level.TileType;
import sh.miles.blobs.client.asset.Assets;

public class ClientLevel {
    public int width;
    public int height;

    private final TiledMapTileLayer[] graphicsLayers;

    public ClientLevel(int width, int height, int[][] tiles) {
        this.width = width;
        this.height = height;

        this.graphicsLayers = new TiledMapTileLayer[2];
        this.graphicsLayers[0] = new TiledMapTileLayer(
            width, height,
            Constants.TILE_SIZE, Constants.TILE_SIZE
        );

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                updateVisualTile(x, y, tiles[x][y]);
            }
        }

    }

    public void populateMap(TiledMap map) {
        var iter = map.getLayers().iterator();
        while (iter.hasNext()) {
            iter.remove();
        }

        for (TiledMapTileLayer layer : graphicsLayers) {
            if (layer != null) {
                map.getLayers().add(layer);
            }
        }
    }

    public void updateVisualTile(int x, int y, int ordinal) {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(Assets.CLIENT_TILE_TYPES.get(TileType.id(ordinal)).getTile());
        graphicsLayers[0].setCell(x, y, cell);
    }

    public void updateVisualTile(GameLevelDTO.TileUpdateDTO data) {
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(Assets.CLIENT_TILE_TYPES.get(data.newType()).getTile());
        this.graphicsLayers[0].setCell(data.x(), data.y(), cell);
    }

}
