package dungeonmania.goals;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.goals.GoalTypes.AndGoal;
import dungeonmania.goals.GoalTypes.BoulderGoal;
import dungeonmania.goals.GoalTypes.EnemyGoal;
import dungeonmania.goals.GoalTypes.ExitGoal;
import dungeonmania.goals.GoalTypes.Goal;
import dungeonmania.goals.GoalTypes.OrGoal;
import dungeonmania.goals.GoalTypes.TreasureGoal;

public class GoalFactory {
    public static Goal createGoal(JSONObject jsonGoal, JSONObject config) {
        JSONArray subgoals;
        return switch (jsonGoal.getString("goal")) {
        case "AND" -> {
            subgoals = jsonGoal.getJSONArray("subgoals");
            yield new AndGoal(createGoal(subgoals.getJSONObject(0), config),
                    createGoal(subgoals.getJSONObject(1), config));
        }
        case "OR" -> {
            subgoals = jsonGoal.getJSONArray("subgoals");
            yield new OrGoal(createGoal(subgoals.getJSONObject(0), config),
                    createGoal(subgoals.getJSONObject(1), config));
        }
        case "exit" -> new ExitGoal();
        case "boulders" -> new BoulderGoal();
        case "treasure" -> {
            int targetAmount = config.optInt("treasure_goal", 1);
            yield new TreasureGoal(targetAmount);
        }
        case "enemies" -> {
            int enemyTarget = config.optInt("enemy_goal", 0); // use default if not specified
            yield new EnemyGoal(enemyTarget);
        }
        default -> null;
        };
    }
}
