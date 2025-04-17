package dungeonmania.entities;

import dungeonmania.map.GameMap;

import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.util.Position;

public class Door extends BasicDoor {
    private int number;

    public Door(Position position, int number) {
        super(position.asLayer(Entity.DOOR_LAYER));
        this.number = number;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (isOpen() || entity instanceof Spider) {
            return true;
        }
        return (entity instanceof Player player && hasKey(player));
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (!(entity instanceof Player player))
            return;

        Inventory inventory = player.getInventory();
        Key key = inventory.getFirst(Key.class);

        if (hasKey(player)) {
            inventory.remove(key);
            setOpen(true);
        }
    }

    protected boolean hasKey(Player player) {
        Inventory inventory = player.getInventory();
        Key key = inventory.getFirst(Key.class);

        return (key != null && key.getnumber() == number);
    }
}
