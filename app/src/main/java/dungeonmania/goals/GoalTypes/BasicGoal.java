package dungeonmania.goals.GoalTypes;

import dungeonmania.Game;

public abstract class BasicGoal implements Goal {
    @Override
    public abstract boolean achieved(Game game);

    @Override
    public abstract String toString(Game game);
}
