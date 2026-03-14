package sh.miles.blobs.api.dto.game;

import sh.miles.blobs.api.level.TileType;

import java.util.List;

public final class GameLevelDTO {
    private GameLevelDTO() {
    }

    /**
     * The massive payload sent only once when a level is loaded or joined.
     */
    public record LevelInitDTO(
        int width,
        int height,
        int[][] initialTileGrid // The full map state
    ) {
    }

    /**
     * Sent only when a single tile's state changes.
     */
    public record TileUpdateDTO(
        int x,
        int y,
        TileType newType
    ) {
    }

    /**
     * The complete dynamic state of the level, sent every tick.
     */
    public record LevelSnapshotDTO(
        long tickNumber,
        List<GameEntityDTO.EntitySnapshotDTO> activeEntities
    ) {}
}
