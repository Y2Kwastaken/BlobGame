package sh.miles.algidle.utils.collection.registry;

import com.google.common.base.Preconditions;
import org.jspecify.annotations.NullMarked;
import sh.miles.algidle.utils.collection.registry.exception.RegistryLifecycleOperationException;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a registry that can have entries within it
 *
 * @param <E> the type of the registry
 */
@NullMarked
public class Registry<E> {

    private final ConcurrentHashMap<RegistryKey, E> values;
    private RegistryLifecycle lifecycle;

    protected Registry(RegistryLifecycle lifecycle) {
        this.values = new ConcurrentHashMap<>();
        this.lifecycle = lifecycle;
    }

    /**
     * Gets a value from a registry with a given key
     *
     * @param key the key to query
     * @return the returned holder
     * @see Holder
     */
    public Holder<E> get(final RegistryKey key) {
        Preconditions.checkArgument(key != null, "The given key must not be null");
        final var value = this.values.get(key);
        if (value == null) {
            return Holder.empty(key);
        }

        return Holder.of(key, value);
    }

    /**
     * Registers a key and value to the registry given the lifecycle is not set to frozen
     *
     * @param key   the key
     * @param value the value
     * @throws RegistryLifecycleOperationException thrown if the registry is frozen
     * @throws IllegalArgumentException            thrown if any value is null or the registry already has a similarly
     *                                             named key registered
     */
    public void register(final RegistryKey key, final E value) throws RegistryLifecycleOperationException, IllegalArgumentException {
        Preconditions.checkArgument(key != null, "The given key must not be null");
        Preconditions.checkArgument(value != null, "The given value must not be null");
        if (lifecycle == RegistryLifecycle.FROZEN) {
            throw new RegistryLifecycleOperationException(this.lifecycle, RegistryLifecycle.ALLOW_ADDITIONS, "register", getClass());
        }

        if (this.values.containsKey(key)) {
            throw new IllegalArgumentException("The given key already has a registered value");
        }

        this.values.put(key, value);
    }

    /**
     * Unregisters a key given that the registry lifecycle is not set to anything above
     * {@link RegistryLifecycle#ALLOW_ADDITIONS}
     *
     * @param key the key to unregister
     * @throws RegistryLifecycleOperationException thrown if the registry lifecycle is invalid for this operation
     */
    public void unregister(final RegistryKey key) throws RegistryLifecycleOperationException {
        if (lifecycle != RegistryLifecycle.BOOTSTRAP) {
            throw new RegistryLifecycleOperationException(this.lifecycle, RegistryLifecycle.BOOTSTRAP, "unregister", getClass());
        }

        this.values.remove(key);
    }

    /**
     * Changes the registry lifecycle
     *
     * @param lifecycle the lifecycle to change to
     * @throws RegistryLifecycleOperationException thrown if the registry lifecycle is invalid for this operation
     */
    public void lifecycle(RegistryLifecycle lifecycle) throws RegistryLifecycleOperationException {
        if (this.lifecycle == RegistryLifecycle.FROZEN) {
            throw new RegistryLifecycleOperationException(this.lifecycle, RegistryLifecycle.ALLOW_ADDITIONS, "lifecycle change", getClass());
        }
        this.lifecycle = lifecycle;
    }

    public enum RegistryLifecycle {
        BOOTSTRAP,
        ALLOW_ADDITIONS,
        FROZEN
    }
}
