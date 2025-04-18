package dungeonmania.entities.LogicExtension;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public interface LogicCondition {
    public boolean isSatisfied(GameMap map, Position pos);
}
