package dungeonmania.entities.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Bow;
import dungeonmania.entities.buildables.BuildableType;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Sword;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Useable;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.map.GameMap;

public class Inventory {
    private List<InventoryItem> items = new ArrayList<>();
    private GameMap map;

    // Modified the constructor to accept a GameMap
    public Inventory(GameMap map) {
        this.map = map;
    }

    private boolean noZombiesPresent() {
        return map.getEntities(dungeonmania.entities.enemies.ZombieToast.class).isEmpty();
    }

    public boolean add(InventoryItem item) {
        items.add(item);
        return true;
    }

    public void remove(InventoryItem item) {
        items.remove(item);
    }

    // Get the list of possible buildables
    public List<String> getBuildables() {

        int wood = count(Wood.class);
        int arrows = count(Arrow.class);
        int treasure = count(Treasure.class);
        int keys = count(Key.class);
        List<String> result = new ArrayList<>();

        if (wood >= 1 && arrows >= 3) {
            result.add("bow");
        }
        if (wood >= 2 && (treasure >= 1 || keys >= 1)) {
            result.add("shield");
        }

        if ((wood >= 1 || arrows >= 2) && (keys >= 1 || treasure >= 1) && count(SunStone.class) >= 1) {
            result.add("sceptre");
        }

        if (count(Sword.class) >= 1 && count(SunStone.class) >= 1 && noZombiesPresent()) {
            result.add("midnight_armour");
        }

        return result;
    }

    // Check whether a player has the supplies to build a particular buildable. If so, build the item.
    public InventoryItem checkBuildCriteria(Player p, BuildableType type, EntityFactory factory) {
        if (type == BuildableType.BOW) {
            return factory.buildBow(getEntities(Wood.class), getEntities(Arrow.class), items);
        } else if (type == BuildableType.SHIELD) {
            return factory.buildShield(getEntities(Wood.class), getEntities(Treasure.class), getEntities(Key.class),
                    items);
        } else if (type == BuildableType.SCEPTRE) {
            List<Wood> woods = getEntities(Wood.class);
            List<Arrow> arrows = getEntities(Arrow.class);
            List<Key> keys = getEntities(Key.class);
            List<Treasure> treasures = getEntities(Treasure.class);
            List<SunStone> sunStones = getEntities(SunStone.class);
            return factory.buildSceptre(woods, arrows, keys, treasures, sunStones, items, factory.getConfig());
        } else if (type == BuildableType.MIDNIGHT_ARMOUR) {
            List<Sword> swords = getEntities(Sword.class);
            List<SunStone> sunStones = getEntities(SunStone.class);
            return factory.buildMidnightArmour(swords, sunStones, items, factory.getGame(), factory.getConfig());
        }
        return null;
    }

    public <T extends InventoryItem> T getFirst(Class<T> itemType) {
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                return itemType.cast(item);
        return null;
    }

    public <T extends InventoryItem> int count(Class<T> itemType) {
        int count = 0;
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                count++;
        return count;
    }

    public Entity getEntity(String itemUsedId) {
        for (InventoryItem item : items)
            if (item.getId().equals(itemUsedId))
                return item;
        return null;
    }

    public List<Entity> getEntities() {
        return items.stream().map(Entity.class::cast).collect(Collectors.toList());
    }

    public <T> List<T> getEntities(Class<T> clz) {
        return items.stream().filter(clz::isInstance).map(clz::cast).collect(Collectors.toList());
    }

    public boolean hasWeapon() {
        return getFirst(Sword.class) != null || getFirst(Bow.class) != null;
    }

    public Useable getWeapon() {
        Useable weapon = getFirst(Sword.class);
        if (weapon == null)
            return getFirst(Bow.class);
        return weapon;
    }

}
