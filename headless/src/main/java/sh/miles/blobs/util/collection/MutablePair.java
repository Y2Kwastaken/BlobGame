package sh.miles.blobs.util.collection;

import java.util.Objects;

public final class MutablePair<L, R> {

    public L left;
    public R right;

    public MutablePair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final MutablePair<?, ?> that)) return false;
        return Objects.equals(left, that.left) && Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
