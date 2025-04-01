package sh.miles.blobs.client.level;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import sh.miles.blobs.client.asset.Assets;
import sh.miles.blobs.level.TileType;

import java.util.Arrays;

public class ClientLevel {

    private final OrthogonalTiledMapRenderer renderer;
    private final int[] layer;

    public ClientLevel(String id, SpriteBatch batch, OrthographicCamera camera, float scale) {
        TiledMap map = new TiledMap();
        final TiledMapTileSet set = new TiledMapTileSet();
        Assets.CLIENT_TILE_TYPES.forEach((type, clientType) -> {
            set.putTile(clientType.getType().ordinal(), clientType.getTile());
        });
        map.getTileSets().addTileSet(set);

        this.renderer = new OrthogonalTiledMapRenderer(map, scale, batch);
        renderer.setView(camera);

        this.layer = new int[]{0};
        final TiledMapTileLayer tiledLayer = new TiledMapTileLayer(1000, 1000, 16, 16);
        for (int y = 0; y < 1000; y++) {
            for (int x = 0; x < 1000; x++) {
                final var cell = new TiledMapTileLayer.Cell();
                cell.setTile(set.getTile(2));
                tiledLayer.setCell(x, y, cell);
            }
        }

        map.getLayers().add(tiledLayer);
    }

    public void render() {
        this.renderer.render(this.layer);
    }

}
