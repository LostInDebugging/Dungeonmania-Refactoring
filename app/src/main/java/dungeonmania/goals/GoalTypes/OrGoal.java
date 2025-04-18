package dungeonmania.goals.GoalTypes;

public class OrGoal extends CompoundGoal {
    public OrGoal(Goal goal1, Goal goal2) {
        super(goal1, goal2);
    }

    @Override
    public <R> R accept(GoalVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
