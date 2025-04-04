package sh.miles.blobs.client.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import sh.miles.blobs.client.asset.Assets;

public class ClientLevel {

    private final OrthogonalTiledMapRenderer renderer;
    private final TiledMapTileSet set;
    private final int[] layer;

    public ClientLevel(String id, SpriteBatch batch, OrthographicCamera camera, float scale) {
        TiledMap map = new TiledMap();
        this.set = new TiledMapTileSet();
        Assets.CLIENT_TILE_TYPES.forEach((type, clientType) -> {
            set.putTile(clientType.getType().ordinal(), clientType.getTile());
        });
        map.getTileSets().addTileSet(set);

        this.renderer = new OrthogonalTiledMapRenderer(map, scale, batch);
        renderer.setView(camera.combined, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.layer = new int[]{0};
        final TiledMapTileLayer tiledLayer = new TiledMapTileLayer(100, 100, 16, 16);
        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
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
