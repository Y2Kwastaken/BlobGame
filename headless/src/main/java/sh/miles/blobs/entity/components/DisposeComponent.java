package sh.miles.blobs.entity.components;

public record DisposeComponent(boolean dispose, int buffer) {
    public DisposeComponent withDispose(boolean dispose) {
        return new DisposeComponent(dispose, this.buffer);
    }

    public DisposeComponent withBuffer(int buffer) {
        return new DisposeComponent(this.dispose, buffer);
    }
}
