package sh.miles.blobs;

import sh.miles.blobs.level.Level;

import java.util.ArrayList;
import java.util.List;

public final class BlobGame {

    private final Level level;
    private final List<Runnable> preTickHook;

    public BlobGame() {
        this.level = new Level(100, 100, 1);
        this.preTickHook = new ArrayList<>();
    }

    public void tick() {
        for (final Runnable runnable : this.preTickHook) {
            runnable.run();
        }
        level.tick();
    }

    public Level getLevel() {
        return level;
    }

    public void addPreTickHook(Runnable runnable) {
        this.preTickHook.add(runnable);
    }
}
