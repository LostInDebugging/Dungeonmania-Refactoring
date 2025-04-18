package dungeonmania.task2;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.mvp.TestUtils;

public class EnemyGoalsTest {
    @Test
    @Tag("microevolution")
    @DisplayName("Test 1: Enemy goal not achieved when no enemies are defeated")
    public void testEnemyGoalNoDefeat() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_enemyGoal_target3", "c_task2_enemyGoal_target3");

        assertTrue(TestUtils.getGoals(res).contains(":enemies"),
                "Enemy goal should be incomplete when no enemy is defeated.");
    }

    @Test
    @Tag("microevolution")
    @DisplayName("Test 2: Enemy goal remains unachieved when only one enemy is defeated and a spawner remains")
    public void testEnemyGoalPartialKillSpawnerRemaining() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_enemyGoal_partial", "c_task2_enemyGoal_partial");

        assertTrue(TestUtils.getGoals(res).contains(":enemies"),
                "Initially, the enemy goal should be incomplete (':enemies' should be present).");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertTrue(TestUtils.getGoals(res).contains(":enemies"),
                "Enemy goal should remain unachieved if only 1 enemy is defeated (target is 3) and a spawner remains.");
    }

    @Test
    @Tag("microevolution")
    @DisplayName("Test 3: Enemy goal remains unachieved when only one enemy is defeated when target = 3")
    public void testEnemyGoalPartialDefeatManual() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_enemyGoal_target3", "c_task2_enemyGoal_target3");

        assertTrue(TestUtils.getGoals(res).contains(":enemies"),
                "Enemy goal should initially be incomplete (':enemies' expected).");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertTrue(TestUtils.getGoals(res).contains(":enemies"),
                "Enemy goal should remain incomplete when only one enemy is defeated (target is 3).");
    }

    @Test
    @Tag("microevolution")
    @DisplayName("Test 4: Defeat mercenary then destroy zombie toast spawner and achieve enemy goal")
    public void testEnemyGoalSequenceWithSwordAndInteractor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_task2_enemyGoal_oneEnemyOneSpawner",
                "c_task2_enemyGoal_oneEnemyOneSpawner");

        assertTrue(TestUtils.getGoals(res).contains(":enemies"),
                "Initially, the enemy goal should be incomplete (':enemies' should be present).");

        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "sword").size(),
                "Player should have picked up a sword after moving DOWN.");
        res = dmc.tick(Direction.RIGHT);

        long mercCount = TestUtils.countType(res, "mercenary");
        assertEquals(0, mercCount, "There should be no mercenaries left after the battle.");

        res = dmc.tick(Direction.UP);

        String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

        assertThrows(IllegalArgumentException.class, () -> dmc.interact("invalid_id"),
                "Interacting with an invalid ID should throw an IllegalArgumentException.");

        res = assertDoesNotThrow(() -> dmc.interact(spawnerId));
        assertEquals(0, TestUtils.countType(res, "zombie_toast_spawner"),
                "The zombie toast spawner should be destroyed after successful interaction.");

        res = dmc.tick(Direction.UP);
        assertEquals("", TestUtils.getGoals(res),
                "Enemy goal should be achieved after defeating the enemy and destroying the spawner.");
    }

    @Test
    @Tag("microevolution")
    @DisplayName("Additional Test 5: Enemy goal achieved when 3 enemies are defeated")
    public void testEnemyGoalAchievedAfterThreeKills() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initialRes = dmc.newGame("d_task2_enemyGoal_threeKills", "c_task2_enemyGoal_threeKills");

        assertTrue(TestUtils.getGoals(initialRes).contains(":enemies"),
                "Enemy goal should initially be incomplete (':enemies' expected).");

        DungeonResponse res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"),
                "After defeating 1 enemy, enemy goal should still be incomplete (target is 3).");

        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"),
                "After defeating 2 enemies, enemy goal should remain incomplete (target is 3).");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);

        int defeated = TestUtils.getDefeatedEnemyCount(initialRes, res, "mercenary");

        assertEquals(3, defeated, "Defeated enemy counter should be 3 after defeating 3 enemies.");
        assertEquals("", TestUtils.getGoals(res),
                "Enemy goal should be achieved after defeating 3 enemies (goal string should be empty).");
    }
}
