package sh.miles.blobs.component;

import java.util.Objects;

/**
 * Represents a type of {@link DataComponent}
 *
 * @param <T> the component type
 */
public class DataComponentType<T> {
    private static int nextId = 0;

    /**
     * The identifier of this component type. Generally used for improving hashing and map operations
     */
    public final int id;
    /**
     * The java type of this data component
     */
    public final Class<T> type;
    /**
     * Whether or not this component persists
     */
    public final boolean persistent;

    public DataComponentType(final Class<T> type, final boolean persistent) {
        this.id = nextId++;
        this.type = type;
        this.persistent = persistent;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final DataComponentType<?> that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
