package sh.miles.blobs.api.dto.client;

public final class ClientEntityDTO {

    public record EntityInputDTO(int id, float moveX, float moveY, boolean isAttacking) {
    }
}
