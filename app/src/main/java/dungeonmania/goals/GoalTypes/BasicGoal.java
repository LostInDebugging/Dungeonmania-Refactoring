package dungeonmania.goals.GoalTypes;

import dungeonmania.Game;
import dungeonmania.goals.AchievedVisitor;
import dungeonmania.goals.CodeVisitor;

public abstract class BasicGoal implements Goal {
    @Override
    public boolean achieved(Game game) {
        return accept(new AchievedVisitor(game));
    }

    @Override
    public String toString(Game game) {
        return accept(new CodeVisitor(game));
    }

    @Override
    public abstract <R> R accept(GoalVisitor<R> visitor);
}
