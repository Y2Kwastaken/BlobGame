package sh.miles.blobs.level;

import org.jspecify.annotations.Nullable;
import sh.miles.blobs.api.dto.game.GameDebugDTO;
import sh.miles.blobs.api.dto.game.GameEntityDTO;
import sh.miles.blobs.api.dto.game.GameLevelDTO;
import sh.miles.blobs.api.entity.EntityType;
import sh.miles.blobs.api.event.GameEvents;
import sh.miles.blobs.api.level.TileType;
import sh.miles.blobs.common.attribute.Tickable;
import sh.miles.blobs.common.math.BoundingBox;
import sh.miles.blobs.common.math.Position;
import sh.miles.blobs.entity.Entity;
import sh.miles.blobs.entity.EntityComponents;
import sh.miles.blobs.entity.component.HitboxComponent;
import sh.miles.blobs.entity.component.OrientationComponent;
import sh.miles.blobs.system.CombatSystem;
import sh.miles.blobs.system.MovementSystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Level implements Tickable {

    public final int width;
    public final int height;

    private final int[][] tiles;
    private final List<Entity> entities;
    private final List<Entity> entitiesToDespawn;

    public Level(int width, int height, Consumer<int[][]> setup) {
        this.width = width;
        this.height = height;
        this.tiles = new int[width][height];
        this.entities = new ArrayList<>();
        this.entitiesToDespawn = new ArrayList<>();
        setup.accept(tiles);
        GameEvents.call(GameEvents.LEVEL_INIT, new GameLevelDTO.LevelInitDTO(width, height, tiles));
    }

    @Override
    public void tick() {
        MovementSystem.SYSTEM.update(this.entities, this);
        CombatSystem.SYSTEM.update(this.entities, this);

        if (!this.entitiesToDespawn.isEmpty()) {
            for (final Entity entity : this.entitiesToDespawn) {
                this.entities.remove(entity);

                // Call game event for despawn
            }

            this.entitiesToDespawn.clear();
        }

        final List<GameEntityDTO.EntitySnapshotDTO> snapshots = new ArrayList<>();
        for (final Entity entity : this.entities) {
            snapshots.add(entity.nextSnapshot());
        }

        GameEvents.call(GameEvents.LEVEL_STATUS, new GameLevelDTO.LevelSnapshotDTO(System.currentTimeMillis(), snapshots));
    }

    public boolean spawnEntity(EntityType type, float x, float y, @Nullable Consumer<Entity> configure) {
        if (!isInside((int) x, (int) y)) return false;
        final Entity entity = new Entity();
        entity.set(EntityComponents.ENTITY_TYPE, type);
        entity.set(EntityComponents.ORIENTATION, new OrientationComponent());
        entity.set(EntityComponents.POSITION, new Position(x, y));
        entity.set(EntityComponents.HITBOX, new HitboxComponent(x, y, type.width, type.height, type.offsetX, type.offsetY));
        entity.set(EntityComponents.LEVEL, this);
        if (configure != null) {
            configure.accept(entity);
        }
        return this.entities.add(entity);
    }

    public Collection<Entity> getEntitiesInside(BoundingBox box, Predicate<Entity> filter) {
        final List<Entity> collector = new ArrayList<>();
        for (final Entity entity : this.entities) {
            final var pos = entity.getOrNull(EntityComponents.POSITION);
            final var hitboxComp = entity.getOrNull(EntityComponents.HITBOX);
            final var hitbox = hitboxComp.getBoundsWithOffset();
            GameEvents.call(GameEvents.BOUNDING_BOX_DRAW, new GameDebugDTO.BoundingBoxDraw(15, 0, 8, 1f, hitbox.width - 0.05f, hitbox.height - 0.05f, hitbox.x, hitbox.y));
            if (hitboxComp.getBoundsWithOffset().overlaps(box) && filter.test(entity)) {
                collector.add(entity);
            }
        }

        return collector;
    }

    @Nullable
    public Entity getEntity(int id) {
        for (final Entity entity : this.entities) {
            if (entity.id == id) {
                return entity;
            }
        }

        return null;
    }

    public void despawn(Entity entity) {
        if (!this.entitiesToDespawn.contains(entity)) {
            this.entitiesToDespawn.add(entity);
        }
    }

    public void setTile(int x, int y, TileType type) {
        if (!isInside(x, y)) return;
        tiles[x][y] = type.ordinal();

        GameEvents.call(GameEvents.LEVEL_TILE_UPDATE, new GameLevelDTO.TileUpdateDTO(x, y, type));
    }

    @Nullable
    public TileType getTile(int x, int y) {
        if (!isInside(x, y)) return null;
        return TileType.id(tiles[x][y]);
    }

    public boolean isInside(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean isInside(float x, float y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean isInside(BoundingBox bounds) {
        return bounds.x >= 0 && (bounds.x + bounds.width) <= this.width &&
            bounds.y >= 0 && (bounds.y + bounds.height) <= this.height;
    }
}
