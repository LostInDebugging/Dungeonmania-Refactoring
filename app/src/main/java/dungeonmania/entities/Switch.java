package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.LogicExtension.Conductor;
import dungeonmania.entities.LogicExtension.Wire;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Switch extends StaticEntity implements Conductor {
    private boolean activated;
    private List<Bomb> bombs = new ArrayList<>();

    private List<Wire> subscribers = new ArrayList<>();
    private List<Position> checkedWirePositions = new ArrayList<>();
    private int tickStarted = -1;

    public void findSubscribers(GameMap map, Position currPos) {
        checkedWirePositions.add(currPos);
        for (Position pos : currPos.getCardinallyAdjacentPositions()) {
            Wire wire = map.positionContainsEntity(pos, Wire.class);
            if (!checkedWirePositions.contains(pos) && wire != null) {
                subscribers.add(wire);
                wire.subscribeToSwitch(this);
                findSubscribers(map, wire.getPosition());
            }
        }
    }

    public Switch(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER));
    }

    public void subscribe(Bomb b) {
        bombs.add(b);
    }

    public void subscribe(Bomb bomb, GameMap map) {
        bombs.add(bomb);
        if (activated) {
            activateBombs(map);
        }
    }

    public void unsubscribe(Bomb b) {
        bombs.remove(b);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = true;
            activateBombs(map);
        }
    }

    public void onOverlap(GameMap map, Entity entity, int currTick) {
        if (entity instanceof Boulder) {
            activated = true;
            tickStarted = currTick;
            subscribers.forEach(w -> w.notifyConducting(currTick));
            activateBombs(map);
        }
    }

    public void activateBombs(GameMap map) {
        for (Bomb b : bombs) {
            b.activate(map);
        }
    }

    public void onMovedAway(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = false;
            tickStarted = -1;
            subscribers.forEach(w -> w.notifyConducting(-1));
        }
    }

    public boolean isActivated() {
        return activated;
    }

    @Override
    public boolean isConducting() {
        return activated;
    }

    @Override
    public int getTickStartedConducting() {
        return tickStarted;
    }
}
