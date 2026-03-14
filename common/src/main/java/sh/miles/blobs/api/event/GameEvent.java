package sh.miles.blobs.api.event;

/**
 * Represents an event that can occur within the game
 *
 * @param <T> the game event
 */
public class GameEvent<T> {
    private static int nextId = 0;

    private final int id;
    private final Class<T> dataType;

    GameEvent(Class<T> dataType) {
        this.id = nextId++;
        this.dataType = dataType;
    }

    public Class<T> dataType() {
        return dataType;
    }

    public int id() {
        return id;
    }
}
