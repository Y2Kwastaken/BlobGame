package sh.miles.blobs.client.network.local;

import com.badlogic.gdx.graphics.Color;
import sh.miles.blobs.api.event.GameEvents;
import sh.miles.blobs.client.GameRender;
import sh.miles.blobs.client.level.ClientLevel;

public final class ClientLocalListener {

    public static void registerAllLocalListeners(GameRender render) {
        GameEvents.INSTANCE.registerListener(GameEvents.LEVEL_INIT, (data) -> {
            render.clientLevel = new ClientLevel(data.width(), data.height(), data.initialTileGrid());
            render.levelRender.clientLevel(render.clientLevel);
        });
        GameEvents.INSTANCE.registerListener(GameEvents.LEVEL_TILE_UPDATE, (data) -> {
            if (render.clientLevel != null) {
                render.clientLevel.updateVisualTile(data);
            }
        });
        GameEvents.INSTANCE.registerListener(GameEvents.LEVEL_STATUS, (data) -> render.latestSnapshot = data);
        GameEvents.INSTANCE.registerListener(GameEvents.ATTACK_DEBUG, (data) -> {
            render.debugRender.queueScaledBox(1000, Color.CORAL, data.x(), data.y(), data.width(), data.height());
        });
        GameEvents.INSTANCE.registerListener(GameEvents.BOUNDING_BOX_DRAW, (data) -> {
            render.debugRender.queueScaledBox(1000, new Color(data.r(), data.g(), data.b(), data.a()), data.x(), data.y(), data.width(), data.height());
        });
    }

}
