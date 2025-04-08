package sh.miles.blobs.client.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.IntMap;
import sh.miles.blobs.entity.Entity;
import sh.miles.blobs.level.Level;

public class EntityAnimationSystem {

    private final IntMap<ClientEntity> clientEntities = new IntMap<>();

    public void render(SpriteBatch batch, Level level) {
        batch.begin();
        for (final Entity entity : level.getEntities()) {
            final var clientEntity = clientEntities.get(entity.id);
            clientEntity.render(batch);
        }
        batch.end();
    }

    public void spawn(Entity entity) {
        clientEntities.put(entity.id, new ClientEntity(entity));
    }

    public void despawn(Entity entity) {
        clientEntities.remove(entity.id);
    }

}
