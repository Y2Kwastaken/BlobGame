package sh.miles.blobs.api.level;

import java.util.Random;

public enum TileType {
    GRASS,
    STONE,
    WATER;

    private static TileType[] values;

    public static TileType id(int id) {
        if (values == null) values = values();
        return values[id];
    }

    public static TileType random(Random random) {
        if (values == null) values = values();
        return values[random.nextInt(0, values.length)];
    }
}
