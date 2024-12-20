package sh.miles.algidle.utils.collection.registry;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import sh.miles.algidle.utils.collection.Option;
import sh.miles.algidle.utils.collection.registry.exception.NoSuchValueException;

import java.util.function.Function;

/**
 * Represents a value from a registry that could be or could not be present. Similar to {@link Optional}, but with
 * better utility related to registries.
 *
 * @param <E> the value type of the holder
 */
@NullMarked
public class Holder<E> {

    private final RegistryKey key;
    @Nullable
    private final E value;

    private Holder(final RegistryKey key, @Nullable final E value) {
        Preconditions.checkArgument(key != null, "The given key must not be null");
        this.key = key;
        this.value = value;
    }

    /**
     * Unwraps the holder and attempts to grab its value directly
     *
     * @return the holder's value if one is present
     * @throws NoSuchValueException thrown if the holder has no value with the associated key
     */
    public E unwrap() throws NoSuchValueException {
        if (this.value == null) {
            throw new NoSuchValueException(this.key);
        }

        return this.value;
    }

    /**
     * Unwraps the key of this holder and grabs it directly
     *
     * @return the holder's key
     */
    public RegistryKey unwrapKey() {
        return this.key;
    }

    /**
     * Checks whether or not this holder is of the given key
     *
     * @param key the key to check
     * @return true if this holder is of the given key, otherwise false
     */
    public boolean is(RegistryKey key) {
        return this.key.equals(key);
    }

    /**
     * Maps this Holder to an {@link Option} given the mapping function. If the value of this holder is null a None
     * option is returned.
     *
     * @param mapper the mapper function
     * @param <R>    the type returned from the mapper
     * @return an option of type Some or None
     */
    public <R> Option<R> map(Function<E, R> mapper) {
        if (this.value == null) {
            return Option.none();
        }

        return Option.some(mapper.apply(this.value));
    }

    public static <E> Holder<E> empty(RegistryKey key) {
        Preconditions.checkArgument(key != null, "The given key must not be null");
        return new Holder<>(key, null);
    }

    public static <E> Holder<E> of(RegistryKey key, E value) {
        Preconditions.checkArgument(key != null, "The given key must not be null");
        Preconditions.checkArgument(value != null, "The given value must not be null");
        return new Holder<>(key, value);
    }
}
