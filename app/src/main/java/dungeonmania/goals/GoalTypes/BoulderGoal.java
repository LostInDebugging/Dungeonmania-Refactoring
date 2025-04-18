package dungeonmania.goals.GoalTypes;

public class BoulderGoal extends BasicGoal {
    public BoulderGoal() {
    }

    @Override
    public <R> R accept(GoalVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
