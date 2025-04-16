package dungeonmania.entities.LogicExtension.LogicConditions;

import java.util.List;

import dungeonmania.entities.LogicExtension.Conductor;
import dungeonmania.entities.LogicExtension.LogicCondition;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class XorCondition implements LogicCondition {
    @Override
    public boolean isSatisfied(GameMap map, Position pos) {
        List<Position> adjacents = pos.getCardinallyAdjacentPositions();
        boolean foundOneActiveConductor = false;

        for (Position position : adjacents) {
            Conductor conductor = map.positionContainsEntity(position, Conductor.class);
            if (conductor != null && conductor.isConducting()) {
                if (foundOneActiveConductor) {
                    return false;
                }
                foundOneActiveConductor = true;
            }
        }

        return true;
    }

}
