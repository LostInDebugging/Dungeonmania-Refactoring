package dungeonmania.entities.LogicExtension;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.StaticEntities.StaticEntity;
import dungeonmania.entities.StaticEntities.Switch;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Wire extends StaticEntity implements Conductor {
    public Wire(Position position) {
        super(position);
    }

    private List<Switch> switches = new ArrayList<>();

    private boolean isConducting = false;
    private int tickStarted = -1;

    public void notifyConducting(int currTick) {
        // if there is an active connected switch
        if (switches.stream().anyMatch(s -> s.isActivated())) {
            // if it is off and the currTick is a differentTick, only then update the tick started
            if (!isConducting && tickStarted != currTick) {
                isConducting = true;
                tickStarted = currTick;
            }
        } else {
            // turn it off
            isConducting = false;
            tickStarted = currTick;
        }
    }

    public int getTickStartedConducting() {
        return tickStarted;
    }

    public void subscribeToSwitch(Switch s) {
        switches.add(s);
    }

    @Override
    public boolean isConducting() {
        return isConducting;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }
}
