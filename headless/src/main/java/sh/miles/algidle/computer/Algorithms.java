package sh.miles.algidle.computer;

import org.jspecify.annotations.NullMarked;
import sh.miles.algidle.utils.collection.registry.RegistryKey;

@NullMarked
public final class Algorithms {

    private Algorithms() {
        throw new UnsupportedOperationException("Can not initialize Algorithms utility class");
    }

    public static final RegistryKey DATA_SORT = RegistryKey.base("data_sort");
    public static final RegistryKey DATA_SEARCH = RegistryKey.base("data_search");
    public static final RegistryKey FFT = RegistryKey.base("fft");
    public static final RegistryKey DFT = RegistryKey.base("dft");
    public static final RegistryKey TRAVELING_SALESMAN_PROBLEM = RegistryKey.base("traveling_sales_man_problem");

}
