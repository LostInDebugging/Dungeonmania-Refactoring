package dungeonmania.task2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.mvp.TestUtils;

@Tag("Logic")
public class LogicEntitiesTest {
    public DungeonResponse tickMove(DungeonManiaController dmc, Direction dir, int tickCount) {
        DungeonResponse res = null;
        for (int i = 0; i < tickCount; i++) {
            res = dmc.tick(dir);
        }
        return res;
    }

    @Test
    @DisplayName("Test simple conduction and light bulb functionality")
    public void testConductingLightBulb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("testConductingLightBulb", "no_spider_bomb_radius_0");

        assertEquals(2, TestUtils.countEntityOfType(res.getEntities(), "light_bulb_off"));

        res = dmc.tick(Direction.DOWN);

        assertEquals(0, TestUtils.countEntityOfType(res.getEntities(), "light_bulb_off"));
        assertEquals(2, TestUtils.countEntityOfType(res.getEntities(), "light_bulb_on"));
    }

    @Test
    @DisplayName("Test 1: Test Logic Entities adjacent to wire and condition AND")
    public void testLogicConditionAnd() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("logicEntities_And", "no_spider_bomb_radius_0");

        // all logic entities should be off
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "light_bulb_off"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "switch_door"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "bomb"));

        res = dmc.tick(Direction.DOWN);

        // the top switch is on, but all logic entities should still be off
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "light_bulb_off"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "switch_door"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "bomb"));

        res = tickMove(dmc, Direction.LEFT, 4);
        res = tickMove(dmc, Direction.DOWN, 3);
        res = tickMove(dmc, Direction.RIGHT, 1);

        // the switch door should be open now
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "switch_door_open"));
        assertEquals(0, TestUtils.countEntityOfType(res.getEntities(), "switch_door"));

        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "bomb"));

        res = tickMove(dmc, Direction.DOWN, 4);
        res = tickMove(dmc, Direction.RIGHT, 3);
        res = tickMove(dmc, Direction.UP, 1);

        // the bomb should have exploded now
        assertEquals(0, TestUtils.countEntityOfType(res.getEntities(), "bomb"));

        res = tickMove(dmc, Direction.RIGHT, 4);
        res = tickMove(dmc, Direction.UP, 3);
        res = tickMove(dmc, Direction.LEFT, 1);

        // the light bulb should be on now
        assertEquals(0, TestUtils.countEntityOfType(res.getEntities(), "light_bulb_off"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "light_bulb_on"));
    }

    @Test
    @DisplayName("Test 2: Test Logic Entities condition OR")
    public void testLogicConditionOr() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("logicEntities_OR", "no_spider_bomb_radius_0");

        // all logic entities should be off
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "light_bulb_off"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "switch_door"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "bomb"));

        res = dmc.tick(Direction.DOWN);

        // the top switch is on, so both switch door and light bulb become on
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "light_bulb_on"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "switch_door_open"));

        res = tickMove(dmc, Direction.LEFT, 4);
        res = tickMove(dmc, Direction.DOWN, 3);
        res = tickMove(dmc, Direction.RIGHT, 1);

        // the bomb should explode now
        assertEquals(0, TestUtils.countEntityOfType(res.getEntities(), "bomb"));
    }

    @Test
    @DisplayName("Test 3: Test Logic Entities condition XOR with adjacency to switch")
    // also implicitly checks that adjacent switches do not propagate current
    public void testLogicConditionXor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("logicEntities_XOR", "no_spider_bomb_radius_0");

        // all logic entities should be off
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "light_bulb_off"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "switch_door"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "bomb"));

        res = dmc.tick(Direction.UP);

        // the bottom switch is on, but nothing should logically be on as it does not satisfy the xor condition
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "light_bulb_off"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "switch_door"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "bomb"));

        res = dmc.tick(Direction.UP);

        // the bomb should have exploded now and the other logic entities should be on
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "light_bulb_on"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "switch_door_open"));
        assertEquals(0, TestUtils.countEntityOfType(res.getEntities(), "bomb"));
    }

    @Test
    @DisplayName("Test 4: Test Logic Entities condition CO_AND")
    // also implicitly checks that current in wires powered by multiple switches is not turned off
    // if one of the switches powering it turns off
    public void testLogicConditionCoAnd() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("logicEntities_CO_AND", "no_spider_bomb_radius_0");

        // all logic entities should be off
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "light_bulb_off"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "switch_door"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "bomb"));

        res = dmc.tick(Direction.RIGHT);

        // still should be off because of CO_AND
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "light_bulb_off"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "switch_door"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "bomb"));

        res = tickMove(dmc, Direction.UP, 3);
        res = tickMove(dmc, Direction.RIGHT, 7);

        res = tickMove(dmc, Direction.DOWN, 2);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.RIGHT);
        res = tickMove(dmc, Direction.DOWN, 2);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.RIGHT);
        res = tickMove(dmc, Direction.DOWN, 2);
        res = dmc.tick(Direction.LEFT);
        res = tickMove(dmc, Direction.DOWN, 2);
        res = tickMove(dmc, Direction.LEFT, 5);
        res = tickMove(dmc, Direction.UP, 5);

        // still should be off but only the switches on the right are pressed
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "light_bulb_off"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "switch_door"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "bomb"));

        res = dmc.tick(Direction.RIGHT);
        res = tickMove(dmc, Direction.UP, 3);
        res = tickMove(dmc, Direction.RIGHT, 4);

        res = tickMove(dmc, Direction.DOWN, 3);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);

        res = tickMove(dmc, Direction.DOWN, 3);
        res = dmc.tick(Direction.LEFT);

        res = dmc.tick(Direction.UP);
        res = tickMove(dmc, Direction.DOWN, 2);

        res = dmc.tick(Direction.RIGHT);

        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "light_bulb_off"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "switch_door"));
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "bomb"));

        res = tickMove(dmc, Direction.UP, 6);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);

        // the light bulb should now be on, because co_and is satisfied.
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "light_bulb_on"));
    }
}
