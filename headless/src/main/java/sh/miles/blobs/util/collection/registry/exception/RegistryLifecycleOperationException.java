package sh.miles.blobs.util.collection.registry.exception;

import sh.miles.blobs.util.collection.registry.Registry;

public class RegistryLifecycleOperationException extends RuntimeException {
    public RegistryLifecycleOperationException(Registry.RegistryLifecycle current, Registry.RegistryLifecycle required, String operationName, Class<?> registryClass) {
        super("Could not %s on the registry class %s because the required lifecycle for this operation is at-least %s, however the current lifecycle is %s".formatted(
                operationName, registryClass.getName(), required.name(), current.name()
        ));
    }
}
