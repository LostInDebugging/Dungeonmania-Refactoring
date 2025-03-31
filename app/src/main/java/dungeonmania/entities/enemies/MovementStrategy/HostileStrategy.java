package dungeonmania.entities.enemies.MovementStrategy;

import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;

public class HostileStrategy implements MovementStrategy {
    @Override
    public void move(GameMap map, Enemy enemy, Player player) {
        map.moveTo(enemy, map.dijkstraPathFind(enemy.getPosition(), player.getPosition(), enemy));
    }

}
