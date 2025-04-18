package dungeonmania.entities;

import dungeonmania.Game;
import dungeonmania.entities.LogicExtension.LightBulb;
import dungeonmania.entities.LogicExtension.LogicCondition;
import dungeonmania.entities.LogicExtension.LogicalBomb;
import dungeonmania.entities.LogicExtension.SwitchDoor;
import dungeonmania.entities.LogicExtension.Wire;
import dungeonmania.entities.LogicExtension.LogicConditions.AndCondition;
import dungeonmania.entities.LogicExtension.LogicConditions.CoAndCondition;
import dungeonmania.entities.LogicExtension.LogicConditions.OrCondition;
import dungeonmania.entities.LogicExtension.LogicConditions.XorCondition;
import dungeonmania.entities.StaticEntities.Door;
import dungeonmania.entities.StaticEntities.Exit;
import dungeonmania.entities.StaticEntities.Portal;
import dungeonmania.entities.StaticEntities.Switch;
import dungeonmania.entities.StaticEntities.Wall;
import dungeonmania.entities.buildables.Bow;
import dungeonmania.entities.buildables.MidnightArmour;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.buildables.Shield;
import dungeonmania.entities.collectables.*;
import dungeonmania.entities.enemies.*;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;

public class EntityFactory {
    private JSONObject config;
    private Game game;
    private Random ranGen = new Random();

    public EntityFactory(JSONObject config, Game game) {
        this.config = config;
        this.game = game;
    }

    public Entity createEntity(JSONObject jsonEntity) {
        return constructEntity(jsonEntity, config);
    }

    public void spawnSpider(Game game) {
        GameMap map = game.getMap();
        int tick = game.getTick();
        int rate = config.optInt("spider_spawn_interval", 0);
        if (rate == 0 || (tick + 1) % rate != 0)
            return;
        int radius = 20;
        Position player = map.getPlayer().getPosition();

        Spider dummySpider = buildSpider(new Position(0, 0)); // for checking possible positions

        List<Position> availablePos = new ArrayList<>();
        for (int i = player.getX() - radius; i < player.getX() + radius; i++) {
            for (int j = player.getY() - radius; j < player.getY() + radius; j++) {
                if (Position.calculatePositionBetween(player, new Position(i, j)).magnitude() > radius)
                    continue;
                Position np = new Position(i, j);
                if (!map.canMoveTo(dummySpider, np) || np.equals(player))
                    continue;
                if (map.getEntities(np).stream().anyMatch(Enemy.class::isInstance))
                    continue;
                availablePos.add(np);
            }
        }
        Position initPosition = availablePos.get(ranGen.nextInt(availablePos.size()));
        Spider spider = buildSpider(initPosition);
        map.addEntity(spider);
        game.register(() -> spider.move(game), Game.AI_MOVEMENT, spider.getId());
    }

    public void spawnZombie(Game game, ZombieToastSpawner spawner) {
        GameMap map = game.getMap();
        int tick = game.getTick();
        Random randGen = new Random();
        int spawnInterval = config.optInt("zombie_spawn_interval", ZombieToastSpawner.DEFAULT_SPAWN_INTERVAL);
        if (spawnInterval == 0 || (tick + 1) % spawnInterval != 0)
            return;
        List<Position> pos = spawner.getPosition().getCardinallyAdjacentPositions();
        pos = pos.stream().filter(p -> map.getEntities(p).stream().noneMatch(Wall.class::isInstance)).toList();
        if (pos.isEmpty())
            return;
        ZombieToast zt = buildZombieToast(pos.get(randGen.nextInt(pos.size())));
        map.addEntity(zt);
        map.registerPotionListener(zt);
        game.register(() -> zt.move(game), Game.AI_MOVEMENT, zt.getId());
    }

    public Spider buildSpider(Position pos) {
        double spiderHealth = config.optDouble("spider_health", Spider.DEFAULT_HEALTH);
        double spiderAttack = config.optDouble("spider_attack", Spider.DEFAULT_ATTACK);
        return new Spider(pos, spiderHealth, spiderAttack);
    }

