package sh.miles.blobs.util.collection.registry.exception;

import sh.miles.blobs.util.collection.registry.RegistryKey;

public class NoSuchValueException extends RuntimeException {
    public NoSuchValueException(RegistryKey key) {
        super("The key " + key.toKeyString() + " has no corresponding value present within this holder");
    }
}
