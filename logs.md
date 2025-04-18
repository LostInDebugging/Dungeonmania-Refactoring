[TOC]
#### Test-COMPILATION "Does the project compile?":
Yes
#### Test-LINTING "Is your project lint valid?": PASS
Yes
#### Test Task-2a-1 "Enemy Goal: Test achieving a basic enemy goal kill spider": PASS
```
dungeonmania.automarking.EnemyGoalTest

  Test Test achieving a basic enemy goal kill spider PASSED

SUCCESS: Executed 1 tests in 513ms

SUCCESS
```
#### Test Task-2a-2 "Enemy Goal: Test achieving a basic enemy goal kill zombie toast": PASS
```
dungeonmania.automarking.EnemyGoalTest

  Test Test achieving a basic enemy goal kill zombie toast PASSED

SUCCESS: Executed 1 tests in 561ms

SUCCESS
```
#### Test Task-2a-3 "Enemy Goal: Test achieving a basic enemy goal kill mercenary": PASS
```
dungeonmania.automarking.EnemyGoalTest

  Test Test achieving a basic enemy goal kill mercenary PASSED

SUCCESS: Executed 1 tests in 501ms

SUCCESS
```
#### Test Task-2a-4 "Enemy Goal: Test achieving a basic enemy goal kill multiple enemies": PASS
```
dungeonmania.automarking.EnemyGoalTest

  Test Test achieving a basic enemy goal kill multiple enemies PASSED

SUCCESS: Executed 1 tests in 477ms

SUCCESS
```
#### Test Task-2a-5 "Enemy Goal: Testing a map with 4 conjunction goal": PASS
```
dungeonmania.automarking.EnemyGoalTest

  Test Testing a map with 4 conjunction goal PASSED

SUCCESS: Executed 1 tests in 545ms

SUCCESS
```
#### Test Task-2a-6 "Enemy Goal: Testing a map with 4 disjunction goal": PASS
```
dungeonmania.automarking.EnemyGoalTest

  Test Testing a map with 4 disjunction goal PASSED

SUCCESS: Executed 1 tests in 562ms

SUCCESS
```
#### Test Task-2a-7 "Enemy Goal: Testing a map with mixed conjunction/disjunction goal": PASS
```
dungeonmania.automarking.EnemyGoalTest

  Test Testing a map with mixed conjunction/disjunction goal PASSED

SUCCESS: Executed 1 tests in 495ms

SUCCESS
```
#### Test Task-2a-8 "Enemy Goal: Testing achieving a basic enemy goal by destroying a spawner": PASS
```
dungeonmania.automarking.EnemyGoalTest

  Test Testing achieving a basic enemy goal by destroying a spawner PASSED

SUCCESS: Executed 1 tests in 525ms

SUCCESS
```
#### Test Task-2a-9 "Enemy Goal: Testing achieving an enemy goal by destroying multiple spawners": PASS
```
dungeonmania.automarking.EnemyGoalTest

  Test Testing achieving an enemy goal by destroying multiple spawners PASSED

SUCCESS: Executed 1 tests in 526ms

SUCCESS
```
#### Test Task-2a-10 "Enemy Goal: Testing achieving an enemy goal by both killing enemies and destroying spawners": PASS
```
dungeonmania.automarking.EnemyGoalTest

  Test Testing achieving an enemy goal by both killing enemies and destroying spawners PASSED

SUCCESS: Executed 1 tests in 539ms

SUCCESS
```
#### Test-Task-2b-1 "Boss: Testing an assassin in line with Player moves towards them":FAIL
```
dungeonmania.automarking.BossTest

  Test Testing an assassin in line with Player moves towards them FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'assassin' entity in GraphNodeFactory
      at dungeonmania.automarking.BossTest.assassinMovement(BossTest.java:24)


FAILURE: Executed 1 tests in 507ms (1 failed)

FAILURE
```
#### Test-Task-2b-2 "Boss: Test assassins can not move through walls and closed doors":FAIL
```
dungeonmania.automarking.BossTest

  Test Test assassins can not move through walls and closed doors FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'assassin' entity in GraphNodeFactory
      at dungeonmania.automarking.BossTest.assassinDoorsAndWalls(BossTest.java:43)


FAILURE: Executed 1 tests in 580ms (1 failed)

FAILURE
```
#### Test-Task-2b-3 "Boss: Testing bribing an assassin with 0 fail rate":FAIL
```
dungeonmania.automarking.BossTest

  Test Testing bribing an assassin with 0 fail rate FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'assassin' entity in GraphNodeFactory
      at dungeonmania.automarking.BossTest.testAssassinBribeSuccess(BossTest.java:62)


FAILURE: Executed 1 tests in 546ms (1 failed)

FAILURE
```
#### Test-Task-2b-4 "Boss: Testing bribed assassin should act as an ally":FAIL
```
dungeonmania.automarking.BossTest

  Test Testing bribed assassin should act as an ally FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'assassin' entity in GraphNodeFactory
      at dungeonmania.automarking.BossTest.testAssassinAllied(BossTest.java:98)


FAILURE: Executed 1 tests in 544ms (1 failed)

FAILURE
```
#### Test-Task-2b-5 "Boss: Testing bribing an assassin with 100% fail rate and no refund":FAIL
```
dungeonmania.automarking.BossTest

  Test Testing bribing an assassin with 100% fail rate, and no refund FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'assassin' entity in GraphNodeFactory
      at dungeonmania.automarking.BossTest.testAssassinBribeFail(BossTest.java:140)


FAILURE: Executed 1 tests in 529ms (1 failed)

FAILURE
```
#### Test-Task-2b-6 "Boss: Test assassin moves towards the player using portals":FAIL
```
dungeonmania.automarking.BossTest

  Test Test assassin moves towards the player using portals FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'assassin' entity in GraphNodeFactory
      at dungeonmania.automarking.BossTest.assassinWithPortals(BossTest.java:180)


FAILURE: Executed 1 tests in 526ms (1 failed)

FAILURE
```
#### Test-Task-2b-7 "Boss: Testing random bribing result should hopefully be different at least once in 30 runs":FAIL
```
dungeonmania.automarking.BossTest

  Test Testing random bribing result should hopefully be different at least once in 30 runs FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'assassin' entity in GraphNodeFactory
      at dungeonmania.automarking.BossTest.testAssassinRandomBribing(BossTest.java:215)


FAILURE: Executed 1 tests in 540ms (1 failed)

FAILURE
```
#### Test-Task-2b-8 "Boss: Testing basic movement for hydra":FAIL
```
dungeonmania.automarking.BossTest

  Test Testing basic movement for hydra FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'hydra' entity in GraphNodeFactory
      at dungeonmania.automarking.BossTest.hydraMovement(BossTest.java:254)


FAILURE: Executed 1 tests in 521ms (1 failed)

FAILURE
```
#### Test-Task-2b-9 "Boss: Testing hydras cannot move through closed doors and walls":FAIL
```
dungeonmania.automarking.BossTest

  Test Testing hydras cannot move through closed doors and walls FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'hydra' entity in GraphNodeFactory
      at dungeonmania.automarking.BossTest.hydraDoorsAndWalls(BossTest.java:281)


FAILURE: Executed 1 tests in 518ms (1 failed)

FAILURE
```
#### Test-Task-2b-10 "Boss: Test player wins a battle against hydra when hydra does not increase health when taking damage":FAIL
```
dungeonmania.automarking.BossTest

  Test Test player wins a battle against hydra when hydra does not increase health when taking damage FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'hydra' entity in GraphNodeFactory
      at dungeonmania.automarking.BossTest.testPlayerWinsHydraBattle(BossTest.java:297)


FAILURE: Executed 1 tests in 562ms (1 failed)

FAILURE
```
#### Test-Task-2b-11 "Boss: Test the hydra must increase health when taking damage - player loses":FAIL
```
dungeonmania.automarking.BossTest

  Test Test the hydra must increase health when taking damage - player loses FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'hydra' entity in GraphNodeFactory
      at dungeonmania.automarking.BossTest.testHydraIncreasesHealth(BossTest.java:326)


FAILURE: Executed 1 tests in 555ms (1 failed)

FAILURE
```
#### Test-Task-2b-12 "Boss: Test health calculation when the hydra increases its health":FAIL
```
dungeonmania.automarking.BossTest

  Test Test health calculation when the hydra increases its health FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'hydra' entity in GraphNodeFactory
      at dungeonmania.automarking.BossTest.testHydraIncreasesHealthCalculation(BossTest.java:352)


FAILURE: Executed 1 tests in 529ms (1 failed)

FAILURE
```
#### Test-Task-2b-13 "Boss: Test random healing result should hopefully be different at least once in 100 rounds of battle":FAIL
```
dungeonmania.automarking.BossTest

  Test Test random healing result should hopefully be different at least once in 100 rounds of battle FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'hydra' entity in GraphNodeFactory
      at dungeonmania.automarking.BossTest.testHydraIncreasesHealthRandom(BossTest.java:393)


FAILURE: Executed 1 tests in 573ms (1 failed)

FAILURE
```
#### Test-Task-2c-1 "Swamp Tile: Testing mercenary gets stuck in swamp":FAIL
```
dungeonmania.automarking.SwampTileTest

  Test Testing mercenary gets stuck in swamp FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'swamp_tile' entity in GraphNodeFactory
      at dungeonmania.automarking.SwampTileTest.slowMercenary(SwampTileTest.java:26)


FAILURE: Executed 1 tests in 543ms (1 failed)

FAILURE
```
#### Test-Task-2c-2 "Swamp Tile: Testing if mercenaries take into account movement factor when pathfinding":FAIL
```
dungeonmania.automarking.SwampTileTest

  Test Testing if mercenaries take into account movement factor when pathfinding FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'swamp_tile' entity in GraphNodeFactory
      at dungeonmania.automarking.SwampTileTest.mercPathfindWithSwampTile(SwampTileTest.java:50)


FAILURE: Executed 1 tests in 558ms (1 failed)

FAILURE
```
#### Test-Task-2c-3 "Swamp Tile: Testing player does not get slowed by swamp tile":FAIL
```
dungeonmania.automarking.SwampTileTest

  Test Testing player does not get slowed by swamp tile FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'swamp_tile' entity in GraphNodeFactory
      at dungeonmania.automarking.SwampTileTest.testPlayerNotSlowedBySwamp(SwampTileTest.java:78)


FAILURE: Executed 1 tests in 559ms (1 failed)

FAILURE
```
#### Test-Task-2c-4 "Swamp Tile: Testing spider is slowed by swamp tile and that movement is still circling":FAIL
```
dungeonmania.automarking.SwampTileTest

  Test Testing spider is slowed by swamp tile and that movement is still circling FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'swamp_tile' entity in GraphNodeFactory
      at dungeonmania.automarking.SwampTileTest.testSpiderSlowedBySwamp(SwampTileTest.java:95)


FAILURE: Executed 1 tests in 544ms (1 failed)

FAILURE
```
#### Test-Task-2c-5 "Swamp Tile: Testing multiple swamp tiles all slow":FAIL
```
dungeonmania.automarking.SwampTileTest

  Test Testing multiple swamp tiles all slow FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'swamp_tile' entity in GraphNodeFactory
      at dungeonmania.automarking.SwampTileTest.testMultiSwamp(SwampTileTest.java:115)


FAILURE: Executed 1 tests in 570ms (1 failed)

FAILURE
```
#### Test-Task-2c-6 "Swamp Tile: Testing a battle on a swamp tile is successful - mercenary dies":FAIL
```
dungeonmania.automarking.SwampTileTest

  Test Testing a battle on a swamp tile is successful - mercenary dies FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'swamp_tile' entity in GraphNodeFactory
      at dungeonmania.automarking.SwampTileTest.testBattleOnSwamp(SwampTileTest.java:143)


FAILURE: Executed 1 tests in 533ms (1 failed)

FAILURE
```
#### Test-Task-2c-7 "Swamp Tile: Test mercenary moves towards the player using swamp tiles":FAIL
```
dungeonmania.automarking.SwampTileTest

  Test Test mercenary moves towards the player using swamp tiles FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'swamp_tile' entity in GraphNodeFactory
      at dungeonmania.automarking.SwampTileTest.mercWithSwampTiles(SwampTileTest.java:169)


FAILURE: Executed 1 tests in 541ms (1 failed)

FAILURE
```
#### Test Task-2d-1 "Sunstone + Buildables: Testing picking up a sun stone from the map adds to inventory": PASS
```
dungeonmania.automarking.BuildablesM3Test

  Test (arrows x2) + (key x1) + (sun stone x1) => (sceptre x1) PASSED

SUCCESS: Executed 1 tests in 536ms

SUCCESS
```
#### Test Task-2d-2 "Sunstone + Buildables: Testing using a sun stone to open two doors and the sun stone is retained": PASS
```
dungeonmania.automarking.BuildablesM3Test

  Test (wood x1) + (key x1) => (sceptre x0) + InvalidActionException PASSED

SUCCESS: Executed 1 tests in 532ms

SUCCESS
```
#### Test Task-2d-3 "Sunstone + Buildables: Testing sun stone can be used interchangeably with treasure to build shield and retained after building": PASS
```
dungeonmania.automarking.BuildablesM3Test

  Test (sword x1) + (sun stone x1) + (zombie x0) => (midnight armour x1) PASSED

SUCCESS: Executed 1 tests in 535ms

SUCCESS
```
#### Test-Task-2d-4 "Sunstone + Buildables: (wood x1) + (sunstone x2) => (sceptre x1) + (retained sunstone x1)":FAIL
```
dungeonmania.automarking.BuildablesM3Test

  Test (sword x1) + (sun stone x1) + (zombie x1) => (midnight armour x0) + InvalidActionException FAILED

  org.opentest4j.AssertionFailedError: Expected dungeonmania.exceptions.InvalidActionException to be thrown, but nothing was thrown.
      at app//dungeonmania.automarking.BuildablesM3Test.testBuildMidnightArmourWithZombies(BuildablesM3Test.java:173)


FAILURE: Executed 1 tests in 577ms (1 failed)

FAILURE
```
#### Test Task-2d-5 "Sunstone + Buildables: Test sunstone counts towards the treasure goal": PASS
```
dungeonmania.automarking.BuildablesM3Test

  Test (sun stone x1) + (zombie x0) => (midnight armour x0) + InvalidActionException PASSED

SUCCESS: Executed 1 tests in 529ms

SUCCESS
```
#### Test Task-2d-6 "Sunstone + Buildables: Sun stone cannot be used to bribe mercenaries": PASS
```
dungeonmania.automarking.BuildablesM3Test

  Test Using sceptre to mind control a mercenary PASSED

SUCCESS: Executed 1 tests in 535ms

SUCCESS
```
#### Test Task-2d-7 "Sunstone + Buildables: (arrows x2) + (key x1) + (sun stone x1) => (sceptre x1)": PASS
```
dungeonmania.automarking.BuildablesM3Test

  Test Test for mind control duration PASSED

SUCCESS: Executed 1 tests in 554ms

SUCCESS
```
#### Test-Task-2d-8 "Sunstone + Buildables: (wood x1) + (key x1) => (sceptre x0) + InvalidActionException":FAIL
```
dungeonmania.automarking.BuildablesM3Test

  Test Use midnight armour to attack - player battles for less rounds to win FAILED

  org.opentest4j.AssertionFailedError: expected: <4> but was: <7>
      at app//dungeonmania.automarking.BuildablesM3Test.testMidnightArmourAttack(BuildablesM3Test.java:370)


FAILURE: Executed 1 tests in 542ms (1 failed)

FAILURE
```
#### Test-Task-2d-9 "Sunstone + Buildables: (sword x1) + (sun stone x1) + (zombie x0) => (midnight armour x1)":FAIL
```
dungeonmania.automarking.BuildablesM3Test

  Test Use midnight armour to defence - player battles for more rounds but still dies FAILED

  org.opentest4j.AssertionFailedError: expected: <5> but was: <4>
      at app//dungeonmania.automarking.BuildablesM3Test.testMidnightArmourDefence(BuildablesM3Test.java:420)


FAILURE: Executed 1 tests in 597ms (1 failed)

FAILURE
```
#### Test Task-2d-10 "Sunstone + Buildables: (sword x1) + (sun stone x1) + (zombie x1) => (midnight armour x0) + InvalidActionException": PASS
```
dungeonmania.automarking.SunStoneTest

  Test Testing picking up a sun stone from the map adds to inventory PASSED

SUCCESS: Executed 1 tests in 509ms

SUCCESS
```
#### Test Task-2d-11 "Sunstone + Buildables: (sun stone x1) + (zombie x0) => (midnight armour x0) + InvalidActionException": PASS
```
dungeonmania.automarking.SunStoneTest

  Test Testing using a sun stone to open two doors and the sun stone is retained PASSED

SUCCESS: Executed 1 tests in 540ms

SUCCESS
```
#### Test-Task-2d-12 "Sunstone + Buildables: Using sceptre to mind control a mercenary":FAIL
```
dungeonmania.automarking.SunStoneTest

  Test Testing sun stone can be used interchangeably withtreasure to build shield, and retained after building FAILED

  org.opentest4j.AssertionFailedError: Unexpected exception thrown: dungeonmania.exceptions.InvalidActionException: shield cannot be built
      at app//dungeonmania.automarking.SunStoneTest.useSunStoneAsTreasure(SunStoneTest.java:88)
  Caused by: dungeonmania.exceptions.InvalidActionException: shield cannot be built
      at app//dungeonmania.automarking.SunStoneTest.lambda$useSunStoneAsTreasure$0(SunStoneTest.java:88)


FAILURE: Executed 1 tests in 548ms (1 failed)

FAILURE
```
#### Test-Task-2d-13 "Sunstone + Buildables: Test for mind control duration":FAIL
```
dungeonmania.automarking.SunStoneTest

  Test (wood x1) + (sunstone x2) => (sceptre x1) + (retained sunstone x1) FAILED

  org.opentest4j.AssertionFailedError: Unexpected exception thrown: dungeonmania.exceptions.InvalidActionException: sceptre cannot be built
      at app//dungeonmania.automarking.SunStoneTest.testBuildSceptreTreasureSunstoneInterchangeable(SunStoneTest.java:120)
  Caused by: dungeonmania.exceptions.InvalidActionException: sceptre cannot be built
      at app//dungeonmania.automarking.SunStoneTest.lambda$testBuildSceptreTreasureSunstoneInterchangeable$1(SunStoneTest.java:120)


FAILURE: Executed 1 tests in 544ms (1 failed)

FAILURE
```
#### Test-Task-2d-14 "Sunstone + Buildables: Use midnight armour to attack - player battles for less rounds to win":FAIL
```
dungeonmania.automarking.SunStoneTest

  Test Test sunstone counts towards the treasure goal FAILED

  org.opentest4j.AssertionFailedError: expected: <> but was: <:treasure>
      at app//dungeonmania.automarking.SunStoneTest.testSunStoneCountsTreasureGoal(SunStoneTest.java:152)


FAILURE: Executed 1 tests in 519ms (1 failed)

FAILURE
```
#### Test Task-2d-15 "Sunstone + Buildables: Use midnight armour to defence - player battles for more rounds but still dies": PASS
```
dungeonmania.automarking.SunStoneTest

  Test Sun stone cannot be used to bribe mercenaries PASSED

SUCCESS: Executed 1 tests in 511ms

SUCCESS
```
#### Test-Task-2e-1 "Snake: Testing a snake takes the shortest path to food":FAIL
```
dungeonmania.automarking.SnakeTest

  Test Testing a snake takes the shortest path to food FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'snake_head' entity in GraphNodeFactory
      at dungeonmania.automarking.SnakeTest.simplePathfinding(SnakeTest.java:27)


FAILURE: Executed 1 tests in 510ms (1 failed)

FAILURE
```
#### Test-Task-2e-2 "Snake: Testing a snake is blocked by other snakes":FAIL
```
dungeonmania.automarking.SnakeTest

  Test Testing a snake is blocked by other snakes FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'snake_head' entity in GraphNodeFactory
      at dungeonmania.automarking.SnakeTest.pathfindingBlockedOtherSnakes(SnakeTest.java:51)


FAILURE: Executed 1 tests in 467ms (1 failed)

FAILURE
```
#### Test-Task-2e-3 "Snake: Testing a snake grows after consuming food":FAIL
```
dungeonmania.automarking.SnakeTest

  Test Testing a snake grows after consuming food FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'snake_head' entity in GraphNodeFactory
      at dungeonmania.automarking.SnakeTest.growthAfterEating(SnakeTest.java:71)


FAILURE: Executed 1 tests in 451ms (1 failed)

FAILURE
```
#### Test-Task-2e-4 "Snake: Testing a snake won't run into itself":FAIL
```
dungeonmania.automarking.SnakeTest

  Test Testing a snake won't run into itself FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'snake_head' entity in GraphNodeFactory
      at dungeonmania.automarking.SnakeTest.noSelfCollision(SnakeTest.java:93)


FAILURE: Executed 1 tests in 1.4s (1 failed)

FAILURE
```
#### Test-Task-2e-5 "Snake: Testing body moves correctly after snake grows":FAIL
```
dungeonmania.automarking.SnakeTest

  Test Testing body moves correctly after snake grows FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'snake_head' entity in GraphNodeFactory
      at dungeonmania.automarking.SnakeTest.multipleGrowthMovement(SnakeTest.java:117)


FAILURE: Executed 1 tests in 1.1s (1 failed)

FAILURE
```
#### Test-Task-2e-6 "Snake: Testing a snake gets buffed after consuming 1 key; 1 treasure; 1 arrow in that order":FAIL
```
dungeonmania.automarking.SnakeTest

  Test Testing a snake gets buffed after consuming 1 key, 1 treasure, 1 arrow in that order FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'snake_head' entity in GraphNodeFactory
      at dungeonmania.automarking.SnakeTest.simpleBuffs(SnakeTest.java:151)


FAILURE: Executed 1 tests in 825ms (1 failed)

FAILURE
```
#### Test-Task-2e-7 "Snake: Testing snake head is battled and whole snake dies":FAIL
```
dungeonmania.automarking.SnakeTest

  Test Testing snake head is battled and whole snake dies FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'snake_head' entity in GraphNodeFactory
      at dungeonmania.automarking.SnakeTest.killHeadDies(SnakeTest.java:185)


FAILURE: Executed 1 tests in 484ms (1 failed)

FAILURE
```
#### Test-Task-2e-8 "Snake: Testing a snake body is buffed; battled/killed; and snake continues":FAIL
```
dungeonmania.automarking.SnakeTest

  Test Testing a snake body is buffed, battled/killed, and snake continues FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'snake_head' entity in GraphNodeFactory
      at dungeonmania.automarking.SnakeTest.battleBodyContinues(SnakeTest.java:215)


FAILURE: Executed 1 tests in 514ms (1 failed)

FAILURE
```
#### Test-Task-2e-9 "Snake: Testing snake can go through wall while invisible":FAIL
```
dungeonmania.automarking.SnakeTest

  Test Testing snake can go through wall while invisible FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'snake_head' entity in GraphNodeFactory
      at dungeonmania.automarking.SnakeTest.invisibilityMovement(SnakeTest.java:262)


FAILURE: Executed 1 tests in 1s (1 failed)

FAILURE
```
#### Test-Task-2e-10 "Snake: Testing snake splits into a new snake when invincible":FAIL
```
dungeonmania.automarking.SnakeTest

  Test Testing snake splits into a new snake when invincible FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'snake_head' entity in GraphNodeFactory
      at dungeonmania.automarking.SnakeTest.simpleInvincibility(SnakeTest.java:282)


FAILURE: Executed 1 tests in 429ms (1 failed)

FAILURE
```
#### Test-Task-2e-11 "Snake: Testing new invincible snake acts independently from the original":FAIL
```
dungeonmania.automarking.SnakeTest

  Test Testing new invincible snake acts independently from the original FAILED

  java.lang.IllegalArgumentException: Failed to recognise 'snake_head' entity in GraphNodeFactory
      at dungeonmania.automarking.SnakeTest.complexInvincibility(SnakeTest.java:314)


FAILURE: Executed 1 tests in 484ms (1 failed)

FAILURE
```
#### Test Task-2f-1 "Logic Switches: Test walking onto a wire": PASS
```
dungeonmania.automarking.LogicGatesTest

  Test Test walking onto a wire PASSED

SUCCESS: Executed 1 tests in 510ms

SUCCESS
```
#### Test Task-2f-2 "Logic Switches: Test turning on a light bulb": PASS
```
dungeonmania.automarking.LogicGatesTest

  Test Test turning on a light bulb PASSED

SUCCESS: Executed 1 tests in 424ms

SUCCESS
```
#### Test Task-2f-3 "Logic Switches: Test activating a wire and exploding a bomb": PASS
```
dungeonmania.automarking.LogicGatesTest

  Test Test activating a wire and exploding a bomb PASSED

SUCCESS: Executed 1 tests in 602ms

SUCCESS
```
#### Test Task-2f-4 "Logic Switches: Test complex logic switches with AND light bulb": PASS
```
dungeonmania.automarking.LogicGatesTest

  Test Test complex logic switches with AND light bulb PASSED

SUCCESS: Executed 1 tests in 1.1s

SUCCESS
```
#### Test Task-2f-5 "Logic Switches: Test complex logic switches with AND light bulb and three switches": PASS
```
dungeonmania.automarking.LogicGatesTest

  Test Test complex logic switches with AND light bulb and three switches PASSED

SUCCESS: Executed 1 tests in 683ms

SUCCESS
```
#### Test Task-2f-6 "Logic Switches: Test complex logic switches with XOR light bulb and three switches": PASS
```
dungeonmania.automarking.LogicGatesTest

  Test Test complex logic switches with XOR light bulb and three switches PASSED

SUCCESS: Executed 1 tests in 524ms

SUCCESS
```
#### Test Task-2f-7 "Logic Switches: Test complex logic switches with XOR light bulb": PASS
```
dungeonmania.automarking.LogicGatesTest

  Test Test complex logic switches with XOR light bulb PASSED

SUCCESS: Executed 1 tests in 503ms

SUCCESS
```
#### Test Task-2f-8 "Logic Switches: Test complex logic switches with CO_AND light bulb": PASS
```
dungeonmania.automarking.LogicGatesTest

  Test Test complex logic switches with CO_AND light bulb PASSED

SUCCESS: Executed 1 tests in 539ms

SUCCESS
```
#### Test Task-2f-9 "Logic Switches: Test complex logic switches failure with CO_AND light bulb": PASS
```
dungeonmania.automarking.LogicGatesTest

  Test Test complex logic switches failure with CO_AND light bulb PASSED

SUCCESS: Executed 1 tests in 516ms

SUCCESS
```
#### Test Task-2f-10 "Logic Switches: Test opening a switch door": PASS
```
dungeonmania.automarking.LogicGatesTest

  Test Test opening a switch door PASSED

SUCCESS: Executed 1 tests in 519ms

SUCCESS
```
#### Test Task-2f-11 "Logic Switches: Test complex logic switches with AND switch door": PASS
```
dungeonmania.automarking.LogicGatesTest

  Test Test complex logic switches with AND switch door PASSED

SUCCESS: Executed 1 tests in 508ms

SUCCESS
```
#### Test Task-2f-12 "Logic Switches: Test complex logic switches with AND switch door and three switches": PASS
```
dungeonmania.automarking.LogicGatesTest

  Test Test complex logic switches with AND switch door and three switches PASSED

SUCCESS: Executed 1 tests in 444ms

SUCCESS
```
#### Test-Task-2f-13 "Logic Switches: Test complex logic switches with XOR switch door and three switches":FAIL
```
dungeonmania.automarking.LogicGatesTest

  Test Test complex logic switches with XOR switch door and three switches FAILED

  org.opentest4j.AssertionFailedError: expected: <Position [x=4, y=0, z=0]> but was: <Position [x=4, y=1, z=0]>
      at app//dungeonmania.automarking.LogicGatesTest.switchDoorXORThreeSwitches(LogicGatesTest.java:466)


FAILURE: Executed 1 tests in 576ms (1 failed)

FAILURE
```
#### Test Task-2f-14 "Logic Switches: Test complex logic switches with CO_AND switch door": PASS
```
dungeonmania.automarking.LogicGatesTest

  Test Test complex logic switches with CO_AND switch door PASSED

SUCCESS: Executed 1 tests in 1.1s

SUCCESS
```
#### Test Task-2f-15 "Logic Switches: Test complex logic switches failure with CO_AND switch door": PASS
```
dungeonmania.automarking.LogicGatesTest

  Test Test complex logic switches failure with CO_AND switch door PASSED

SUCCESS: Executed 1 tests in 504ms

SUCCESS
```
#### Test Task-2f-16 "Logic Switches: Test an XOR light bulb does not remain on": PASS
```
dungeonmania.automarking.LogicGatesTest

  Test Test an XOR light bulb does not remain on PASSED

SUCCESS: Executed 1 tests in 532ms

SUCCESS
```
#### Test-Task-2f-17 "Logic Switches: Test an XOR switch door does not remain open":FAIL
```
dungeonmania.automarking.LogicGatesTest

  Test Test an XOR switch door does not remain open FAILED

  org.opentest4j.AssertionFailedError: expected: <Position [x=2, y=2, z=0]> but was: <Position [x=3, y=2, z=0]>
      at app//dungeonmania.automarking.LogicGatesTest.switchDoorCloses(LogicGatesTest.java:673)


FAILURE: Executed 1 tests in 1.2s (1 failed)

FAILURE
```
#### Test Task-2f-18 "Logic Switches: Test an OR lightbulb can be turned on; off; and on again": PASS
```
dungeonmania.automarking.LogicGatesTest

  Test Test an OR lightbulb can be turned on, off, and on again PASSED

SUCCESS: Executed 1 tests in 543ms

SUCCESS
```
## Summary