    public Player buildPlayer(Position pos) {
        double playerHealth = config.optDouble("player_health", Player.DEFAULT_HEALTH);
        double playerAttack = config.optDouble("player_attack", Player.DEFAULT_ATTACK);
        return new Player(pos, playerHealth, playerAttack, game.getMap());
    }

    public ZombieToast buildZombieToast(Position pos) {
        double zombieHealth = config.optDouble("zombie_health", ZombieToast.DEFAULT_HEALTH);
        double zombieAttack = config.optDouble("zombie_attack", ZombieToast.DEFAULT_ATTACK);
        return new ZombieToast(pos, zombieHealth, zombieAttack);
    }

    public ZombieToastSpawner buildZombieToastSpawner(Position pos) {
        int zombieSpawnRate = config.optInt("zombie_spawn_interval", ZombieToastSpawner.DEFAULT_SPAWN_INTERVAL);
        return new ZombieToastSpawner(pos, zombieSpawnRate);
    }

    public Mercenary buildMercenary(Position pos) {
        double mercenaryHealth = config.optDouble("mercenary_health", Mercenary.DEFAULT_HEALTH);
        double mercenaryAttack = config.optDouble("mercenary_attack", Mercenary.DEFAULT_ATTACK);
        double allyAttack = config.optDouble("ally_attack", Mercenary.DEFAULT_HEALTH);
        double allyDefence = config.optDouble("ally_defence", Mercenary.DEFAULT_ATTACK);
        int mercenaryBribeAmount = config.optInt("bribe_amount", Mercenary.DEFAULT_BRIBE_AMOUNT);
        int mercenaryBribeRadius = config.optInt("bribe_radius", Mercenary.DEFAULT_BRIBE_RADIUS);
        return new Mercenary(pos, mercenaryHealth, mercenaryAttack, mercenaryBribeAmount, mercenaryBribeRadius,
                allyAttack, allyDefence);
    }

    public Bow buildBow(List<Wood> woods, List<Arrow> arrows, List<InventoryItem> items) {
        if (woods.size() >= 1 && arrows.size() >= 3) {
            items.removeAll(List.of(woods.get(0), arrows.get(0), arrows.get(1), arrows.get(2)));
        } else {
            return null;
        }
        int bowDurability = config.optInt("bow_durability");
        return new Bow(bowDurability);
    }

    public Shield buildShield(List<Wood> woods, List<Treasure> treasures, List<Key> keys, List<InventoryItem> items) {
        if (woods.size() >= 2 && (treasures.size() >= 1 || keys.size() >= 1)) {
            items.removeAll(List.of(woods.get(0), woods.get(1)));
            if (treasures.size() >= 1) {
                items.remove(treasures.get(0));
            } else {
                items.remove(keys.get(0));
            }
        } else {
            return null;
        }
        int shieldDurability = config.optInt("shield_durability");
        double shieldDefence = config.optInt("shield_defence");
        return new Shield(shieldDurability, shieldDefence);
    }

    public Sceptre buildSceptre(List<Wood> woods, List<Arrow> arrows, List<Key> keys, List<Treasure> treasures,
            List<SunStone> sunStones, List<InventoryItem> items, JSONObject config) {
        boolean slot1Satisfied = false;
        if (woods.size() >= 1) {
            items.remove(woods.get(0));
            slot1Satisfied = true;
        } else if (arrows.size() >= 2) {
            items.remove(arrows.get(0));
            items.remove(arrows.get(1));
            slot1Satisfied = true;
        }
        if (!slot1Satisfied) {
            return null;
        }

        boolean slot2Satisfied = false;
        boolean usedSunStoneForSlot2 = false;
        if (keys.size() >= 1) {
            items.remove(keys.get(0));
            slot2Satisfied = true;
        } else if (treasures.size() >= 1) {
            items.remove(treasures.get(0));
            slot2Satisfied = true;
        } else if (sunStones.size() >= 2) {
            items.remove(sunStones.get(0));
            usedSunStoneForSlot2 = true;
            slot2Satisfied = true;
        }
        if (!slot2Satisfied) {
            return null;
        }

        if (sunStones.size() < 1) {
            return null;
        }
        items.remove(sunStones.get(0));

        int duration = config.optInt("mind_control_duration", 2);

        return new Sceptre(null, duration);
    }

