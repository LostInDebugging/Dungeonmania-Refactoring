package dungeonmania.entities.enemies.MovementStrategy;

import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class AlliedStrategy implements MovementStrategy {
    private boolean wasAdjacentToPlayer = false;

    @Override
    public void move(GameMap map, Enemy enemy, Player player) {
        boolean isAdjacentToPlayer = Position.isAdjacent(player.getPosition(), enemy.getPosition());
        if (wasAdjacentToPlayer && !isAdjacentToPlayer) {
            map.moveTo(enemy, player.getPreviousDistinctPosition());
        } else {
            // If currently still adjacent, wait in place. Else pursue the player.
            Position nextPos = isAdjacentToPlayer ? enemy.getPosition()
                    : map.dijkstraPathFind(enemy.getPosition(), player.getPosition(), enemy);
            wasAdjacentToPlayer = Position.isAdjacent(player.getPosition(), nextPos);
            map.moveTo(enemy, nextPos);
        }
    }

}
