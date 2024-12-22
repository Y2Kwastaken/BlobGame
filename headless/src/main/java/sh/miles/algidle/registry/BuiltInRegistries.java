package sh.miles.algidle.registry;

import org.jspecify.annotations.NullMarked;
import sh.miles.algidle.computer.Algorithm;
import sh.miles.algidle.computer.Algorithms;
import sh.miles.algidle.computer.BigO;
import sh.miles.algidle.utils.collection.registry.Registry;

@NullMarked
public final class BuiltInRegistries {

    static final Registry<Algorithm> ALGORITHMS = Registry.bootstrap((registry) -> {
        registry.register(Algorithms.DATA_SEARCH, new Algorithm("Data Search", BigO.LOG_N, BigO.FACTORIAL, 1.0));
        registry.register(Algorithms.DATA_SORT, new Algorithm("Data Sort", BigO.N_LOG_N, BigO.FACTORIAL, 2.0));
        registry.register(Algorithms.FFT, new Algorithm("Fast Fourier Transformation", BigO.N_LOG_N, BigO.FACTORIAL, 3.0));
        registry.register(Algorithms.DFT, new Algorithm("Discrete Fourier Transformation", BigO.N_LOG_N, BigO.FACTORIAL, 4.0));
        registry.register(Algorithms.TRAVELING_SALESMAN_PROBLEM, new Algorithm("Traveling Salesman Problem", BigO.N_SQUARED, BigO.FACTORIAL, 5.0));
    }, Registry.RegistryLifecycle.ALLOW_ADDITIONS);

    private BuiltInRegistries() {
        throw new UnsupportedOperationException("Can not initialize utility class");
    }
}
