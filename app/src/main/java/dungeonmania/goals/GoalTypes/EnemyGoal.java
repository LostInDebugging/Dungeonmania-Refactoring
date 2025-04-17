package dungeonmania.goals.GoalTypes;

public class EnemyGoal extends BasicGoal {
    private int enemyTarget;

    public EnemyGoal(int enemyTarget) {
        this.enemyTarget = enemyTarget;
    }

    public int getEnemyTarget() {
        return enemyTarget;
    }

    @Override
    public <R> R accept(GoalVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
