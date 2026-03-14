package sh.miles.blobs.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import sh.miles.blobs.HeadlessGame;
import sh.miles.blobs.api.dto.game.GameDebugDTO;
import sh.miles.blobs.api.dto.game.GameEntityDTO;
import sh.miles.blobs.api.dto.game.GameLevelDTO;
import sh.miles.blobs.client.input.Controls;
import sh.miles.blobs.client.level.ClientLevel;
import sh.miles.blobs.client.network.local.ClientLocalListener;
import sh.miles.blobs.client.render.DebugRender;
import sh.miles.blobs.client.render.EntityAnimationSystem;
import sh.miles.blobs.client.render.LevelRender;
import sh.miles.blobs.client.util.RenderDebugSettings;
import sh.miles.blobs.common.Constants;
import sh.miles.blobs.common.TickableRunner;

public class GameRender extends ApplicationAdapter {

    public static final int TARGET_TILES_X = 20;
    public static final int TARGET_TILES_Y = 15;
    public static final float WORLD_WIDTH = TARGET_TILES_X * Constants.TILE_SIZE;
    public static final float WORLD_HEIGHT = TARGET_TILES_Y * Constants.TILE_SIZE;
    public static final RenderDebugSettings RDSET = new RenderDebugSettings();

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private OrthographicCamera camera;
    private OrthographicCamera hud;
    private ScreenViewport viewport;
    private Viewport hudViewport;

    private HeadlessGame game;
    private TickableRunner runner;
    public LevelRender levelRender;
    public DebugRender debugRender;
    private EntityAnimationSystem entityAnimationSystem;
    private Controls controls;

    // Frontend State Caches
    public ClientLevel clientLevel;
    public GameLevelDTO.LevelSnapshotDTO latestSnapshot;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.font = new BitmapFont();
        this.camera = new OrthographicCamera();
        this.hud = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.hud.position.set(hud.viewportWidth / 2.0f, hud.viewportHeight / 2.0f, 1.0f);
        this.hudViewport = new ScreenViewport(this.hud);
        this.viewport = new ScreenViewport(camera);
        viewport.setUnitsPerPixel(0.25f);
        this.viewport.apply();
        this.controls = new Controls();

        this.levelRender = new LevelRender(this.batch, 1f);
        this.debugRender = new DebugRender();
        this.entityAnimationSystem = new EntityAnimationSystem();

        ClientLocalListener.registerAllLocalListeners(this);

        // Headless Game Hook
        this.game = new HeadlessGame();
        this.runner = new TickableRunner(this.game);
        this.runner.start();
        // init sample world
    }

    private void updateCamera() {
        if (clientLevel == null || latestSnapshot == null) {
            return;
        }

        final GameEntityDTO.EntitySnapshotDTO player = this.latestSnapshot.activeEntities().getFirst();

        camera.position.set(player.x() * 16f, player.y() * 16f, 0);

        float mapWidthPixels = clientLevel.width * 16f; // Constants.TILE_SIZE
        float mapHeightPixels = clientLevel.height * 16f;

        float camHalfWidth = camera.viewportWidth / 2f;
        float camHalfHeight = camera.viewportHeight / 2f;

        float minX = camHalfWidth;
        float minY = camHalfHeight;

        float maxX = Math.max(minX, mapWidthPixels - camHalfWidth);
        float maxY = Math.max(minY, mapHeightPixels - camHalfHeight);

        camera.position.x = MathUtils.clamp(camera.position.x, minX, maxX);
        camera.position.y = MathUtils.clamp(camera.position.y, minY, maxY);

        camera.update();
    }

    @Override
    public void render() {
        this.controls.tickInput();
        updateCamera();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        this.levelRender.render(batch, camera);
        if (this.latestSnapshot != null) {
            this.entityAnimationSystem.render(batch, this.latestSnapshot);
        }

        hudViewport.apply();
        batch.setProjectionMatrix(hud.combined);
        batch.begin();
        if (RDSET.showDiagnostics.observe()) {
            font.draw(batch, "FPS=" + Gdx.graphics.getFramesPerSecond(), 10, hudViewport.getWorldHeight() - 10);
            font.draw(batch, "TPS=" + this.runner.lastTps, 10, hudViewport.getWorldHeight() - 10 - font.getLineHeight());
        }
        batch.end();

        this.debugRender.render(this.batch, this.camera);
        if (RDSET.showHitboxes.observe() && this.latestSnapshot != null) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            for (GameEntityDTO.EntitySnapshotDTO entity : this.latestSnapshot.activeEntities()) {
                float drawX = (entity.hitboxX()) * 16f;
                float drawY = (entity.hitboxY()) * 16f;
                float drawWidth = entity.hitboxWidth() * 16f;
                float drawHeight = entity.hitboxHeight() * 16f;

                shapeRenderer.setColor(Color.RED);
                shapeRenderer.rect(drawX, drawY, drawWidth, drawHeight);
            }
            shapeRenderer.end();
        }
//
        if (RDSET.showLocation.observe() && this.latestSnapshot != null) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

            for (final GameEntityDTO.EntitySnapshotDTO entity : this.latestSnapshot.activeEntities()) {
                float drawX = (entity.x() * 16f);
                float drawY = (entity.y() * 16f);
                float drawWidth = 1;
                float drawHeight = 1;
                shapeRenderer.setColor(Color.GREEN);
                shapeRenderer.rect(drawX, drawY, drawWidth, drawHeight);
            }
            shapeRenderer.end();
        }
//
//        if (RDSET.showAttackSwing.observe() && this.latestAttackBounds != null) {
//            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//            for (final GameEntityDTO.EntitySnapshotDTO entity : this.latestSnapshot.activeEntities()) {
//                if (entity.entityId() != latestAttackBounds.entityId()) {
//                    continue;
//                }
//
//                float drawX = (latestAttackBounds.x()) * 16f;
//                float drawY = (latestAttackBounds.y()) * 16f;
//                float drawWidth = latestAttackBounds.width() * 16f;
//                float drawHeight = latestAttackBounds.height() * 16f;
//
//                shapeRenderer.setColor(Color.GREEN);
//                shapeRenderer.rect(drawX, drawY, drawWidth, drawHeight);
//            }
//            shapeRenderer.end();
//        }
//
//        if (RDSET.showMiscDraws.observe() && this.latestBoundingBoxDraw != null) {
//            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//
//            float drawX = latestBoundingBoxDraw.x() * 16f;
//            float drawY = latestBoundingBoxDraw.y() * 16f;
//            float drawWidth = latestBoundingBoxDraw.width() * 16f;
//            float drawHeight = latestBoundingBoxDraw.height() * 16f;
//
//
//            shapeRenderer.setColor(Color.BLACK);
//            shapeRenderer.rect(drawX, drawY, drawWidth, drawHeight);
//            shapeRenderer.end();
//        }
    }

    @Override
    public void resize(final int width, final int height) {
        viewport.update(width, height);
        hudViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.runner.stop();
    }
}
