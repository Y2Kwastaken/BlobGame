package sh.miles.blobs.client.atlas;

import sh.miles.blobs.client.atlas.animation.AtlasAnimation;
import sh.miles.blobs.client.entity.animation.EntityAnimationSystem;
import sh.miles.blobs.entity.EntityType;
import sh.miles.blobs.entity.animation.EntityAnimationType;

import java.util.stream.IntStream;

import static java.util.stream.IntStream.rangeClosed;

public final class BlobsTextures {

    public static final TextureAtlas CHARACTER = new TextureAtlas("characters/player.png", new TextureAtlasData(48, 48, 288, 480))
            .modify(12, 1, (b) -> b.flip(true, false))
            .modify(36, 4, (b) -> b.flip(true, false));
    public static final TextureAtlas SLIME = new TextureAtlas("characters/slime.png", new TextureAtlasData(32, 32, 224, 416));

    private static final float FRAME_DURATION = 0.25F;

    static {
        CHARACTER
                .addAnimation(EntityAnimationType.IDLE_SOUTH, new AtlasAnimation(CHARACTER, FRAME_DURATION, rangeClosed(0, 5)))
                .addAnimation(EntityAnimationType.IDLE_EAST, new AtlasAnimation(CHARACTER, FRAME_DURATION, rangeClosed(6, 11)))
                .addAnimation(EntityAnimationType.IDLE_WEST, new AtlasAnimation(CHARACTER, FRAME_DURATION, rangeClosed(12, 17)))
                .addAnimation(EntityAnimationType.IDLE_NORTH, new AtlasAnimation(CHARACTER, FRAME_DURATION, rangeClosed(18, 23)))
                .addAnimation(EntityAnimationType.WALK_SOUTH, new AtlasAnimation(CHARACTER, FRAME_DURATION, rangeClosed(24, 29)))
                .addAnimation(EntityAnimationType.WALK_EAST, new AtlasAnimation(CHARACTER, FRAME_DURATION, rangeClosed(30, 35)))
                .addAnimation(EntityAnimationType.WALK_WEST, new AtlasAnimation(CHARACTER, FRAME_DURATION, rangeClosed(36, 41)))
                .addAnimation(EntityAnimationType.WALK_NORTH, new AtlasAnimation(CHARACTER, FRAME_DURATION, rangeClosed(42, 47)))
        ;
    }

    private BlobsTextures() {
    }

    public static TextureAtlas getAtlasFor(EntityType entityType) {
        switch (entityType) {
            case HUMAN -> {
                return CHARACTER;
            }

            case SLIME -> {
                return SLIME;
            }

            default -> throw new IllegalArgumentException("What Did you do");
        }
    }

    private static IntStream circle(int from, int to) {
        return IntStream.concat(rangeClosed(from, to), rangeClosed(from + 1, to).map(i -> to - i + from));
    }
}
