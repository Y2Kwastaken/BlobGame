package sh.miles.blobs.common.math;

import sh.miles.blobs.common.attribute.Copyable;

/**
 * A pure Java representation of an Axis-Aligned Bounding Box (AABB) used for 2D spatial representation and collision
 * detection.
 */
public class BoundingBox implements Copyable<BoundingBox> {

    public float x;
    public float y;
    public float width;
    public float height;

    /**
     * Constructs a new BoundingBox.
     */
    public BoundingBox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Copy constructor.
     */
    public BoundingBox(BoundingBox other) {
        this(other.x, other.y, other.width, other.height);
    }

    /**
     * Default constructor creates a box at 0,0 with 0 size.
     */
    public BoundingBox() {
        this(0, 0, 0, 0);
    }

    /**
     * Updates the position and size of the bounding box.
     */
    public void set(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Checks if this bounding box overlaps with another bounding box. This is the standard AABB collision detection
     * algorithm.
     *
     * @param other The other BoundingBox to check against.
     * @return true if the boxes overlap, false otherwise.
     */
    public boolean overlaps(BoundingBox other) {
        return this.x < other.x + other.width &&
            this.x + this.width > other.x &&
            this.y < other.y + other.height &&
            this.y + this.height > other.y;
    }

    /**
     * Checks if a specific point is contained within this bounding box.
     *
     * @param pointX The X coordinate of the point.
     * @param pointY The Y coordinate of the point.
     * @return true if the point is inside the box, false otherwise.
     */
    public boolean contains(float pointX, float pointY) {
        return this.x <= pointX &&
            this.x + this.width >= pointX &&
            this.y <= pointY &&
            this.y + this.height >= pointY;
    }

    @Override
    public String toString() {
        return "BoundingBox{" + "x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + '}';
    }

    @Override
    public BoundingBox copy() {
        return new BoundingBox(this.x, this.y, this.width, this.height);
    }
}
