package sh.miles.blobs.client.atlas;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import sh.miles.blobs.client.atlas.animation.AtlasAnimation;
import sh.miles.blobs.entity.EntityType;
import sh.miles.blobs.entity.animation.EntityAnimationType;

import java.util.function.Consumer;

import static java.util.stream.IntStream.rangeClosed;

public final class BlobsTextures {

    private static final Consumer<TextureRegion> FLIP_X = (b) -> b.flip(true, false);

    public static final TextureAtlas CHARACTER = new TextureAtlas("characters/player.png", new TextureAtlasData(48, 48, 288, 480))
            .modify(12, 1, FLIP_X)
            .modify(36, 4, FLIP_X)
            .modify(60, 7, FLIP_X);
    public static final TextureAtlas SLIME = new TextureAtlas("characters/slime.png", new TextureAtlasData(32, 32, 224, 416))
            .modify(14, 1, FLIP_X)
            .modify(42, 4, FLIP_X)
            .modify(69, 7, FLIP_X);

    private static final float HFD = 0.25F;
    public static final float HFDW = 0.2F;
    private static final float HFDA = 0.15F;
    private static final float SFD = 0.2F;

    static {
        CHARACTER
                .addAnimation(EntityAnimationType.IDLE_SOUTH, new AtlasAnimation(CHARACTER, HFD, rangeClosed(0, 5)))
                .addAnimation(EntityAnimationType.IDLE_EAST, new AtlasAnimation(CHARACTER, HFD, rangeClosed(6, 11)))
                .addAnimation(EntityAnimationType.IDLE_WEST, new AtlasAnimation(CHARACTER, HFD, rangeClosed(12, 17)))
                .addAnimation(EntityAnimationType.IDLE_NORTH, new AtlasAnimation(CHARACTER, HFD, rangeClosed(18, 23)))
                .addAnimation(EntityAnimationType.WALK_SOUTH, new AtlasAnimation(CHARACTER, HFDW, rangeClosed(24, 29)))
                .addAnimation(EntityAnimationType.WALK_EAST, new AtlasAnimation(CHARACTER, HFDW, rangeClosed(30, 35)))
                .addAnimation(EntityAnimationType.WALK_WEST, new AtlasAnimation(CHARACTER, HFDW, rangeClosed(36, 41)))
                .addAnimation(EntityAnimationType.WALK_NORTH, new AtlasAnimation(CHARACTER, HFDW, rangeClosed(42, 47)))
                .addAnimation(EntityAnimationType.ATTACK_SOUTH, new AtlasAnimation(CHARACTER, HFDA, rangeClosed(48, 51)))
                .addAnimation(EntityAnimationType.ATTACK_EAST, new AtlasAnimation(CHARACTER, HFDA, rangeClosed(54, 57)))
                .addAnimation(EntityAnimationType.ATTACK_WEST, new AtlasAnimation(CHARACTER, HFDA, rangeClosed(60, 63)))
                .addAnimation(EntityAnimationType.ATTACK_NORTH, new AtlasAnimation(CHARACTER, HFDA, rangeClosed(66, 69)))
        ;

        SLIME
                .addAnimation(EntityAnimationType.IDLE_SOUTH, new AtlasAnimation(SLIME, SFD, rangeClosed(0, 3)))
                .addAnimation(EntityAnimationType.IDLE_EAST, new AtlasAnimation(SLIME, SFD, rangeClosed(7, 10)))
                .addAnimation(EntityAnimationType.IDLE_WEST, new AtlasAnimation(SLIME, SFD, rangeClosed(14, 17)))
                .addAnimation(EntityAnimationType.IDLE_NORTH, new AtlasAnimation(SLIME, SFD, rangeClosed(21, 24)))
                .addAnimation(EntityAnimationType.WALK_SOUTH, new AtlasAnimation(SLIME, SFD, rangeClosed(28, 33)))
                .addAnimation(EntityAnimationType.WALK_EAST, new AtlasAnimation(SLIME, SFD, rangeClosed(35, 40)))
                .addAnimation(EntityAnimationType.WALK_WEST, new AtlasAnimation(SLIME, SFD, rangeClosed(42, 47)))
                .addAnimation(EntityAnimationType.WALK_NORTH, new AtlasAnimation(SLIME, SFD, rangeClosed(49, 54)))
                .addAnimation(EntityAnimationType.ATTACK_SOUTH, new AtlasAnimation(SLIME, SFD, rangeClosed(56, 62)))
                .addAnimation(EntityAnimationType.ATTACK_EAST, new AtlasAnimation(SLIME, SFD, rangeClosed(63, 69)))
                .addAnimation(EntityAnimationType.ATTACK_WEST, new AtlasAnimation(SLIME, SFD, rangeClosed(69, 75)))
                .addAnimation(EntityAnimationType.ATTACK_NORTH, new AtlasAnimation(SLIME, SFD, rangeClosed(76, 82)))
                .addAnimation(EntityAnimationType.DIE_SOUTH, new AtlasAnimation(SLIME, SFD, rangeClosed(105, 108)))
                .addAnimation(EntityAnimationType.DIE_EAST, new AtlasAnimation(SLIME, SFD, rangeClosed(105, 108)))
                .addAnimation(EntityAnimationType.DIE_WEST, new AtlasAnimation(SLIME, SFD, rangeClosed(105, 108)))
                .addAnimation(EntityAnimationType.DIE_NORTH, new AtlasAnimation(SLIME, SFD, rangeClosed(105, 108)))
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
}
