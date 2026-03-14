package sh.miles.blobs.client.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import sh.miles.blobs.client.asset.Assets;
import sh.miles.blobs.client.level.ClientLevel;

public class LevelRender {

    private ClientLevel clientLevel;
    private final TiledMap tiledMap;
    private final OrthogonalTiledMapRenderer renderer;
    public int[] toRender;

    public LevelRender(SpriteBatch batch, float unitScale) {
        this.tiledMap = new TiledMap();
        tiledMap.getTileSets().addTileSet(initTileSet());
        this.renderer = new OrthogonalTiledMapRenderer(this.tiledMap, unitScale, batch);
        this.toRender = new int[1];

        // Various Listeners
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        if (this.clientLevel == null) return;
        renderer.setView(camera);
        this.renderer.render(this.toRender);
    }

    public void dispose() {
        this.renderer.dispose();
        this.tiledMap.dispose();
    }

    public void clientLevel(final ClientLevel clientLevel) {
        this.clientLevel = clientLevel;
        this.clientLevel.populateMap(this.tiledMap);
        this.toRender[0] = 0;
    }

    public ClientLevel clientLevel() {
        return clientLevel;
    }

    private TiledMapTileSet initTileSet() {
        final TiledMapTileSet set = new TiledMapTileSet();
        set.setName("Tiles");

        Assets.CLIENT_TILE_TYPES.forEach((type, clientType) -> {
            set.putTile(clientType.getType().ordinal(), clientType.getTile());
        });
        return set;
    }

}
