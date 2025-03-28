package sh.miles.blobs;

import sh.miles.blobs.entity.Entity;
import sh.miles.blobs.entity.components.EntityComponents;
import sh.miles.blobs.util.Ticking;

import java.util.ArrayList;
import java.util.List;

/**
 * The blobs game
 */
public final class BlobsGame implements Ticking {

    public final boolean client;
    public final boolean server;
    public final List<Entity> entities;
    private final Runnable onTickEnd;

    public BlobsGame(boolean client, Runnable onTickEnd) {
        this.client = client;
        this.server = !client;
        this.entities = new ArrayList<>();
        this.onTickEnd = onTickEnd;
    }

    @Override
    public void tick() {
        for (final Entity entity : entities) {
            entity.tick();
        }

        this.onTickEnd.run();
    }


}
