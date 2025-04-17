package dungeonmania.goals.GoalTypes;

public interface GoalVisitor<R> {
    R visit(ExitGoal goal);

    R visit(EnemyGoal goal);

    R visit(TreasureGoal goal);

    R visit(BoulderGoal goal);

    R visit(AndGoal goal);

    R visit(OrGoal goal);
}
