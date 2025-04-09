package sh.miles.blobs.client.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import sh.miles.blobs.client.BlobsRender;
import sh.miles.blobs.client.asset.Assets;
import sh.miles.blobs.client.entity.EntityAnimationSystem;
import sh.miles.blobs.entity.Entity;
import sh.miles.blobs.events.GameEvents;
import sh.miles.blobs.level.Level;
import sh.miles.blobs.util.tile.ServerCell;

public final class LevelRenderer {

    private Level level;
    private boolean isSetUp;

    private final OrthogonalTiledMapRenderer renderer;
    public int[] toRender;

    private final EntityAnimationSystem entityAnimationSystem = new EntityAnimationSystem();

    public LevelRenderer(SpriteBatch batch, float unitScale) {
        final TiledMap tiledMap = new TiledMap();
        final TiledMapTileSet set = new TiledMapTileSet();
        set.setName("Tiles");

        Assets.CLIENT_TILE_TYPES.forEach((type, clientType) -> {
            set.putTile(clientType.getType().ordinal(), clientType.getTile());
        });
        tiledMap.getTileSets().addTileSet(set);

        this.renderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale, batch);

        this.isSetUp = false;
        this.toRender = new int[1]; // holds the index of the layer to render

        GameEvents.listen(GameEvents.SERVER_CELL_SET, (objects) -> {
            final var cell = (ServerCell) objects[0];
            cell.setTile(set.getTile(cell.getId()));
        });
        GameEvents.listen(GameEvents.ENTITY_SPAWN, (objects) -> {
            final var entity = (Entity) objects[1];
            this.entityAnimationSystem.spawn(entity);
        });
        GameEvents.listen(GameEvents.ENTITY_DESPAWN, (objects) -> {
            final var entity = (Entity) objects[1];
            this.entityAnimationSystem.despawn(entity);
        });
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        if (this.level == null) return;
        if (!this.isSetUp) {
            if (renderer.getMap().getLayers().getCount() == 0 && level.width > 0 && level.height > 0) {
                this.level.addLayers(this.renderer.getMap().getLayers());
                this.toRender[0] = this.level.currentLayer;
                this.isSetUp = true;
            }
        }

        renderer.setView(camera);
        this.renderer.render(this.toRender);
        this.entityAnimationSystem.render(batch, this.level);
    }

    public void dispose() {
        this.renderer.dispose();
    }

//    public LevelRenderer(SpriteBatch batch, OrthographicCamera camera, float scale) {
//        final TiledMap tiledMap = new TiledMap();
//        final TiledMapTileSet set = new TiledMapTileSet();
//        Assets.CLIENT_TILE_TYPES.forEach((type, clientType) -> {
//            set.putTile(clientType.getType().ordinal(), clientType.getTile());
//        });
//        tiledMap.getTileSets().addTileSet(set);
//
//        this.renderer = new OrthogonalTiledMapRenderer(tiledMap, scale, batch);
//        this.renderer.setView(camera.combined, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//
//        this.isSetUp = false;
//        this.toRender = new int[1];
//
//        GameEvents.listen(GameEvents.SERVER_CELL_SET, (objects) -> {
//            final var cell = (ServerCell) objects[0];
//            cell.setTile(set.getTile(cell.getId()));
//        });
//        GameEvents.listen(GameEvents.ENTITY_SPAWN, (objects) -> {
//            final var entity = (Entity) objects[1];
//            this.entityAnimationSystem.spawn(entity);
//        });
//        GameEvents.listen(GameEvents.ENTITY_DESPAWN, (objects) -> {
//            final var entity = (Entity) objects[1];
//            this.entityAnimationSystem.despawn(entity);
//        });
//    }

//    public void render(SpriteBatch batch) {
//        if (this.level == null) return;
//        if (!this.isSetUp) {
//            this.level.addLayers(this.renderer.getMap().getLayers());
//            this.toRender[0] = this.level.currentLayer;
//            this.isSetUp = true;
//        }
//
//        this.renderer.render(this.toRender);
//        this.entityAnimationSystem.render(batch, this.level);
//    }

    public void setLevel(final Level level) {
        this.level = level;
        isSetUp = false;
    }

    public Level getLevel() {
        return level;
    }
}
