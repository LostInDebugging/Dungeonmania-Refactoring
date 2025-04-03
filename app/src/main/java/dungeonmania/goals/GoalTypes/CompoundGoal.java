package dungeonmania.goals.GoalTypes;

import dungeonmania.Game;

public abstract class CompoundGoal implements Goal {
    private Goal goal1;
    private Goal goal2;

    public CompoundGoal(Goal goal1, Goal goal2) {
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    @Override
    public abstract boolean achieved(Game game);

    @Override
    public abstract String toString(Game game);

    protected Goal getGoal1() {
        return goal1;
    }

    protected Goal getGoal2() {
        return goal2;
    }
}
