package sh.miles.blobs.client.entity.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;
import sh.miles.blobs.client.atlas.BlobsTextures;
import sh.miles.blobs.client.atlas.animation.AtlasAnimation;
import sh.miles.blobs.entity.Entity;
import sh.miles.blobs.entity.animation.EntityAnimationType;
import sh.miles.blobs.entity.components.EntityComponents;

import java.util.List;

public final class EntityAnimationSystem {

    private static final IntMap<AnimWrap> progress = new IntMap<>();

    private EntityAnimationSystem() {
    }

    public static TextureRegion getCurrentFrameFor(int id) {
        final var current = progress.get(id);
        if (current == null) return null;
        return current.animation.getFrame(current.stateTime);
    }

    public static void update(List<Entity> entities) {
        for (final Entity entity : entities) {
            final var atlas = BlobsTextures.getAtlasFor(entity.get(EntityComponents.ENTITY_TYPE));
            final var id = entity.id;
            final var current = progress.get(id);
            final var animation = entity.get(EntityComponents.ANIMATION);

            if (current == null) {
                progress.put(id, new AnimWrap(animation, atlas.getAnimation(animation)));
                return;
            }

            if (current.type != animation) {
                progress.put(id, new AnimWrap(animation, atlas.getAnimation(animation)));
                return;
            }

            current.stateTime += Gdx.graphics.getDeltaTime();
            current.stateTime %= current.animation.getLength();
        }
    }

    private static class AnimWrap {
        float stateTime;
        EntityAnimationType type;
        AtlasAnimation animation;

        public AnimWrap(EntityAnimationType type, AtlasAnimation animation) {
            this.type = type;
            this.animation = animation;
            this.stateTime = 0.0F;
        }
    }
}
