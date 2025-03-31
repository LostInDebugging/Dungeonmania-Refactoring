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
        List<Position> pos = enemy.getPosition().getCardinallyAdjacentPositions();
        pos = pos.stream().filter(p -> map.canMoveTo(enemy, p)).toList();
        if (pos.size() == 0) {
            map.moveTo(enemy, enemy.getPosition());
        } else {
            map.moveTo(enemy, pos.get(randGen.nextInt(pos.size())));
        }
    }
}
