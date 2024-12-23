package sh.miles.algidle.texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import org.jspecify.annotations.NullMarked;
import sh.miles.algidle.utils.collection.Option;
import sh.miles.algidle.utils.collection.registry.RegistryKey;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@NullMarked
public class ResourcePack {

    public static final ResourcePack DEFAULT = new ResourcePack(
            () -> Map.of(
                    RegistryKey.base("libgdx"), new Texture("libgdx.png")
            ),
            () -> Map.of(
                    RegistryKey.base("loading_screen"), Gdx.audio.newSound(Gdx.files.internal("sounds/loading_screen.mp3"))
            )
    );

    private final Map<RegistryKey, Texture> textures;
    private final Map<RegistryKey, Sound> sounds;

    public ResourcePack() {
        this.textures = new HashMap<>();
        this.sounds = new HashMap<>();
    }

    public ResourcePack(Supplier<Map<RegistryKey, Texture>> textureSupplier, Supplier<Map<RegistryKey, Sound>> soundSupplier) {
        this.textures = textureSupplier.get();
        this.sounds = soundSupplier.get();
    }

    public Option<Texture> getTexture(final RegistryKey key) {
        final Texture texture = textures.get(key);
        if (texture == null) return Option.none();
        return Option.some(texture);
    }

    public Option<Sound> getSound(final RegistryKey key) {
        final Sound sound = sounds.get(key);
        if (sound == null) return Option.none();
        return Option.some(sound);
    }

    public void playSound(final RegistryKey key, final float volume) {
        final var sound = getSound(key);
        if (sound instanceof Option.Some<Sound> some) {
            some.some().play(volume);
        }
    }
}
