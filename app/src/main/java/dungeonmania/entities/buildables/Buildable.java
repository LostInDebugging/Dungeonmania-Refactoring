package dungeonmania.entities.buildables;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public interface Buildable {
    public void onOverlap(GameMap map, Entity entity);
}
