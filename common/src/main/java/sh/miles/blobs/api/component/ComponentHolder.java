package sh.miles.blobs.api.component;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * An interface that marks some object as being able to store components.
 * <p>
 * Component holders can store and set components of {@link ComponentType} class and various values associated with
 * them.
 */
@NullMarked
public interface ComponentHolder {

    /**
     * Sets the provided component on this component holder
     *
     * @param component the component to set
     * @param data      the data associated with this component
     * @param <T>       the type of component data
     */
    <T> void set(ComponentType<T> component, T data);

    /**
     * Gets whether or not the ComponentHolder has this component available.
     *
     * @param component the component to check for.
     * @param <T>       the generic type of the component.
     * @return true if this holder has the component, otherwise false.
     */
    <T> boolean has(ComponentType<T> component);

    /**
     * Gets a component of the given type. Check {@link #has(ComponentType)} first to decide if the component exists or
     * not.
     *
     * @param component the component to get the value of.
     * @param <T>       the generic type of the component to be returned.
     * @return the value of the component.
     * @throws IllegalArgumentException thrown if the component given is not contained on this holder
     */
    <T> T get(ComponentType<T> component) throws IllegalArgumentException;

    /**
     * Gets a component of the given type. Check {@link #has(ComponentType)} first to decide if the component exists or
     * not.
     *
     * @param component the component to get the value of.
     * @param <T>       the generic type of the component to be returned.
     * @return the value of the component.
     * @throws IllegalArgumentException thrown if the component given is not contained on this holder
     */
    @Nullable
    <T> T getOrNull(ComponentType<T> component);

    /**
     * Gets the component of a given type. If the component is not present the defaultValue is evaluted.
     *
     * @param component    the component to get the value of.
     * @param defaultValue the default evaluated only if needed.
     * @param <T>          the generic type of the component to be returned.
     * @return the value that is either the default or the computed get
     */
    <T> T getOrDefault(ComponentType<T> component, Supplier<T> defaultValue);

    /**
     * Removes a component from this holder.
     *
     * @param type the type of component to remove.
     * @param <T>  the generic type of the component to be returned.
     * @return the value wrapped in an optional.
     */
    <T> Optional<T> remove(ComponentType<T> type);

    /**
     * Copies all of the components on this holder to the other holder
     *
     * @param other the holder to copy all components to
     */
    void copyTo(ComponentHolder other);
}
