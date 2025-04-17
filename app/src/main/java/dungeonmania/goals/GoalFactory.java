package dungeonmania.goals;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.goals.GoalTypes.*;

public class GoalFactory {
    public static Goal createGoal(JSONObject jsonGoal, JSONObject config) {
        String type = jsonGoal.getString("goal");
        JSONArray subgoals = jsonGoal.optJSONArray("subgoals");
        return switch (type) {
        case "exit" -> new ExitGoal();
        case "boulders" -> new BoulderGoal();
        case "treasure" -> {
            int t = config.optInt("treasure_goal", 1);
            yield new TreasureGoal(t);
        }
        case "enemies" -> {
            int e = config.optInt("enemy_goal", 0);
            yield new EnemyGoal(e);
        }
        case "AND", "and" -> {
            Goal g1 = createGoal(subgoals.getJSONObject(0), config);
            Goal g2 = createGoal(subgoals.getJSONObject(1), config);
            yield new AndGoal(g1, g2);
        }
        case "OR", "or" -> {
            Goal g1 = createGoal(subgoals.getJSONObject(0), config);
            Goal g2 = createGoal(subgoals.getJSONObject(1), config);
            yield new OrGoal(g1, g2);
        }
        default -> throw new IllegalArgumentException("Unknown goal: " + type);
        };
    }
}
