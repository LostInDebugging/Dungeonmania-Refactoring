package dungeonmania.task2;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

public class SunstoneAndBuildablesTest {
    @Test
    @Tag("sceptre")
    @DisplayName("Building sceptre with preferred ingredients")
    public void testBuildSceptrePreferredIngredients() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_buildables_sceptre_preferred",
                "c_task2_buildables_sceptre_preferred");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        List<String> buildables = res.getBuildables();
        assertTrue(buildables.contains("sceptre"), "Sceptre should be available in the buildables list.");

        res = assertDoesNotThrow(() -> dmc.build("sceptre"));

        List<String> invTypes = TestUtils.getInventory(res, "sceptre").stream().map(item -> item.getType()).toList();
        assertTrue(invTypes.contains("sceptre"), "Sceptre should be in the player's inventory after building.");

        assertEquals(0, TestUtils.getInventory(res, "wood").size(), "Wood should be consumed when building sceptre.");
        assertEquals(0, TestUtils.getInventory(res, "key").size(), "Key should be consumed when building sceptre.");
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size(),
                "Sun stone should be consumed when building sceptre.");
    }

    @Test
    @Tag("sceptre")
    @DisplayName("Building sceptre with substitute ingredients (arrows and treasure)")
    public void testBuildSceptreSubstituteIngredients() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_buildables_sceptre_substitute",
                "c_task2_buildables_sceptre_substitute");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);

        List<String> buildables = res.getBuildables();
        assertTrue(buildables.contains("sceptre"),
                "Sceptre should be available for building with substitute ingredients.");

        res = assertDoesNotThrow(() -> dmc.build("sceptre"));

        List<String> invItems = TestUtils.getInventory(res, "sceptre").stream().map(item -> item.getType()).toList();
        assertTrue(invItems.contains("sceptre"), "Sceptre should be in the inventory after building.");

        assertEquals(0, TestUtils.getInventory(res, "arrow").size(),
                "Arrows should be consumed when building sceptre with substitute ingredients.");
        assertEquals(0, TestUtils.getInventory(res, "treasure").size(),
                "Treasure should be consumed when used in place of a key.");
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size(),
                "Sun stone should be consumed as required in the recipe.");
    }

    @Test
    @Tag("midnight")
    @DisplayName("Test 3: Building midnight armour when no zombies are present")
    public void testBuildMidnightArmourNoZombies() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_buildables_midnight", "c_task2_buildables_midnight");
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        List<String> buildables = res.getBuildables();
        assertTrue(buildables.contains("midnight_armour"),
                "Midnight armour should be available in the buildables list when there are no zombies in the dungeon.");

        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));

        List<String> invItems = TestUtils.getInventory(res, "midnight_armour").stream().map(item -> item.getType())
                .toList();
        assertTrue(invItems.contains("midnight_armour"),
                "Midnight armour should be in the player's inventory after building.");

        assertEquals(0, TestUtils.getInventory(res, "sword").size(),
                "Sword should be consumed when building midnight armour.");
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size(),
                "Sun stone should be consumed when building midnight armour.");
    }

    @Test
    @Tag("sunstone")
    @DisplayName("Test 4: Door opening with a Sun Stone (retained after use)")
    public void testDoorOpeningWithSunStone() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_door_sunstone", "c_task2_door_sunstone");

        res = dmc.tick(Direction.DOWN);

        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size(), "Sun stone should be present after pickup.");

        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size(),
                "Sun stone should be retained after using it to open a door.");
    }

    @Test
    @Tag("midnight")
    @DisplayName("Additional Test 5: Building midnight armour fails when zombies are present")
    public void testBuildMidnightArmourWithZombiesPresent() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_buildables_midnight_zombies", "c_task2_buildables_midnight_zombies");

        List<String> buildables = res.getBuildables();
        assertFalse(buildables.contains("midnight_armour"),
                "Midnight armour should not be available when zombies are present.");

        assertThrows(Exception.class, () -> dmc.build("midnight_armour"),
                "Building midnight armour when zombies are present should fail.");
    }

    @Test
    @Tag("sceptre")
    @DisplayName("Building sceptre fails when sun stone is missing")
    public void testBuildSceptreMissingSunStone() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_buildables_sceptre_no_sunstone",
                "c_task2_buildables_sceptre_no_sunstone");

        List<String> buildables = res.getBuildables();
        assertFalse(buildables.contains("sceptre"), "Sceptre should not be available when no sun stone is present.");

        assertThrows(Exception.class, () -> dmc.build("sceptre"), "Building sceptre without a sun stone should fail.");
    }

    @Test
    @Tag("midnight")
    @DisplayName("Building midnight armour with extra sun stones")
    public void testMidnightArmourConsumesOneSunStone() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_buildables_midnight_extraSun",
                "c_task2_buildables_midnight_extraSun");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertEquals(1, TestUtils.getInventory(res, "sword").size(), "Player should have 1 sword.");
        assertEquals(2, TestUtils.getInventory(res, "sun_stone").size(), "Player should have 2 sun stones.");

        List<String> buildables = res.getBuildables();
        assertTrue(buildables.contains("midnight_armour"),
                "Midnight armour should be available when no zombies are present.");

        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));

        List<String> invItems = TestUtils.getInventory(res, "midnight_armour").stream().map(item -> item.getType())
                .toList();
        assertTrue(invItems.contains("midnight_armour"),
                "Midnight armour should be in the player's inventory after building.");

        assertEquals(0, TestUtils.getInventory(res, "sword").size(),
                "Sword should be consumed when building midnight armour.");

        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size(),
                "Exactly one sun stone should remain after building midnight armour.");
    }

    @Test
    @Tag("sceptre")
    @DisplayName("Building sceptre fails when no ingredients are present")
    public void testBuildSceptreNoIngredients() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_buildables_sceptre_noIngredients",
                "c_task2_buildables_sceptre_noIngredients");

        List<String> buildables = res.getBuildables();
        assertFalse(buildables.contains("sceptre"),
                "Sceptre should not be available in the buildables list when no ingredients are present.");

        assertThrows(Exception.class, () -> dmc.build("sceptre"),
                "Building sceptre with no ingredients should throw an exception.");
    }

    @Test
    @Tag("mindcontrol")
    @DisplayName("Mind control using sceptre on a mercenary")
    public void testMindControlUsingSceptreOnMercenary() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_mindcontrol", "c_task2_mindcontrol");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertTrue(TestUtils.getBuildables(res).contains("sceptre"),
                "Sceptre should be available in the buildables list when proper ingredients are present.");

        res = assertDoesNotThrow(() -> dmc.build("sceptre"),
                "Building sceptre should succeed with proper ingredients.");

        res = dmc.tick(Direction.DOWN);

        List<EntityResponse> mercs = TestUtils.getEntities(res, "mercenary");

        assertFalse(mercs.isEmpty(), "No mercenaries found in the dungeon!");
        String mercId = mercs.get(0).getId();

        res = assertDoesNotThrow(() -> dmc.interact(mercId),
                "Interacting with the mercenary using sceptre should succeed.");

        assertThrows(Exception.class, () -> dmc.interact(mercId),
                "Mercenary should not be interactable after being mind controlled.");
    }

    @Test
    @Tag("mindcontrol")
    @DisplayName("Mind control using sceptre (with substitute ingredients) on a mercenary")
    public void testMindControlUsingSceptreSubstituteOnMercenary() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_mindcontrol_substitute", "c_task2_mindcontrol_substitute");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        List<String> buildables = TestUtils.getBuildables(res);
        assertTrue(buildables.contains("sceptre"),
                "Sceptre should be available in the buildables list when proper ingredients are present.");

        res = assertDoesNotThrow(() -> dmc.build("sceptre"),
                "Building sceptre should succeed with proper (substitute) ingredients.");

        res = dmc.tick(Direction.DOWN);

        long mercCount = TestUtils.countType(res, "mercenary");
        assertEquals(1, mercCount, "There should be exactly 1 mercenary in the dungeon!");

        List<EntityResponse> mercList = TestUtils.getEntities(res, "mercenary");
        assertTrue(mercList.size() > 0, "There should be at least one mercenary in the dungeon!");
        String mercId = mercList.get(0).getId();

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        res = assertDoesNotThrow(() -> dmc.interact(mercId),
                "Interacting with the mercenary using sceptre should succeed.");

        boolean allNonInteractable = TestUtils.getEntities(res, "mercenary").stream()
                .allMatch(entityResponse -> !entityResponse.isInteractable());
        assertTrue(allNonInteractable, "Mercenary should not be interactable after being mind controlled.");

        assertEquals(0, TestUtils.getInventory(res, "treasure").size(),
                "Treasure should be consumed when used to build sceptre as a substitute for a key.");
    }

    @Test
    @Tag("mindcontrol")
    @DisplayName("Mind control using sceptre on a mercenary and effect expires after duration")
    public void testMindControlUsingSceptreOnMercenaryWithExpiry() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_mindcontrol", "c_task2_mindcontrol");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        List<String> buildables = TestUtils.getBuildables(res);
        assertTrue(buildables.contains("sceptre"),
                "Sceptre should be available in the buildables list when proper ingredients are present.");

        res = assertDoesNotThrow(() -> dmc.build("sceptre"),
                "Building sceptre should succeed with proper ingredients.");

        res = dmc.tick(Direction.DOWN);

        long mercCount = TestUtils.countType(res, "mercenary");
        assertEquals(1, mercCount, "There should be exactly 1 mercenary in the dungeon!");

        List<EntityResponse> mercList = TestUtils.getEntities(res, "mercenary");
        assertTrue(mercList.size() > 0, "There should be at least one mercenary in the dungeon!");
        String mercId = mercList.get(0).getId();

        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        res = assertDoesNotThrow(() -> dmc.interact(mercId),
                "Interacting with the mercenary using sceptre should succeed.");

        assertThrows(Exception.class, () -> dmc.interact(mercId),
                "Mercenary should not be interactable while under mind control.");

        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        boolean anyInteractable = TestUtils.getEntities(res, "mercenary").stream()
                .anyMatch(entity -> entity.isInteractable());
        assertTrue(anyInteractable, "Mercenary should become interactable after mind control expires.");
    }

    @Test
    @Tag("sceptre")
    @DisplayName("Building sceptre prioritizes key over treasure when both are present")
    public void testSceptrePrioritizesKey() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_buildables_sceptre_keyTreasure",
                "c_task2_buildables_sceptre_keyTreasure");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        List<String> buildables = TestUtils.getBuildables(res);
        assertTrue(buildables.contains("sceptre"),
                "Sceptre should be available in the buildables list when proper ingredients are present.");

        int initialTreasureCount = TestUtils.getInventory(res, "treasure").size();
        int initialKeyCount = TestUtils.getInventory(res, "key").size();
        assertTrue(initialKeyCount > 0, "There should be at least one key available.");

        res = assertDoesNotThrow(() -> dmc.build("sceptre"),
                "Building sceptre should succeed with proper ingredients.");

        List<String> invTypes = TestUtils.getInventory(res, "sceptre").stream().map(item -> item.getType()).toList();
        assertTrue(invTypes.contains("sceptre"), "Sceptre should be in the player's inventory after building.");

        assertEquals(0, TestUtils.getInventory(res, "key").size(),
                "Key should be consumed when building sceptre if both key and treasure are present.");

        assertEquals(initialTreasureCount, TestUtils.getInventory(res, "treasure").size(),
                "Treasure should not be consumed when a key is available for sceptre crafting.");
    }

}
