package dungeonmania.entities.collectables;

import dungeonmania.util.Position;

import dungeonmania.entities.LogicExtension.LogicCondition;
import dungeonmania.entities.LogicExtension.LogicalEntity;
import dungeonmania.map.GameMap;

public class LogicalBomb extends BasicBomb implements LogicalEntity {
    private LogicCondition lc;

    public LogicalBomb(Position position, int radius, LogicCondition lc) {
        super(position, radius);
        this.lc = lc;
    }

    @Override
    public void switchState(GameMap map) {
        if (lc.isSatisfied(map, getPosition())) {
            this.activate(map);
        }
    }


}
