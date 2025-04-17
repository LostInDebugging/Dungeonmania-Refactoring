package dungeonmania.goals.GoalTypes;

public class TreasureGoal extends BasicGoal {
    private int treasureTarget;

    public TreasureGoal(int treasureTarget) {
        this.treasureTarget = treasureTarget;
    }

    public int getTreasureTarget() {
        return treasureTarget;
    }

    @Override
    public <R> R accept(GoalVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
