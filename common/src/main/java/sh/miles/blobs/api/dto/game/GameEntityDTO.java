package sh.miles.blobs.api.dto.game;

import sh.miles.blobs.api.entity.EntityType;

public final class GameEntityDTO {
    private GameEntityDTO() {
    }

    /**
     * A snapshot of a single entity for a specific frame.
     */
    public record EntitySnapshotDTO(
        int entityId,
        EntityType type,
        float x,
        float y,
        float velocityX, // Useful for the frontend to know which way to flip the sprite
        float velocityY,
        float hitboxX,
        float hitboxY,
        float hitboxWidth,
        float hitboxHeight,
        boolean isAttacking,
        boolean hasOrientaiton,
        float facingX,
        float facingY
    ) {
    }
}
