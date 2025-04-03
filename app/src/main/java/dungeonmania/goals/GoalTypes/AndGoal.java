package dungeonmania.goals.GoalTypes;

import dungeonmania.Game;

public class AndGoal extends CompoundGoal {
    public AndGoal(Goal goal1, Goal goal2) {
        super(goal1, goal2);
    }

    public boolean achieved(Game game) {
        return getGoal1().achieved(game) && getGoal2().achieved(game);
    }

    public String toString(Game game) {
        return achieved(game) ? "" : "(" + getGoal1().toString(game) + " AND " + getGoal2().toString(game) + ")";
    }
}
