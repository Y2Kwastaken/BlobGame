package sh.miles.blobs.api.dto.game;

public class GameDebugDTO {
    public record AttackBoundsDTO(int entityId, float x, float y, float width, float height) {
    }

    public record BoundingBoxDraw(float r, float g, float b, float a, float width, float height, float x, float y) {
    }
}
