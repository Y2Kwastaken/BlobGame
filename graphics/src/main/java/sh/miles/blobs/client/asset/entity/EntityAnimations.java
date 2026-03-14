package sh.miles.blobs.client.asset.entity;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import sh.miles.blobs.client.asset.Assets;
import sh.miles.blobs.client.asset.SpriteSheet;
import sh.miles.blobs.client.util.JsonDeserializer;
import sh.miles.blobs.api.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class EntityAnimations {

    public final EntityType entityType;
    private final Map<EntityAnimationType, EntityAnimation> animations;

    private EntityAnimations(final EntityType entityType, final List<EntityAnimation> animations) {
        this.entityType = entityType;
        this.animations = animations.stream().collect(Collectors.toMap(
                (v) -> v.animationType,
                (v) -> v
        ));
    }

    public EntityAnimation getAnimation(EntityAnimationType type) {
        return this.animations.get(type);
    }

    public EntityType getType() {
        return entityType;
    }

    public static class Parser implements JsonDeserializer<EntityAnimations> {

        private final Assets.AssetMap<String, SpriteSheet> sheets;

        public Parser(final Assets.AssetMap<String, SpriteSheet> sheets) {
            this.sheets = sheets;
        }

        @Override
        public EntityAnimations read(final Json json, final JsonValue data, final Class type) {
            final var sheet = this.sheets.get(data.getString("id"));
            final List<EntityAnimation> animations = new ArrayList<>();
            for (final JsonValue animation : data.get("animation")) {
                animations.add(EntityAnimation.ParserHelper.parse(sheet, animation));
            }

            return new EntityAnimations(EntityType.valueOf(data.getString("id").toUpperCase()), animations);
        }
    }
}
