package sh.miles.blobs.client.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import sh.miles.blobs.client.asset.BasicRenderer;
import sh.miles.blobs.client.asset.structures.TimedDrawingList;

public class DebugRender {

    private final ShapeRenderer renderer;
    private final TimedDrawingList<BasicRenderer<ShapeRenderer>, ShapeRenderer> debugDrawings = new TimedDrawingList<>();

    public DebugRender() {
        this.renderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        this.renderer.setProjectionMatrix(camera.combined);
        this.renderer.begin(ShapeType.Line);
        this.debugDrawings.draw(this.renderer);
        this.renderer.end();
    }

    public void queueScaledBox(long lengthMiliseconds, Color color, float x, float y, float width, float height) {
        this.debugDrawings.add(lengthMiliseconds, new BoxRender(color, x * 16f, y * 16f, width * 16f, height * 16f));
    }

    private record BoxRender(Color color, float x, float y, float width, float height) implements BasicRenderer<ShapeRenderer> {
        @Override
        public void draw(final ShapeRenderer provider) {
            provider.setColor(color);
            provider.rect(x, y, width, height);
        }
    }

}
