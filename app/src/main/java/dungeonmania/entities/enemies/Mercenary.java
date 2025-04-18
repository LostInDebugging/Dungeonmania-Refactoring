package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.PotionListener;
import dungeonmania.entities.buildables.Sceptre; // import for sceptre check
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.entities.collectables.potions.Potion;
import dungeonmania.entities.enemies.MovementStrategy.AlliedStrategy;
import dungeonmania.entities.enemies.MovementStrategy.HostileStrategy;
import dungeonmania.entities.enemies.MovementStrategy.RandomStrategy;
import dungeonmania.entities.enemies.MovementStrategy.RunawayStrategy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Mercenary extends Enemy implements Interactable, PotionListener {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;

    private int bribeAmount = DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = DEFAULT_BRIBE_RADIUS;

    private double allyAttack;
    private double allyDefence;
    private boolean allied = false;
    private int mindControlTicks = 0;

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius,
            double allyAttack, double allyDefence) {
        super(position, health, attack, new HostileStrategy());
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        this.allyAttack = allyAttack;
        this.allyDefence = allyDefence;
    }

    public boolean isAllied() {
        return allied;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (allied)
            return;
        super.onOverlap(map, entity);
    }

    /**
     * check whether the current merc can be bribed
     */
    private boolean canBeBribed(Player player) {
        return bribeRadius >= 0 && player.countEntityOfType(Treasure.class) >= bribeAmount;
    }

    /**
     * If the player has a sceptre, mind control is applied and no treasure is consumed.
     * Otherwise, if the player can bribe the mercenary
     * (has sufficient treasure and is within range), bribe is executed.
     */
    @Override
    public void interact(Player player, Game game) {
        Sceptre sceptre = player.getInventory().getFirst(Sceptre.class);
        if (sceptre != null) {
            allied = true;
            setMovementStrategy(new AlliedStrategy());
            mindControlTicks = sceptre.getMindControlDuration();
        } else if (isInteractable(player)) {
            allied = true;
            setMovementStrategy(new AlliedStrategy());
            bribe(player);
        } else {
            throw new IllegalStateException("Mercenary cannot be interacted with.");
        }
    }

    // Normal bribery: consume the required treasure(s).
    private void bribe(Player player) {
        for (int i = 0; i < bribeAmount; i++) {
            player.use(Treasure.class);
        }

    }

    @Override
    public boolean isInteractable(Player player) {
        if (allied)
            return false;
        if (player.getInventory().getFirst(Sceptre.class) != null) {
            return true;
        }
        return canBeBribed(player);
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        if (!allied) {
            return super.getBattleStatistics();
        }
        return new BattleStatistics(0, allyAttack, allyDefence, 1, 1);
    }

    @Override
    public void notifyPotion(Potion potion) {
        if (allied)
            return;

        if (potion instanceof InvisibilityPotion) {
            setMovementStrategy(new RandomStrategy());
        }
        if (potion instanceof InvincibilityPotion) {
            setMovementStrategy(new RunawayStrategy());
        }
    }

    @Override
    public void notifyNoPotion() {
        if (allied) {
            return;
        }

        setMovementStrategy(new HostileStrategy());
    }

    public void onTick() {
        if (allied && mindControlTicks > 0) {
            mindControlTicks--;
            if (mindControlTicks == 0) {
                allied = false;
                setMovementStrategy(new HostileStrategy());
            }
        }
    }
}
