package dungeonmania.goals.GoalTypes;

import dungeonmania.Game;
import dungeonmania.entities.Switch;

public class BoulderGoal extends BasicGoal {
    @Override
    public boolean achieved(Game game) {
        return game.getMap().getEntities(Switch.class).stream().allMatch(s -> s.isActivated());
    }

    @Override
    public String toString(Game game) {
        return achieved(game) ? "" : ":boulders";
    }
}