    public MidnightArmour buildMidnightArmour(List<Sword> swords, List<SunStone> sunStones, List<InventoryItem> items,
            Game game, JSONObject config) {

        if (!swords.isEmpty() && !sunStones.isEmpty()
                && game.getMap().getEntities(dungeonmania.entities.enemies.ZombieToast.class).isEmpty()) {
            items.remove(swords.get(0));
            items.remove(sunStones.get(0));
            double bonusAttack = config.optDouble("midnight_armour_attack", 2.0);
            double bonusDefence = config.optDouble("midnight_armour_defence", 2.0);
            return new MidnightArmour(null, bonusAttack, bonusDefence);
        }
        return null;
    }

    public JSONObject getConfig() {
        return config;
    }

    public Game getGame() {
        return game;
    }

    private Entity constructEntity(JSONObject jsonEntity, JSONObject config) {
        Position pos = new Position(jsonEntity.getInt("x"), jsonEntity.getInt("y"));

        switch (jsonEntity.getString("type")) {
        case "player":
            return buildPlayer(pos);
        case "zombie_toast":
            return buildZombieToast(pos);
        case "zombie_toast_spawner":
            return buildZombieToastSpawner(pos);
        case "mercenary":
            return buildMercenary(pos);
        case "wall":
            return new Wall(pos);
        case "boulder":
            return new Boulder(pos);
        case "switch":
            return new Switch(pos);
        case "exit":
            return new Exit(pos);
        case "treasure":
            return new Treasure(pos);
        case "wood":
            return new Wood(pos);
        case "arrow":
            return new Arrow(pos);
        case "bomb":
            int bombRadius = config.optInt("bomb_radius", Bomb.DEFAULT_RADIUS);
            if (jsonEntity.has("logic")) {
                return new LogicalBomb(pos, bombRadius, constructCondition(jsonEntity.getString("logic")));
            } else {
                return new Bomb(pos, bombRadius);
            }
        case "invisibility_potion":
            int invisibilityPotionDuration = config.optInt("invisibility_potion_duration",
                    InvisibilityPotion.DEFAULT_DURATION);
            return new InvisibilityPotion(pos, invisibilityPotionDuration);
        case "invincibility_potion":
            int invincibilityPotionDuration = config.optInt("invincibility_potion_duration",
                    InvincibilityPotion.DEFAULT_DURATION);
            return new InvincibilityPotion(pos, invincibilityPotionDuration);
        case "portal":
            return new Portal(pos, ColorCodedType.valueOf(jsonEntity.getString("colour")));
        case "sword":
            double swordAttack = config.optDouble("sword_attack", Sword.DEFAULT_ATTACK);
            int swordDurability = config.optInt("sword_durability", Sword.DEFAULT_DURABILITY);
            return new Sword(pos, swordAttack, swordDurability);
        case "spider":
            return buildSpider(pos);
        case "door":
            return new Door(pos, jsonEntity.getInt("key"));
        case "key":
            return new Key(pos, jsonEntity.getInt("key"));
        case "light_bulb_off":
            return new LightBulb(pos, constructCondition(jsonEntity.getString("logic")));
        case "wire":
            return new Wire(pos);
        case "switch_door":
            return new SwitchDoor(pos, constructCondition(jsonEntity.getString("logic")));
        case "sun_stone":
            return new SunStone(pos);
        default:
            throw new IllegalArgumentException(
                    String.format("Failed to recognise '%s' entity in EntityFactory", jsonEntity.getString("type")));
        }
    }

    private LogicCondition constructCondition(String logicKey) {
        switch (logicKey) {
        case "and":
            return new AndCondition();
        case "or":
            return new OrCondition();
        case "co_and":
            return new CoAndCondition();
        case "xor":
            return new XorCondition();
        default:
            throw new IllegalArgumentException();
        }
    }
}
