package dungeonmania.entities.buildables;

import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.util.Position;

public class Sceptre extends InventoryItem {
    private int mindControlDuration;

    public Sceptre(Position position, int mindControlDuration) {
        super(position);
        this.mindControlDuration = mindControlDuration;
    }

    public int getMindControlDuration() {
        return mindControlDuration;
    }
}
