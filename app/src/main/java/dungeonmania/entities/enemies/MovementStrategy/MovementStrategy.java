package dungeonmania.entities.enemies.MovementStrategy;

import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;

public interface MovementStrategy {
    public void move(GameMap map, Enemy enemy, Player player);
}
