package dungeonmania.entities.enemies;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.Battleable;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.PotionListener;
import dungeonmania.entities.enemies.MovementStrategy.MovementStrategy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Enemy extends Entity implements Battleable, Destroyable {
    private BattleStatistics battleStatistics;
    private MovementStrategy movementStrategy;

    public Enemy(Position position, double health, double attack, MovementStrategy movementStrategy) {
        super(position.asLayer(Entity.CHARACTER_LAYER));
        battleStatistics = new BattleStatistics(health, attack, 0, BattleStatistics.DEFAULT_DAMAGE_MAGNIFIER,
                BattleStatistics.DEFAULT_ENEMY_DAMAGE_REDUCER);
        this.movementStrategy = movementStrategy;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return entity instanceof Player;
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        return battleStatistics;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player player) {
            map.getGame().battle(player, this);
        }
    }

    public void onDestroy(GameMap map) {
        Game g = map.getGame();
        g.unsubscribe(getId());
        if (this instanceof PotionListener potionListener)
            map.getPlayer().removePotionListener(potionListener);
    }

    public void move(Game game) {
        movementStrategy.move(game.getMap(), this, game.getPlayer());
    }

    public double getHealth() {
        return getBattleStatistics().getHealth();
    }

    protected void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

    public List<Position> getValidMovementPositions(GameMap map) {
        return this.getPosition().getCardinallyAdjacentPositions().stream().filter(p -> map.canMoveTo(this, p))
                .collect(Collectors.toList());
    }
}
