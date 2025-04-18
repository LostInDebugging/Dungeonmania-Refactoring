package dungeonmania.entities;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

import java.util.UUID;

public abstract class Entity {
    public static final int FLOOR_LAYER = 0;
    public static final int ITEM_LAYER = 1;
    public static final int DOOR_LAYER = 2;
    public static final int CHARACTER_LAYER = 3;

    private Position position;
    private String entityId;

    public Entity(Position position) {
        this.position = position;
        this.entityId = UUID.randomUUID().toString();
    }

    public boolean canMoveOnto(GameMap map, Entity entity) {
        return false;
    }

    public abstract void onOverlap(GameMap map, Entity entity);

    public Position getPosition() {
        return position;
    }

    public String getId() {
        return entityId;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
