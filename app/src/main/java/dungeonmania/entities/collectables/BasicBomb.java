package dungeonmania.entities.collectables;

import dungeonmania.util.Position;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;

public class BasicBomb extends CollectableItem {
    public enum State {
        SPAWNED, PLACED
    }

    public static final int DEFAULT_RADIUS = 1;
    private State state;
    private int radius;

    public BasicBomb(Position position, int radius) {
        super(position);
        state = State.SPAWNED;
        this.radius = radius;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (state != State.SPAWNED)
            return;
        if (entity instanceof Player player) {
            if (!player.pickUp(this))
                return;
            map.destroyEntity(this);
        }
    }

    protected int getRadius() {
        return radius;
    }

    public void onPutDown(GameMap map, Position p) {
        setPosition(p);
        map.addEntity(this);
        this.state = State.PLACED;
    }

    public void activate(GameMap map) {
        int x = getPosition().getX();
        int y = getPosition().getY();
        for (int i = x - getRadius(); i <= x + getRadius(); i++) {
            for (int j = y - getRadius(); j <= y + getRadius(); j++) {
                map.destroyEntitiesOnPosition(i, j);
            }
        }
    }

    public State getState() {
        return state;
    }
}
