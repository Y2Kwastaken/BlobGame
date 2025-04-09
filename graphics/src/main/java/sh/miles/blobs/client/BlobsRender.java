package sh.miles.blobs.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import sh.miles.blobs.Constants;
import sh.miles.blobs.GameRunner;
import sh.miles.blobs.client.asset.Textures;
import sh.miles.blobs.client.input.Controls;
import sh.miles.blobs.client.level.LevelRenderer;
import sh.miles.blobs.entity.Entity;
import sh.miles.blobs.entity.EntityType;
import sh.miles.blobs.entity.component.EntityComponents;
import sh.miles.blobs.entity.component.VelocityComponent;
import sh.miles.blobs.level.TileType;
import sh.miles.blobs.level.position.Position;
import sh.miles.blobs.util.gdx.DeepVector2;

public class BlobsRender extends ApplicationAdapter {

    public static final int TARGET_TILES_X = 20;
    public static final int TARGET_TILES_Y = 15;
    public static final float WORLD_WIDTH = TARGET_TILES_X * Constants.TILE_SIZE;
    public static final float WORLD_HEIGHT = TARGET_TILES_Y * Constants.TILE_SIZE;


    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;

    private LevelRenderer levelRenderer;
    private Controls controls;
    private Entity player;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.viewport = new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        this.viewport.apply();

        this.levelRenderer = new LevelRenderer(this.batch, 1f);
        final var level = GameRunner.GAME.getLevel();
        this.levelRenderer.setLevel(level);

        for (int y = 0; y < level.height; y++) {
            for (int x = 0; x < level.width; x++) {
                level.setType(x, y, TileType.GRASS);
            }
        }

        float initialPlayerX = 5 * Constants.TILE_SIZE;
        float initialPlayerY = 5 * Constants.TILE_SIZE;
        level.spawn(() -> Entity.create(EntityType.HUMANOID, (humanoid) -> {
            humanoid.set(EntityComponents.LEVEL, level);
            humanoid.set(EntityComponents.POSITION, new Position(0, 0));
            humanoid.set(EntityComponents.MOVEMENT_SPEED, 1f);
            humanoid.set(EntityComponents.VELOCITY, new VelocityComponent(DeepVector2.ZERO, 0.2f, VelocityComponent.NATURAL_DEGRADE));
        }));
        player = level.getEntity(0);

        Position playerPos = player.get(EntityComponents.POSITION);
        camera.position.set(playerPos.x() * 16, playerPos.y() * 16, 0);
        camera.update(); // Apply camera position change

        this.controls = new Controls(this);
        Gdx.input.setInputProcessor(controls);

        GameRunner.GAME.addPreTickHook(() -> {
            this.controls.tickInput();
            updateCamera();
        });

        GameRunner.RUNNER.start();
    }

    private void updateCamera() {
        if (player != null) {
            Position playerPos = player.get(EntityComponents.POSITION);
            camera.position.set(playerPos.x() * 16, playerPos.y() * 16, 0);
            camera.update();
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        this.levelRenderer.render(batch, camera);
    }

    //    @Override
//    public void create() {
//        this.batch = new SpriteBatch();
//        this.camera = new OrthographicCamera();
//        camera.setToOrtho(false, NUM_TILES_X, NUM_TILES_Y);
//        float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
//        this.viewport = new FillViewport(UNIT_WIDTH * aspectRatio, UNIT_HEIGHT, camera);
//        this.viewport.apply(true);
//
//        float scaleX = (float) Gdx.graphics.getWidth() / UNIT_WIDTH;
//        float scaleY = (float) Gdx.graphics.getHeight() / UNIT_HEIGHT;
//        float scale = Math.min(scaleX, scaleY);
//
//        this.levelRenderer = new LevelRenderer(this.batch, this.camera, 1 / 4f);
//        this.levelRenderer.setLevel(GameRunner.GAME.getLevel());
//        final var level = this.levelRenderer.getLevel();
//        for (int y = 0; y < level.height; y++) {
//            for (int x = 0; x < level.width; x++) {
//                level.setType(x, y, TileType.GRASS);
//            }
//        }
//
//        level.spawn(() -> Entity.create(EntityType.HUMANOID, (humanoid) -> {
//            humanoid.set(EntityComponents.POSITION, new Position(50, 50));
//            humanoid.set(EntityComponents.MOVEMENT_SPEED, 0.5f);
//            humanoid.set(EntityComponents.VELOCITY, new VelocityComponent(DeepVector2.ZERO, 0.2f, VelocityComponent.NATURAL_DEGRADE));
//        }));
//        player = level.getEntity(0);
//        camera.position.set(level.getEntity(0).get(EntityComponents.POSITION).toVector2(), 0);
//
//        this.controls = new Controls(this);
//        Gdx.input.setInputProcessor(controls);
//        GameRunner.GAME.addPreTickHook(() -> {
//            this.controls.tickInput();
//        });
//        GameRunner.RUNNER.start();
//    }

    @Override
    public void resize(final int width, final int height) {
        viewport.update(width, height);
    }

//    @Override
//    public void render() {
//        Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.setProjectionMatrix(this.camera.combined);
//        camera.update();
//        this.levelRenderer.render(batch);
//    }

    @Override
    public void dispose() {
        GameRunner.RUNNER.stop();

        this.batch.dispose();
        this.levelRenderer.dispose();
        Textures.dispose();
    }

    public LevelRenderer getLevelRenderer() {
        return levelRenderer;
    }

    public OrthographicCamera getCamera() {
        return this.camera;
    }
}
