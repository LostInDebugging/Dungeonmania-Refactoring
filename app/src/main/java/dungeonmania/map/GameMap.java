package dungeonmania.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.entities.Boulder;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.PotionListener;
import dungeonmania.entities.LogicExtension.Wire;
import dungeonmania.entities.StaticEntities.Portal;
import dungeonmania.entities.StaticEntities.Switch;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.enemies.Destroyable;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.ZombieToastSpawner;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

/**
 * Class representing the map of the game
 */
public class GameMap {
    private Game game;
    private Map<Position, GraphNode> nodes = new HashMap<>(); //The tiles of the map
    private Player player;

    /**
     * Initialise the game map
     * 1. pair up portals
     * 2. register all movables
     * 3. register all spawners
     * 4. register bombs and switches
     * 5. link all switches and wires
     */
    public void init() {
        initPairPortals();
        initRegisterMovables();
        initRegisterSpawners();
        initRegisterBombsAndSwitches();
        initRegisterSwitchesAndWires();
        initPotionListeners();
    }

    // Subscribe bombs and switches to each other
    private void initRegisterBombsAndSwitches() {
        getEntities(Bomb.class).forEach(b -> getEntities(Switch.class).forEach((s) -> {
            if (Position.isAdjacent(b.getPosition(), s.getPosition())) {
                b.subscribe(s);
                s.subscribe(b);
            }
        }));
    }

    // Subscribe Switches and wires to each other
    private void initRegisterSwitchesAndWires() {
        getEntities(Switch.class).forEach(s -> {
            dfsFindConnectedWires(s, new HashSet<>(), s.getPosition());
        });
    }

    private void dfsFindConnectedWires(Switch sw, Set<Position> checkedWirePositions, Position currPos) {
        checkedWirePositions.add(currPos);
        currPos.getCardinallyAdjacentPositions().forEach(pos -> {
            Wire wire = positionContainsEntity(pos, Wire.class);
            if (!checkedWirePositions.contains(pos) && wire != null) {
                sw.addWireSubscriber(wire);
                wire.subscribeToSwitch(sw);
                dfsFindConnectedWires(sw, checkedWirePositions, pos);
            }
        });
    }

    // Pair up portals if there's any
    private void initPairPortals() {
        Map<String, Portal> portalsMap = new HashMap<>();
        nodes.forEach((k, v) -> {
            v.getEntities().stream().filter(Portal.class::isInstance).map(Portal.class::cast).forEach(portal -> {
                String color = portal.getColor();
                if (portalsMap.containsKey(color)) {
                    portal.bind(portalsMap.get(color));
                } else {
                    portalsMap.put(color, portal);
                }
            });
        });
    }

    // Register each enemy to move on each tick.
    private void initRegisterMovables() {
        getEntities(Enemy.class).forEach(e -> {
            game.register(() -> e.move(game), Game.AI_MOVEMENT, e.getId());
        });
    }

    // Register each zombie toast spawner to attempt to spawn an enemy each tick
    // as well as initialise the spider spawning mechanic.
    private void initRegisterSpawners() {
        getEntities(ZombieToastSpawner.class).forEach(e -> {
            game.register(() -> e.spawn(game), Game.AI_MOVEMENT, e.getId());
        });
        game.register(() -> game.getEntityFactory().spawnSpider(game), Game.AI_MOVEMENT, "spawnSpiders");
    }

    // Initialise and register "potion listeners" to be responsive to player potion updates
    private void initPotionListeners() {
        getEntities().stream().filter(PotionListener.class::isInstance).map(PotionListener.class::cast)
                .forEach(this::registerPotionListener);
    }

    public void registerPotionListener(PotionListener e) {
        player.registerPotionListener(e);
    }

    // Move an entity to a position
    public void moveTo(Entity entity, Position position) {
        if (!canMoveTo(entity, position)) {
            return;
        }

        triggerMovingAwayEvent(entity);
        removeNode(entity);
        entity.setPosition(position);
        addEntity(entity);
        triggerOverlapEvent(entity);
    }

    // Move an entity in a given direction
    public void moveTo(Entity entity, Direction direction) {
        Position newPos = Position.translateBy(entity.getPosition(), direction);
        if (!canMoveTo(entity, Position.translateBy(entity.getPosition(), direction))) {
            return;
        }
        triggerMovingAwayEvent(entity);
        removeNode(entity);
        entity.setPosition(newPos);
        addEntity(entity);
        triggerOverlapEvent(entity);
    }

    /*
     * changed version - Calls onMovedAway only for switches, other entities do nothing when moved away from.
     */
    private void triggerMovingAwayEvent(Entity entity) {
        // if a boulder is moving away from a switch then call onMovedAway for switch
        List<Runnable> callbacks = new ArrayList<>();
        if (entity instanceof Boulder) {
            getEntities(entity.getPosition()).forEach(e -> {
                if (e instanceof Switch s) {
                    callbacks.add(() -> s.onMovedAway(this, entity, game.getTick()));
                }
            });
        }
        callbacks.forEach(callback -> {
            callback.run();
        });

    }

