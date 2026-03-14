package sh.miles.blobs.api.dto.client;

public final class ClientDebugDTO {

    public record PlayerDebugDTO(int id, boolean spawnSlime, boolean cloneSelf) {
    }
}
