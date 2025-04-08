package sh.miles.blobs.client.util;

import com.badlogic.gdx.utils.Json;

public interface JsonDeserializer<T> extends Json.Serializer<T> {
    @Override
    default void write(Json json, T object, Class knownType) {
        throw new IllegalArgumentException("Write Not Implemented");

    }
}
