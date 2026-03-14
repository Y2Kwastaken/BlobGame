package sh.miles.blobs.common.attribute;

/**
 * Represents an object that can be copied
 *
 * @param <S> the object
 */
public interface Copyable<S> {
    /**
     * Copies this object to a copy of itself deeply
     *
     * @return a copy of this object
     */
    S copy();
}
