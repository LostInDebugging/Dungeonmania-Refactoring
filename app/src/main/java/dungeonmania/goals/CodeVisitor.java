package dungeonmania.goals;

import dungeonmania.goals.GoalTypes.*;

public class CodeVisitor implements GoalVisitor<String> {
    private dungeonmania.Game game;

    public CodeVisitor(dungeonmania.Game game) {
        this.game = game;
    }

    @Override
    public String visit(ExitGoal g) {
        return g.achieved(game) ? "" : ":exit";
    }

    @Override
    public String visit(BoulderGoal g) {
        return g.achieved(game) ? "" : ":boulders";
    }

    @Override
    public String visit(TreasureGoal g) {
        return g.achieved(game) ? "" : ":treasure";
    }

    @Override
    public String visit(EnemyGoal g) {
        return g.achieved(game) ? "" : ":enemies";
    }

    @Override
    public String visit(AndGoal g) {
        String left = g.getGoal1().toString(game);
        String right = g.getGoal2().toString(game);
        if (left.isEmpty())
            return right;
        if (right.isEmpty())
            return left;
        return "(" + left + " AND " + right + ")";
    }

    @Override
    public String visit(OrGoal g) {
        String left = g.getGoal1().toString(game);
        String right = g.getGoal2().toString(game);
        if (left.isEmpty() && right.isEmpty())
            return "";
        return "(" + left + " OR " + right + ")";
    }
}
