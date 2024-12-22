package sh.miles.algidle.registry;

import com.google.common.base.Preconditions;
import sh.miles.algidle.utils.collection.registry.Holder;
import sh.miles.algidle.utils.collection.registry.Registry;
import sh.miles.algidle.utils.collection.registry.RegistryKey;

public final class Registries {

    public static final RegistryKey ALGORITHMS = RegistryKey.base("algorithms");

    private static final Registry<Registry<?>> REGISTRIES = Registry.bootstrap((registry) -> {
        registry.register(Registries.ALGORITHMS, BuiltInRegistries.ALGORITHMS);
    }, Registry.RegistryLifecycle.ALLOW_ADDITIONS);


    public static <E> Holder<Registry<E>> get(RegistryKey key) {
        Preconditions.checkArgument(key != null, "The given key must not be null");

        return (Holder<Registry<E>>) (Object) REGISTRIES.get(key);
    }

    public static <E> Registry<E> getOrThrow(RegistryKey key) {
        return (Registry<E>) get(key).unwrap();
    }

    public static void freeze() {
        REGISTRIES.forEach((registry) -> registry.lifecycle(Registry.RegistryLifecycle.FROZEN));
    }
}
