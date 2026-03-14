package sh.miles.blobs.client.render;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sh.miles.blobs.api.dto.game.GameEntityDTO;
import sh.miles.blobs.api.dto.game.GameLevelDTO;
import sh.miles.blobs.api.entity.EntityType;
import sh.miles.blobs.client.entity.ClientEntity;

import java.util.concurrent.ConcurrentHashMap;

public class EntityAnimationSystem {

    private final ConcurrentHashMap<Integer, ClientEntity> clientEntities = new ConcurrentHashMap<>();
    private final BitmapFont font = new BitmapFont();

    public void render(SpriteBatch batch, GameLevelDTO.LevelSnapshotDTO snapshot) {
        if (snapshot == null) return;

        batch.begin();
        for (final GameEntityDTO.EntitySnapshotDTO dto : snapshot.activeEntities()) {
            ClientEntity clientEntity = clientEntities.computeIfAbsent(
                dto.entityId(),
                id -> new ClientEntity(id, dto.type())
            );

            clientEntity.render(batch, dto);
        }
        batch.end();
    }

    public void spawn(int entityId, EntityType type) {
        clientEntities.putIfAbsent(entityId, new ClientEntity(entityId, type));
    }

    public void despawn(int entityId) {
        clientEntities.remove(entityId);
    }

    public void damage(int entityId, float damage, float x, float y) {
        ClientEntity ce = clientEntities.get(entityId);
        if (ce == null) return;

        ce.enqueueRenderOperation((batch) -> {
            font.draw(batch, "*thwack* " + damage, x * 16f, y * 16f);
        });
    }
}
