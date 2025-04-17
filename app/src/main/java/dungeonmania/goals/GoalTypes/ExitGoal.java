package dungeonmania.goals.GoalTypes;

public class ExitGoal extends BasicGoal {
    public ExitGoal() {
    }

    @Override
    public <R> R accept(GoalVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
