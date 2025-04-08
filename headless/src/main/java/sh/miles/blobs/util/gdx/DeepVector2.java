package sh.miles.blobs.util.gdx;

import com.badlogic.gdx.math.Vector2;

import java.util.function.Consumer;

public record DeepVector2(Vector2 vector) {

    public static DeepVector2 ZERO = new DeepVector2(Vector2.Zero);

    public DeepVector2 modify(Consumer<Vector2> editor) {
        final var copy = vector.cpy();
        editor.accept(copy);
        return new DeepVector2(copy);
    }

    @Override
    public Vector2 vector() {
        return vector.cpy();
    }
}