- Task-2a-1: PASS
- Task-2a-2: PASS
- Task-2a-3: PASS
- Task-2a-4: PASS
- Task-2a-5: PASS
- Task-2a-6: PASS
- Task-2a-7: PASS
- Task-2a-8: PASS
- Task-2a-9: PASS
- Task-2a-10: PASS
- Task-2b-1: FAIL
- Task-2b-2: FAIL
- Task-2b-3: FAIL
- Task-2b-4: FAIL
- Task-2b-5: FAIL
- Task-2b-6: FAIL
- Task-2b-7: FAIL
- Task-2b-8: FAIL
- Task-2b-9: FAIL
- Task-2b-10: FAIL
- Task-2b-11: FAIL
- Task-2b-12: FAIL
- Task-2b-13: FAIL
- Task-2c-1: FAIL
- Task-2c-2: FAIL
- Task-2c-3: FAIL
- Task-2c-4: FAIL
- Task-2c-5: FAIL
- Task-2c-6: FAIL
- Task-2c-7: FAIL
- Task-2d-1: PASS
- Task-2d-2: PASS
- Task-2d-3: PASS
- Task-2d-4: FAIL
- Task-2d-5: PASS
- Task-2d-6: PASS
- Task-2d-7: PASS
- Task-2d-8: FAIL
- Task-2d-9: FAIL
- Task-2d-10: PASS
- Task-2d-11: PASS
- Task-2d-12: FAIL
- Task-2d-13: FAIL
- Task-2d-14: FAIL
- Task-2d-15: PASS
- Task-2e-1: FAIL
- Task-2e-2: FAIL
- Task-2e-3: FAIL
- Task-2e-4: FAIL
- Task-2e-5: FAIL
- Task-2e-6: FAIL
- Task-2e-7: FAIL
- Task-2e-8: FAIL
- Task-2e-9: FAIL
- Task-2e-10: FAIL
- Task-2e-11: FAIL
- Task-2f-1: PASS
- Task-2f-2: PASS
- Task-2f-3: PASS
- Task-2f-4: PASS
- Task-2f-5: PASS
- Task-2f-6: PASS
- Task-2f-7: PASS
- Task-2f-8: PASS
- Task-2f-9: PASS
- Task-2f-10: PASS
- Task-2f-11: PASS
- Task-2f-12: PASS
- Task-2f-13: FAIL
- Task-2f-14: PASS
- Task-2f-15: PASS
- Task-2f-16: PASS
- Task-2f-17: FAIL
- Task-2f-18: PASS
