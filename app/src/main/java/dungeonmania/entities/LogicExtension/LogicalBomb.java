package dungeonmania.entities.LogicExtension;

import dungeonmania.util.Position;
import dungeonmania.entities.collectables.BasicBomb;
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
