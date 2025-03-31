package dungeonmania.entities.enemies.MovementStrategy;

import java.util.List;

import dungeonmania.entities.Boulder;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class LoopStrategy implements MovementStrategy {
    private List<Position> movementTrajectory;
    private int nextPositionElement = 1;
    private boolean forward = true;

    public LoopStrategy(List<Position> movementTrajectory) {
        this.movementTrajectory = movementTrajectory;
    }

    private void updateNextPosition() {
        if (forward) {
            nextPositionElement++;
            if (nextPositionElement == 8) {
                nextPositionElement = 0;
            }
        } else {
            nextPositionElement--;
            if (nextPositionElement == -1) {
                nextPositionElement = 7;
            }
        }
    }

    @Override
    public void move(GameMap map, Enemy enemy, Player player) {
        Position nextPos = movementTrajectory.get(nextPositionElement);
        List<Entity> entities = map.getEntities(nextPos);
        if (entities != null && entities.size() > 0 && entities.stream().anyMatch(Boulder.class::isInstance)) {
            forward = !forward;
            updateNextPosition();
            updateNextPosition();
        }
        nextPos = movementTrajectory.get(nextPositionElement);
        entities = map.getEntities(nextPos);
        if (entities == null || entities.size() == 0 || entities.stream().allMatch(e -> e.canMoveOnto(map, enemy))) {
            map.moveTo(enemy, nextPos);
            updateNextPosition();
        }
    }

}
