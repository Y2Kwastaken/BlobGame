package sh.miles.blobs.client.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import sh.miles.blobs.client.asset.SpriteSheet;
import sh.miles.blobs.client.asset.Textures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class EntityAnimation {

    public final EntityAnimationType animationType;
    private final Map<AnimationVariation, Variation> variations;

    private EntityAnimation(final EntityAnimationType animationType, final Map<AnimationVariation, Variation> variations) {
        this.animationType = animationType;
        this.variations = variations;
    }

    public Animation<TextureRegion> get(AnimationVariation variation) {
        return variations.get(variation).animation.get();
    }

    public static final class Variation {
        public final AnimationVariation variation;
        public final float interval;
        public final Supplier<Animation<TextureRegion>> animation;

        public Variation(AnimationVariation variation, float interval, Supplier<List<TextureRegion>> regions) {
            this.variation = variation;
            this.interval = interval;
            this.animation = () -> new Animation<>(interval, regions.get().toArray(TextureRegion[]::new));
        }
    }

    public static final class ParserHelper {

        public static EntityAnimation parse(SpriteSheet sheet, JsonValue parent) {
            final EntityAnimationType animation = EntityAnimationType.valueOf(parent.getString("animation_type").toUpperCase());
            final Map<AnimationVariation, Variation> variations = new HashMap<>();
            for (final JsonValue variation : parent.get("variations")) {
                parseAnimation(sheet, variation.getString("variation_type"), variation.get("animation"), false, variations);
            }

            return new EntityAnimation(animation, variations);
        }

        private static void parseAnimation(SpriteSheet sheet, String variation, JsonValue parent, boolean flip, final Map<AnimationVariation, Variation> collector) {
            if (variation.equals("side")) {
                parseAnimation(sheet, "east", parent, false, collector);
                parseAnimation(sheet, "west", parent, true, collector);
                return;
            }

            final float interval = parent.getFloat("interval");
            final List<Supplier<TextureRegion>> regions = new ArrayList<>();
            for (final JsonValue step : parent.get("steps")) {
                final int x = step.getInt("x");
                final int y = step.getInt("y");

                regions.add(() -> {
                    final TextureRegion region = sheet.createRegion(Textures.getFromId(sheet.id()), x, y);
                    if (flip) region.flip(true, false);
                    return region;
                });
            }

            final var animationVariation = AnimationVariation.valueOf(variation.toUpperCase());
            collector.put(animationVariation, new Variation(animationVariation, interval, () -> regions.stream().map(Supplier::get).toList()));
        }

    }
}
