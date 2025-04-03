package dungeonmania.goals.GoalTypes;

import dungeonmania.Game;

public class TreasureGoal extends BasicGoal {
    private int targetAmount = 1;

    public TreasureGoal(int targetAmount) {
        this.targetAmount = targetAmount;
    }

    @Override
    public boolean achieved(Game game) {
        return game.getCollectedTreasureCount() >= targetAmount;
    }

    @Override
    public String toString(Game game) {
        return achieved(game) ? "" : ":treasure";
    }
}
