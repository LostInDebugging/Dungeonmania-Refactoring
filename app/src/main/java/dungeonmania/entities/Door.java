package dungeonmania.entities;

import dungeonmania.map.GameMap;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.entities.collectables.SunStone;
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
        if (entity instanceof Player player) {
            return hasMatchingKey(player) || hasSunStone(player);
        }
        return false;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (!(entity instanceof Player player))
            return;

        Inventory inventory = player.getInventory();

        if (hasMatchingKey(player)) {

            Key key = inventory.getFirst(Key.class);
            inventory.remove(key);
            setOpen(true);
        } else if (hasSunStone(player)) {
            setOpen(true);
        }
    }

    private boolean hasMatchingKey(Player player) {
        Inventory inv = player.getInventory();
        Key key = inv.getFirst(Key.class);
        return key != null && key.getnumber() == number;
    }

    private boolean hasSunStone(Player player) {
        return player.getInventory().getFirst(SunStone.class) != null;
    }
}
