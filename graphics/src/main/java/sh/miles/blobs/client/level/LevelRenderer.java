package sh.miles.blobs.client.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import sh.miles.blobs.client.asset.Assets;
import sh.miles.blobs.events.GameEvents;
import sh.miles.blobs.level.Level;
import sh.miles.blobs.util.tile.ServerCell;

public final class LevelRenderer {

    private Level level;
    private boolean isSetUp;

    private final OrthogonalTiledMapRenderer renderer;
    public int[] toRender;

    public LevelRenderer(SpriteBatch batch, OrthographicCamera camera, float scale) {
        final TiledMap tiledMap = new TiledMap();
        final TiledMapTileSet set = new TiledMapTileSet();
        Assets.CLIENT_TILE_TYPES.forEach((type, clientType) -> {
            set.putTile(clientType.getType().ordinal(), clientType.getTile());
        });
        tiledMap.getTileSets().addTileSet(set);

        this.renderer = new OrthogonalTiledMapRenderer(tiledMap, scale, batch);
        this.renderer.setView(camera.combined, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.isSetUp = false;
        this.toRender = new int[1];

        GameEvents.listen(GameEvents.SERVER_CELL_SET, (objects) -> {
            final var cell = (ServerCell) objects[0];
            cell.setTile(set.getTile(cell.getId()));
        });
    }

    public void render() {
        if (this.level == null) return;
        if (!this.isSetUp) {
            this.level.addLayers(this.renderer.getMap().getLayers());
            this.toRender[0] = this.level.currentLayer;
            this.isSetUp = true;
        }

//        System.out.println(((ServerCell) ((TiledMapTileLayer) this.renderer.getMap().getLayers().get(0)).getCell(0, 0)).getId());
        this.renderer.render(this.toRender);
    }

    public void setLevel(final Level level) {
        this.level = level;
        isSetUp = false;
    }

    public Level getLevel() {
        return level;
    }
}
