package dungeonmania.entities.collectables;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.StaticEntities.Switch;
import dungeonmania.map.GameMap;

public class Bomb extends BasicBomb {
    private List<Switch> subs = new ArrayList<>();

    public Bomb(Position position, int radius) {
        super(position, radius);
    }

    public void subscribe(Switch s) {
        this.subs.add(s);
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (getState() != State.SPAWNED)
            return;
        if (entity instanceof Player player) {
            if (!player.pickUp(this))
                return;
            map.destroyEntity(this);
        }
    }

    @Override
    public void onPutDown(GameMap map, Position p) {
        super.onPutDown(map, p);
        List<Position> adjPosList = getPosition().getCardinallyAdjacentPositions();
        adjPosList.stream().forEach(node -> {
            List<Entity> entities = map.getEntities(node).stream().filter(Switch.class::isInstance).toList();
            entities.stream().map(Switch.class::cast).forEach(s -> s.subscribe(this, map));
        });
    }
}
