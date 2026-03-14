package sh.miles.blobs.entity.component;

import sh.miles.blobs.common.attribute.Copyable;

public class OrientationComponent implements Copyable<OrientationComponent> {
    public float facingX = 0f;
    public float facingY = -1f;

    public OrientationComponent() {
    }

    public OrientationComponent(final float facingX, final float facingY) {
        this.facingX = facingX;
        this.facingY = facingY;
    }

    @Override
    public OrientationComponent copy() {
        return new OrientationComponent(facingX, facingY);
    }
}
