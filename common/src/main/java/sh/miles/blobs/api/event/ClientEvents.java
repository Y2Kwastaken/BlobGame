package sh.miles.blobs.api.event;

import sh.miles.blobs.api.dto.client.ClientDebugDTO;
import sh.miles.blobs.api.dto.client.ClientEntityDTO;

public class ClientEvents {
    public static GameEvent<ClientEntityDTO.EntityInputDTO> PLAYER_INPUT = new GameEvent<>(ClientEntityDTO.EntityInputDTO.class);
    public static GameEvent<ClientDebugDTO.PlayerDebugDTO> PLAYER_DEBUG = new GameEvent<>(ClientDebugDTO.PlayerDebugDTO.class);
}
