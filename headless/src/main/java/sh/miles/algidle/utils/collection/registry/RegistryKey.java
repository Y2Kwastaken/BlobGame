package sh.miles.algidle.utils.collection.registry;

import com.google.common.base.Preconditions;
import org.jspecify.annotations.NullMarked;

import java.util.Arrays;

/**
 * Designations registry location with a {@link Registry} with a namespace and location. Specification of namespace
 * allows for addons to have clashing location names. e.g. "base;one", "modded;one".
 *
 * @param namespace the namespace of the key
 * @param location  the location
 */
@NullMarked
public record RegistryKey(String namespace, String location) {

    public static final char SEPARATOR = ';';
    public static final String BASE_NAMESPACE = "base";

    public RegistryKey {
        Preconditions.checkArgument(namespace != null, "The provided namespace must not be null");
        Preconditions.checkArgument(location != null, "The provided key location must not be null");
    }

    /**
     * Formats registry key to a "key string" of format "namespace;location"
     *
     * @return the key string
     */
    public String toKeyString() {
        return this.namespace + ";" + this.location;
    }

    public static RegistryKey base(String location) {
        Preconditions.checkArgument(location != null, "The provided key location must not be null");
        return new RegistryKey(BASE_NAMESPACE, location);
    }

    public static RegistryKey of(String key) throws IllegalArgumentException {
        Preconditions.checkArgument(key != null, "The provided key must not be null");
        final String[] result = key.split("" + SEPARATOR);
        Preconditions.checkArgument(result.length == 2, "Malformed key split contents of " + Arrays.toString(result) + " expected array size of 2 when split across ';'");

        return new RegistryKey(result[0], result[1]);
    }
}
