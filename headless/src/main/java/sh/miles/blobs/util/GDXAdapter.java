package sh.miles.blobs.util;

import com.badlogic.gdx.utils.Array;

import java.util.List;

public final class GDXAdapter {

    public static <E> Array<E> copyFrom(E[] array) {
        final Array<E> gdxArray = new Array<>(array.length);
        for (final E e : array) {
            gdxArray.add(e);
        }
        return gdxArray;
    }

    public static <E> Array<E> copyFrom(List<E> list) {
        final Array<E> array = new Array<>(list.size());
        for (final E e : list) {
            array.add(e);
        }

        return array;
    }

}
