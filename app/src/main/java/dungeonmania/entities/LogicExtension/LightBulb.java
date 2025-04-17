package dungeonmania.entities.LogicExtension;

import dungeonmania.entities.Entity;
import dungeonmania.entities.StaticEntities.StaticEntity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class LightBulb extends StaticEntity implements LogicalEntity {
    private LogicCondition lc;
    private boolean isOn = false;

    public LightBulb(Position position, LogicCondition lc) {
        super(position);
        this.lc = lc;
    }

    @Override
    public void switchState(GameMap map) {
        isOn = lc.isSatisfied(map, this.getPosition());
    }

    public boolean isOn() {
        return isOn;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }
}
