package dungeonmania.entities.LogicExtension.LogicConditions;

import java.util.List;

import dungeonmania.entities.LogicExtension.Conductor;
import dungeonmania.entities.LogicExtension.LogicCondition;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class CoAndCondition implements LogicCondition {
    @Override
    public boolean isSatisfied(GameMap map, Position pos) {
        List<Position> adjacents = pos.getCardinallyAdjacentPositions();

        int count = 0;
        boolean first = true;
        int tickStarted = -1;

        // they must all be activated, and there must be two or more
        for (Position position : adjacents) {
            Conductor conductor = map.positionContainsEntity(position, Conductor.class);
            if (conductor != null) {
                if (first) {
                    first = false;
                    tickStarted = conductor.getTickStartedConducting();
                }
                if (!conductor.isConducting() || conductor.getTickStartedConducting() != tickStarted) {
                    return false;
                } else {
                    count++;
                }
            }
        }

        return count > 1;
    }
}
