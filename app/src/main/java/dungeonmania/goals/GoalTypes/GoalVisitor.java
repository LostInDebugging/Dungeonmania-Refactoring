package dungeonmania.goals.GoalTypes;

public interface GoalVisitor<R> {
    public R visit(ExitGoal goal);

    public R visit(EnemyGoal goal);

    public R visit(TreasureGoal goal);

    public R visit(BoulderGoal goal);

    public R visit(AndGoal goal);

    public R visit(OrGoal goal);
}
