package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.collectables.Buffable;
import dungeonmania.entities.collectables.Useable;
import dungeonmania.entities.inventory.InventoryItem;

public class Bow extends InventoryItem implements Useable, Buffable {
    private int durability;

    public Bow(int durability) {
        super(null);
        this.durability = durability;
    }

    @Override
    public void use(Game game) {
        durability--;
        if (durability <= 0) {
            game.removeInventoryItemFromPlayer(this);
        }
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 2, 1));
    }

    @Override
    public int getDurability() {
        return durability;
    }
}
