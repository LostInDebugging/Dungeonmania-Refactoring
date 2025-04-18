package dungeonmania.goals.GoalTypes;

public class AndGoal extends CompoundGoal {
    public AndGoal(Goal goal1, Goal goal2) {
        super(goal1, goal2);
    }

    @Override
    public <R> R accept(GoalVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
