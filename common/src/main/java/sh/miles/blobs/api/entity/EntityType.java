package sh.miles.blobs.api.entity;

public enum EntityType {
    HUMANOID(1.0f, 1.2f, -0.5f, -0.5f),
    SKELETON(1.0f, 1.2f, -0.5f, -0.5f),
    SLIME(1.2f, 1f, -0.6f, -0.5f),
    ;

    public final float width;
    public final float height;
    public final float offsetX;
    public final float offsetY;

    EntityType(float width, float height, float offsetX, float offsetY) {
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }
}
