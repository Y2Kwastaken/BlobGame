package sh.miles.blobs.common.attribute;

/**
 * Represents an entity or system that has a distinct active lifespan and requires periodic updates.
 * <p>Classes implementing this interface can be actively managed, allowing
 * them to be started, stopped, and receive regular update ticks (inherited from {@link Tickable}). This pattern is
 * highly effective for managing discrete mechanics like hitboxes, movement systems, or game screens that require setup
 * and teardown.
 */
public interface Lifecycle extends Tickable {

    /**
     * Initializes or resumes the system. This method should handle any setup required before the object begins
     * processing ticks. Common tasks include allocating resources, resetting internal state variables, or registering
     * event listeners.
     */
    void start();

    /**
     * Halts the system and performs necessary cleanup. * This method safely pauses or terminates the object's active
     * state. Implementations should handle tasks like deregistering listeners, saving state, freeing memory, or halting
     * active physical calculations.
     */
    void stop();
}
