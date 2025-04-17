package dungeonmania.entities;

import dungeonmania.entities.LogicExtension.LogicCondition;
import dungeonmania.entities.LogicExtension.LogicalEntity;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwitchDoor extends BasicDoor implements LogicalEntity {
    private LogicCondition lc;

    public SwitchDoor(Position pos, LogicCondition lc) {
        super(pos);

        this.lc = lc;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (isOpen() || entity instanceof Spider) {
            return true;
        }
        return false;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        return;
    }

    @Override
    public void switchState(GameMap map) {
        setOpen(lc.isSatisfied(map, getPosition()));
    }
}
