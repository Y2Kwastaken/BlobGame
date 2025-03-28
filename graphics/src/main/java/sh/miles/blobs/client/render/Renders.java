package sh.miles.blobs.client.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import sh.miles.blobs.client.entity.animation.EntityAnimationSystem;
import sh.miles.blobs.entity.Entity;
import sh.miles.blobs.entity.components.EntityComponents;

import java.util.List;

public final class Renders {

    private Renders() {
    }

    public static void entities(SpriteBatch batch, List<Entity> entities) {
        for (final Entity entity : entities) {
            var position = entity.get(EntityComponents.POSITION);
            var animation = entity.get(EntityComponents.ANIMATION);
            if (position != null && animation != null) {
                TextureRegion region = EntityAnimationSystem.getCurrentFrameFor(entity.id);
                if (region != null) {
                    batch.draw(region, position.x, position.y);
                }
            }
        }
    }

}
