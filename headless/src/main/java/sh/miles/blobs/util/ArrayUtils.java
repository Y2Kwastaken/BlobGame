package sh.miles.blobs.util;

import java.util.function.Supplier;

public final class ArrayUtils {

    public static <T> void fill(T[] array, Supplier<T> supplier) {
        for (int i = 0; i < array.length; i++) {
            array[i] = supplier.get();
        }
    }

}
