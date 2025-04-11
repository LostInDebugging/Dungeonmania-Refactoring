package dungeonmania.goals.GoalTypes;

import dungeonmania.Game;
import dungeonmania.entities.enemies.ZombieToastSpawner;

public class EnemyGoal extends BasicGoal {
    private int enemyTarget;

    public EnemyGoal(int enemyTarget) {
        this.enemyTarget = enemyTarget;
    }

    @Override
    public boolean achieved(Game game) {
        boolean defeatedEnough = game.getDefeatedEnemies() >= enemyTarget;
        boolean spawnersCleared = game.getMap().getEntities(ZombieToastSpawner.class).isEmpty();
        return defeatedEnough && spawnersCleared;
    }

    @Override
    public String toString(Game game) {
        return achieved(game) ? "" : ":enemies";
    }
}
