package sh.miles.blobs.util.tile;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import sh.miles.blobs.events.GameEvents;

public class ServerCell extends TiledMapTileLayer.Cell {

    private int id;

    public ServerCell() {
        this.id = 0;
    }

    public ServerCell(int id) {
        setId(id);
    }

    public void setId(final int id) {
        this.id = id;
        GameEvents.call(GameEvents.SERVER_CELL_SET, new Object[]{this});
    }

    public int getId() {
        return id;
    }
}
