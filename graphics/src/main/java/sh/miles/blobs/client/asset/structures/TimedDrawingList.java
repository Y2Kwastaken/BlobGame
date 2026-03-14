package sh.miles.blobs.client.asset.structures;

import sh.miles.blobs.client.asset.BasicRenderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class TimedDrawingList<T extends BasicRenderer<P>, P> {
    private final List<Entry<T>> drawings = new ArrayList<>();

    public void add(long durationMiliseconds, T draw) {
        if (durationMiliseconds <= 0) {
            throw new IllegalArgumentException("can't queue drawing for - miliseconds");
        }

        final Entry<T> entry = new Entry<>();
        entry.expires = System.currentTimeMillis() + durationMiliseconds;
        entry.draw = draw;
        drawings.add(entry);
    }

    public void draw(P provider) {
        Iterator<Entry<T>> entries = drawings.iterator();
        Entry<T> entry;
        long current = System.currentTimeMillis();
        while (entries.hasNext()) {
            entry = entries.next();
            if (current >= entry.expires) {
                entries.remove();
            }

            entry.draw.draw(provider);
        }
    }

    private static class Entry<T> {
        long expires;
        T draw;
    }
}
