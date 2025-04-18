package dungeonmania.entities.playerState;

import dungeonmania.entities.Player;

public abstract class PlayerState {
    private Player player;

    PlayerState(Player player) {
        this.player = player;
    }

    public boolean isInvincible() {
        return this instanceof InvincibleState;
    };

    public boolean isInvisible() {
        return this instanceof InvisibleState;
    };

    public Player getPlayer() {
        return player;
    }

    public abstract void transitionInvisible();

    public abstract void transitionInvincible();

    public abstract void transitionBase();
}
