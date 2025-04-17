package dungeonmania.entities.buildables;

import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.util.Position;

public class MidnightArmour extends InventoryItem {
    private double attackBonus;
    private double defenceBonus;

    public MidnightArmour(Position position, double attackBonus, double defenceBonus) {
        super(position);
        this.attackBonus = attackBonus;
        this.defenceBonus = defenceBonus;
    }

    public double getAttackBonus() {
        return attackBonus;
    }

    public double getDefenceBonus() {
        return defenceBonus;
    }
}