    /*
     * Calls onOverlap for each entity on a tile, given an entity overlapping them.
     * Notably, it calls the onOverlap method of all entities being overlapped onto
     * not the entity that is doing the overlapping
     */
    private void triggerOverlapEvent(Entity entity) {
        List<Runnable> overlapCallbacks = new ArrayList<>();
        getEntities(entity.getPosition()).forEach(e -> {
            if (e instanceof Switch s && entity instanceof Boulder) {
                overlapCallbacks.add(() -> s.onOverlap(this, entity, game.getTick()));
            } else if (e != entity)
                overlapCallbacks.add(() -> e.onOverlap(this, entity));
        });
        overlapCallbacks.forEach(callback -> {
            callback.run();
        });
    }

    public boolean canMoveTo(Entity entity, Position position) {
        return !nodes.containsKey(position) || nodes.get(position).canMoveOnto(this, entity);
    }

    public Position dijkstraPathFind(Position src, Position dest, Entity entity) {
        // if inputs are invalid, don't move
        if (!nodes.containsKey(src) || !nodes.containsKey(dest)) {
            return src;
        }

        Map<Position, Integer> dist = new HashMap<>();
        Map<Position, Position> prev = new HashMap<>();
        Map<Position, Boolean> visited = new HashMap<>();

        prev.put(src, null);
        dist.put(src, 0);

        PriorityQueue<Position> q = new PriorityQueue<>((x, y) -> Integer
                .compare(dist.getOrDefault(x, Integer.MAX_VALUE), dist.getOrDefault(y, Integer.MAX_VALUE)));
        q.add(src);

        while (!q.isEmpty()) {
            Position curr = q.poll();
            if (curr.equals(dest) || dist.get(curr) > 200)
                break;
            // check portal
            if (nodes.containsKey(curr) && nodes.get(curr).getEntities().stream().anyMatch(Portal.class::isInstance)) {
                Portal portal = nodes.get(curr).getEntities().stream().filter(Portal.class::isInstance)
                        .map(Portal.class::cast).toList().get(0);
                List<Position> teleportDest = portal.getDestPositions(this, entity);
                teleportDest.stream().filter(p -> !visited.containsKey(p)).forEach(p -> {
                    dist.put(p, dist.get(curr));
                    prev.put(p, prev.get(curr));
                    q.add(p);
                });
                continue;
            }
            visited.put(curr, true);
            List<Position> neighbours = curr.getCardinallyAdjacentPositions().stream()
                    .filter(p -> !visited.containsKey(p))
                    .filter(p -> !nodes.containsKey(p) || nodes.get(p).canMoveOnto(this, entity)).toList();

            neighbours.forEach(n -> {
                int newDist = dist.get(curr) + (nodes.containsKey(n) ? nodes.get(n).getWeight() : 1);
                if (newDist < dist.getOrDefault(n, Integer.MAX_VALUE)) {
                    q.remove(n);
                    dist.put(n, newDist);
                    prev.put(n, curr);
                    q.add(n);
                }
            });
        }
        Position ret = dest;
        if (prev.get(ret) == null || ret.equals(src))
            return src;
        while (!prev.get(ret).equals(src)) {
            ret = prev.get(ret);
        }
        return ret;
    }

    public void removeNode(Entity entity) {
        Position p = entity.getPosition();
        if (nodes.containsKey(p)) {
            nodes.get(p).removeEntity(entity);
            if (nodes.get(p).size() == 0) {
                nodes.remove(p);
            }
        }
    }

    public void destroyEntitiesOnPosition(int x, int y) {
        List<Entity> entities = getEntities(new Position(x, y));
        entities = entities.stream().filter(Predicate.not(Player.class::isInstance)).toList();
        for (Entity e : entities) {
            destroyEntity(e);
        }
    }

    // Destroy an entity from the game map
    public void destroyEntity(Entity entity) {
        removeNode(entity);
        // clean up for entities that need to be cleaned up
        if (entity instanceof Destroyable d) {
            d.onDestroy(this);
        }
    }

    public void addEntity(Entity entity) {
        addNode(new GraphNode(entity));
    }

    public void addNode(GraphNode node) {
        Position p = node.getPosition();

        if (!nodes.containsKey(p))
            nodes.put(p, node);
        else {
            GraphNode curr = nodes.get(p);
            curr.mergeNode(node);
            nodes.put(p, curr);
        }
    }

    public Entity getEntity(String id) {
        Entity res = null;
        for (Map.Entry<Position, GraphNode> entry : nodes.entrySet()) {
            List<Entity> es = entry.getValue().getEntities().stream().filter(e -> e.getId().equals(id)).toList();
            if (!es.isEmpty()) {
                res = es.get(0);
                break;
            }
        }
        return res;
    }

    public List<Entity> getEntities(Position p) {
        GraphNode node = nodes.get(p);
        return (node != null) ? node.getEntities() : new ArrayList<>();
    }

    public List<Entity> getEntities() {
        List<Entity> entities = new ArrayList<>();
        nodes.forEach((k, v) -> entities.addAll(v.getEntities()));
        return entities;
    }

    public <T extends Entity> List<T> getEntities(Class<T> type) {
        return getEntities().stream().filter(type::isInstance).map(type::cast).collect(Collectors.toList());
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    // Given a position on the map and an entity type, returns the entity if it exists
    // there and null otherwise.
    public <T> T positionContainsEntity(Position pos, Class<T> entityType) {
        GraphNode node = nodes.get(pos);
        List<Entity> entities = (node != null) ? node.getEntities() : new ArrayList<>();
        return entities.stream().filter(e -> entityType.isInstance(e)).map(e -> entityType.cast(e)).findFirst()
                .orElse(null);
    }
}
