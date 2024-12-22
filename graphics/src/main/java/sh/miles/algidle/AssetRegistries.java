package sh.miles.algidle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import sh.miles.algidle.assets.Sounds;
import sh.miles.algidle.registry.Registries;
import sh.miles.algidle.screen.LoadingScreen;
import sh.miles.algidle.assets.Screens;
import sh.miles.algidle.screen.MainMenuScreen;
import sh.miles.algidle.utils.collection.registry.Registry;
import sh.miles.algidle.utils.collection.registry.RegistryKey;

public final class AssetRegistries {

    public static final RegistryKey SCREENS = RegistryKey.base("screens");
    public static final RegistryKey SOUNDS = RegistryKey.base("sounds");

    private static final Registry<Screen> SCREEN_REGISTRY = Registry.bootstrap((registry) -> {
        registry.register(Screens.LOADING, new LoadingScreen());
        registry.register(Screens.MAIN, new MainMenuScreen());
    }, Registry.RegistryLifecycle.FROZEN);
    private static final Registry<Sound> SOUND_REGISTRY = Registry.bootstrap((registry) -> {
        registry.register(Sounds.LOADING_SOUND, Gdx.audio.newSound(Gdx.files.internal("sounds/loading_screen.mp3")));
    }, Registry.RegistryLifecycle.FROZEN);

    private AssetRegistries() {
        throw new UnsupportedOperationException("Can not initialize utility class");
    }

    public static void initialize() {
        Registries.REGISTRIES.register(SCREENS, SCREEN_REGISTRY);
        Registries.REGISTRIES.register(SOUNDS, SOUND_REGISTRY);
    }

}
