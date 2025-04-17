package dungeonmania.goals.GoalTypes;

import dungeonmania.Game;

public interface Goal {
    boolean achieved(Game game);

    String toString(Game game);

    <R> R accept(GoalVisitor<R> visitor);
}
