package dungeonmania.entities.inventory;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;
import dungeonmania.entities.StaticEntity;

/**
 * An item in the inventory
 */
public abstract class InventoryItem extends StaticEntity {
    public InventoryItem(Position position) {
        super(position);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }
}
