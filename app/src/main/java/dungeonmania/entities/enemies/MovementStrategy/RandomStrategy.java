package dungeonmania.entities.enemies.MovementStrategy;

import java.util.List;
import java.util.Random;

import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class RandomStrategy implements MovementStrategy {
    private Random randGen = new Random();

    @Override
    public void move(GameMap map, Enemy enemy, Player player) {
        List<Position> validPositions = enemy.getValidMovementPositions(map);
        if (validPositions.isEmpty()) {
            map.moveTo(enemy, enemy.getPosition());
        } else {
            map.moveTo(enemy, validPositions.get(randGen.nextInt(validPositions.size())));
        }
    }
}
