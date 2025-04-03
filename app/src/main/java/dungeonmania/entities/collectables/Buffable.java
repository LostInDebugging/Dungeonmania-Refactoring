package dungeonmania.entities.collectables;

import dungeonmania.battles.BattleStatistics;

public interface Buffable {
    public BattleStatistics applyBuff(BattleStatistics origin);
}
