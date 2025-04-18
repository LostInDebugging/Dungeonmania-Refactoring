package dungeonmania.entities.collectables.potions;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.collectables.Buffable;
import dungeonmania.entities.collectables.CollectableItem;
import dungeonmania.util.Position;

public abstract class Potion extends CollectableItem implements Buffable {
    private int duration;

    public Potion(Position position, int duration) {
        super(position);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return origin;
    }
}
