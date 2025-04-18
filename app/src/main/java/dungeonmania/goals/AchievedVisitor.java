package dungeonmania.goals;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.StaticEntities.Exit;
import dungeonmania.entities.Player;
import dungeonmania.entities.StaticEntities.Switch;
import dungeonmania.entities.enemies.ZombieToastSpawner;
import dungeonmania.util.Position;
import dungeonmania.goals.GoalTypes.*;

public class AchievedVisitor implements GoalVisitor<Boolean> {
    private Game game;

    public AchievedVisitor(Game game) {
        this.game = game;
    }

    @Override
    public Boolean visit(ExitGoal g) {
        Player p = game.getPlayer();
        Position pos = p.getPosition();
        List<Exit> exits = game.getMap().getEntities(Exit.class);
        return !exits.isEmpty() && exits.stream().map(Exit::getPosition).anyMatch(pos::equals);
    }

    @Override
    public Boolean visit(BoulderGoal g) {
        return game.getMap().getEntities(Switch.class).stream().allMatch(Switch::isActivated);
    }

    @Override
    public Boolean visit(TreasureGoal g) {
        return game.getCollectedTreasureCount() >= g.getTreasureTarget();
    }

    @Override
    public Boolean visit(EnemyGoal g) {
        boolean defeatedEnough = game.getDefeatedEnemies() >= g.getEnemyTarget();
        boolean spawnersCleared = game.getMap().getEntities(ZombieToastSpawner.class).isEmpty();
        return defeatedEnough && spawnersCleared;
    }

    @Override
    public Boolean visit(AndGoal g) {
        return g.getGoal1().accept(this) && g.getGoal2().accept(this);
    }

    @Override
    public Boolean visit(OrGoal g) {
        return g.getGoal1().accept(this) || g.getGoal2().accept(this);
    }
}
