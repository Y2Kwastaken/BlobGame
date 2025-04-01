package sh.miles.blobs.level;

public enum TileType {
    GRASS,
    STONE,
    WATER;

    private static TileType[] values;

    public static TileType id(int id) {
        if (values == null) values = values();
        return values[id];
    }
}
