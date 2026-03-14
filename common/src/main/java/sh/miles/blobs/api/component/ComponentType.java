package sh.miles.blobs.api.component;

import org.jspecify.annotations.NullMarked;
import sh.miles.blobs.api.Identifier;
import sh.miles.blobs.common.attribute.Copyable;

import java.util.Objects;

/**
 * Represents a typical type of component. Components can be used by a variety of classes
 *
 * @param <T>        the component type
 * @param id         The identifier of this component type. Generally used for improving hashing and map operations
 * @param type       The java type of this data component
 * @param persistent Whether or not this component persists
 */
@NullMarked
public record ComponentType<T>(Identifier id, Class<T> type, boolean persistent) {

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final ComponentType<?> that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
