package dungeonmania.entities.LogicExtension;

import java.util.List;

import dungeonmania.entities.StaticEntity;
import dungeonmania.entities.Switch;
import dungeonmania.util.Position;

public class Wire extends StaticEntity implements Conductor {
    public Wire(Position position) {
        super(position);
    }

    private List<Switch> subscribers;

    private boolean isConducting = false;
    private int tickStarted = -1;

    public void notifyConducting(int currTick) {
        // if there is an active connected switch
        if (subscribers.stream().anyMatch(s -> s.isActivated())) {
            // if it is off, only then update the tick started
            if (!isConducting) {
                isConducting = true;
                tickStarted = currTick;
            }
        } else {
            // turn it off
            isConducting = false;
            tickStarted = -1;
        }
    }

    public int getTickStartedConducting() {
        return tickStarted;
    }

    public void subscribeToSwitch(Switch s) {
        subscribers.add(s);
    }

    @Override
    public boolean isConducting() {
        return isConducting;
    }
}
