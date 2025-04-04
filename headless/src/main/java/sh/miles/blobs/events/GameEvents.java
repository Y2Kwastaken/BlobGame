package sh.miles.blobs.events;

import com.badlogic.gdx.utils.IntMap;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class GameEvents {

    public static final int SERVER_CELL_SET = 0;

    private static final IntMap<List<Consumer<Object[]>>> events = new IntMap<>();

    private GameEvents() {
    }

    /**
     * Calls an event
     *
     * @param event the event to call
     */
    public static void call(int event, Object[] parameters) {
        final var found = events.get(event);
        if (found == null) return;
        for (final Consumer<Object[]> consumer : found) {
            consumer.accept(parameters);
        }
    }

    public static void listen(int event, Consumer<Object[]> listener) {
        if (!events.containsKey(event)) {
            events.put(event, new ArrayList<>(1));
        }

        events.get(event).add(listener);
    }
}
