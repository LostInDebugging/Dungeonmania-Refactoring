package dungeonmania.entities.StaticEntities;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

import dungeonmania.util.Position;

public abstract class BasicDoor extends StaticEntity {
    private boolean open = false;

    public BasicDoor(Position position) {
        super(position.asLayer(Entity.DOOR_LAYER));
    }

    @Override
    public abstract boolean canMoveOnto(GameMap map, Entity entity);

    @Override
    public abstract void onOverlap(GameMap map, Entity entity);

    public boolean isOpen() {
        return open;
    }

    protected void setOpen(boolean open) {
        this.open = open;
    }
}
