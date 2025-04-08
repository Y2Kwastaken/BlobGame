package sh.miles.blobs.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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

    public static float TILE_SIZE = 16.0f;
    public static int NUM_TILES_X = 10;
    public static int NUM_TILES_Y = 8;
    public static float UNIT_WIDTH = NUM_TILES_X * TILE_SIZE;
    public static float UNIT_HEIGHT = NUM_TILES_Y * TILE_SIZE;


    private Controls controls;
    private SpriteBatch batch;
    private LevelRenderer levelRenderer;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Entity player;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, NUM_TILES_X, NUM_TILES_Y);
        float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
        this.viewport = new FillViewport(UNIT_WIDTH * aspectRatio, UNIT_HEIGHT, camera);
        this.viewport.apply(true);

        float scaleX = (float) Gdx.graphics.getWidth() / UNIT_WIDTH;
        float scaleY = (float) Gdx.graphics.getHeight() / UNIT_HEIGHT;
        float scale = Math.min(scaleX, scaleY);

        this.levelRenderer = new LevelRenderer(this.batch, this.camera, 1 / 4f);
        this.levelRenderer.setLevel(GameRunner.GAME.getLevel());
        final var level = this.levelRenderer.getLevel();
        for (int y = 0; y < level.height; y++) {
            for (int x = 0; x < level.width; x++) {
                level.setType(x, y, TileType.GRASS);
            }
        }

        level.spawn(() -> Entity.create(EntityType.HUMANOID, (humanoid) -> {
            humanoid.set(EntityComponents.POSITION, new Position(50, 50));
            humanoid.set(EntityComponents.MOVEMENT_SPEED, 0.5f);
            humanoid.set(EntityComponents.VELOCITY, new VelocityComponent(DeepVector2.ZERO, 0.2f, VelocityComponent.NATURAL_DEGRADE));
        }));
        player = level.getEntity(0);
        camera.position.set(level.getEntity(0).get(EntityComponents.POSITION).toVector2(), 0);

        this.controls = new Controls(this);
        Gdx.input.setInputProcessor(controls);
        GameRunner.GAME.addPreTickHook(() -> {
            this.controls.tickInput();
        });
        GameRunner.RUNNER.start();
    }

    @Override
    public void resize(final int width, final int height) {
        viewport.update(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(this.camera.combined);
        camera.update();
        this.levelRenderer.render(batch);
    }

    @Override
    public void dispose() {
        GameRunner.RUNNER.stop();

        Textures.dispose();
    }

    public LevelRenderer getLevelRenderer() {
        return levelRenderer;
    }

    public OrthographicCamera getCamera() {
        return this.camera;
    }
}
