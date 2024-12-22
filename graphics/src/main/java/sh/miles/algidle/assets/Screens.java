package sh.miles.algidle.assets;

import sh.miles.algidle.utils.collection.registry.RegistryKey;

public final class Screens {
    public static final RegistryKey LOADING = RegistryKey.base("loading");
    public static final RegistryKey MAIN = RegistryKey.base("main");

    private Screens() {
        throw new UnsupportedOperationException("Can not initialize utility class");
    }
}
