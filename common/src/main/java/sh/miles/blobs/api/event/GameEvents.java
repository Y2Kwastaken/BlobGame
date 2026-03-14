package sh.miles.blobs.api.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.jspecify.annotations.NullMarked;
import sh.miles.blobs.api.dto.game.GameDebugDTO;
import sh.miles.blobs.api.dto.game.GameLevelDTO;
import sh.miles.blobs.common.Unit;

import java.util.function.Consumer;

/**
 * A central event dispatcher (Event Bus) for managing and triggering game-wide events. * This class allows different
 * game systems and components to communicate in a decoupled manner. Systems can register listeners for specific
 * {@link GameEvent}s and will be notified automatically when those events are called.
 */
@NullMarked
public class GameEvents {

    // GAME LOOP START
    public static final GameEvent<Unit> START = new GameEvent<>(Unit.class);
    public static final GameEvent<Unit> STOP = new GameEvent<>(Unit.class);
    public static final GameEvent<Unit> TICK_START = new GameEvent<>(Unit.class);
    public static final GameEvent<Unit> TICK_END = new GameEvent<>(Unit.class);
    // GAME LOOP END

    // LEVEL EVENTS START
    public static final GameEvent<GameLevelDTO.LevelInitDTO> LEVEL_INIT = new GameEvent<>(GameLevelDTO.LevelInitDTO.class);
    public static final GameEvent<GameLevelDTO.TileUpdateDTO> LEVEL_TILE_UPDATE = new GameEvent<>(GameLevelDTO.TileUpdateDTO.class);
    public static final GameEvent<GameLevelDTO.LevelSnapshotDTO> LEVEL_STATUS = new GameEvent<>(GameLevelDTO.LevelSnapshotDTO.class);
    // LEVEL EVENTS END

    // ENTITY EVENTS START
    // ENTITY EVENTS END

    // DEBUG EVENTS START
    public static final GameEvent<GameDebugDTO.AttackBoundsDTO> ATTACK_DEBUG = new GameEvent<>(GameDebugDTO.AttackBoundsDTO.class);
    public static final GameEvent<GameDebugDTO.BoundingBoxDraw> BOUNDING_BOX_DRAW = new GameEvent<>(GameDebugDTO.BoundingBoxDraw.class);
    // DEBUG EVENTS END

    /**
     * The global singleton instance of the event manager.
     */
    public static GameEvents INSTANCE = new GameEvents();

    private final Multimap<Integer, Consumer<Object>> listeners = ArrayListMultimap.create();

    /**
     * Subscribes a listener to a specific game event. When the specified event is triggered via
     * {@link #call(GameEvent, Object)}, the provided listener will be executed.
     *
     * @param event    The specific {@link GameEvent} to listen for.
     * @param listener The callback function to execute when the event occurs.
     * @param <T>      The type of the parameter expected by the event.
     */
    @SuppressWarnings("unchecked")
    public <T> void registerListener(GameEvent<T> event, Consumer<T> listener) {
        this.listeners.put(event.id(), (Consumer<Object>) listener);
    }

    /**
     * Dispatches an event, notifying all registered listeners. Iterates through every listener subscribed to the given
     * event's ID and accepts the provided parameter.
     *
     * @param event     The {@link GameEvent} to trigger.
     * @param parameter The data payload to pass to the listeners (can be null for Void events).
     * @param <T>       The type of the parameter being passed.
     */
    public <T> void callEvent(GameEvent<T> event, T parameter) {
        for (final Consumer<Object> consumer : this.listeners.get(event.id())) {
            consumer.accept(parameter);
        }
    }

    public static <T> void call(GameEvent<T> event, T parameter) {
        INSTANCE.callEvent(event, parameter);
    }
}
