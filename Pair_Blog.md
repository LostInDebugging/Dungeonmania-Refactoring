# Assignment II Pair Blog Template

## Task 0) Core Investigation 🔎

> What is the difference in purpose between the `DungeonManiaController` and the `Game` classes? (1 mark)

    DungeonManiaController
    It acts like a public-Interface for the game serving as a bridge between the frontend and backend logic.
    Also works to receive input from the player and convert it into backend logic calls.
    Exposes the following methods
        newGame()
        tick()
        interact()
        build()
    
    Game
    It is the core 'engine' of the game. It holds and handles all game data and states including
    Dugeon Map and entities
    Player and enemies
    Battle and Interactions
    Tick handling and logic 

    In summary, the DungeonManiaController is an API for the game, in that it allows
    an external system to query information about the game state and forward user input
    (i.e. in a UI system). As opposed to this, the Game class handles the logic and 
    the changing game states of the game.


> In the game, player and enemy actions to be performed later are stored as "comparable callbacks". Callbacks are pieces of code that can be run at a later time. However, for what purpose are these callbacks made *comparable*? (1 mark)

    ComparableCallback class wraps a Runnable and assigns it a priority v. These callbacks are made comparable so they be ordered by priority in a PriorityQueue. - (if actions like potion effects need to execute before others in a tick)
    Basically so that the game can prioritise which actions to run first each tick so that the game can run correctly

> Why is it so important that the dungeon files used for testing follow the technical specification? (4.1.1 in the MVP spec) (1 mark) 
 
    The game relies on reading the files in JSON format and they define entities, types and the layout
    If they don't follow the technical specification in 4.1.1 it could lead to:
        Failing tests even if logic is correct
        Runtime errors - missing fields 
        Invalid game states - no player loaded
    It would ensure that the test inputs are valid and compatible with the game expectations

> The Game class includes a method with the signature public Game tick(Direction movementDirection). Provide a detailed explanation of what this method does, including an overview of all the other methods it calls. Additionally, explain the purpose of the callback system it interacts with, and clarify the intentions behind the tickActions, futureTickActions, and currentAction fields. (1 mark)

    It triggers the main game loop for a single tick (a transition from one state to another state, as defined in the spec).

    The public method with signature Game tick(Direction movementDirection) is split into:

        - The registerOnce() method which registers the player movement as a callback
        "registerOnce(() -> player.move(this.getMap(), movementDirection), PLAYER_MOVEMENT, "playerMoves");"

        - Then calls a tick() which runs game logic based on the priority 
        which processes all tickActions and handles futureTickActions which are scheduled callbacks for next ticks
        updates tick counter

    tickActions - Priority Queue of actions scheduled for the current tick
    futureTickActions - Actions scheduled to occur in the future ticks
    currentAction - The callback being executed now


> A player with 10 health and 1 attack, holding a sword which gives a +1 attack bonus, battles a spider with 4 health and 1 attack. How many rounds of battle occur? How many ticks does the battle take? Explain how you came to this conclusion, referring to lines of code. How do the answers to these questions change if the player has additionally drunk an invisibility potion? (1 mark)

    In battleStatistics the while loop 53-60
        while (self.getHealth() > 0 && target.getHealth() > 0)
            self.setHealth(self.getHealth() - damageOnSelf);
            target.setHealth(target.getHealth() - damageOnTarget);
            rounds.add(new BattleRound(-damageOnSelf, -damageOnTarget));
    Each iteration creates a new round so:

    We assume that the reducer for the spider is 5 while the reducer for the
    player is 10, as per the spec. Obviously the actual values depend on the specific
    construction of BattleStatistics, but for now we assume that the code follows
    the spec, in which case the following lines are responsible for the damage values

    double damageOnSelf = target.getMagnifier() * (target.getAttack() - self.getDefence()) / self.getReducer();
    double damageOnTarget = self.getMagnifier() * (self.getAttack() - target.getDefence()) / target.getReducer();

    The actual number of rounds are as follows, with the enemy being spider in this case:

    Battle occurs:
    - Round 1   enemy health    = 4 - ((1 + 1) / 5)  = 3.6
                player health   = 10 - (1 / 10)       = 9.9
    - Round 2   enemy health    = 3.6 - ((1 + 1) / 5)  = 3.2
                player health   = 9.9 - (1 / 10)       = 9.8
    ...
    - Round 10   enemy health    = 0.4 - ((1 + 1) / 5)  = 0
                player health   = 9.1 - (1 / 10)       = 9

    It all works in the same tick. So 10 rounds 1 tick.

    For invisibility, under player.java 
        public BattleStatistics applyBuff(BattleStatistics origin)
        else if (state.isInvisible())
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 1, 1, false, false  (<----isBattleEnabled)));
    which disabled battle from Battle statistics isBattleEnabled is set to false

    So 0 rounds 0 ticks.


## Task 1) Code Analysis and Refactoring ⛏️

### a) From DRY to Design Patterns

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/25T1/groups/T09A_MALTESE/assignment-ii/-/merge_requests/3)

> i. Look inside src/main/java/dungeonmania/entities/enemies. Where can you notice an instance of repeated code? Note down the particular offending lines/methods/fields.

    - The repeated code is inside the methods with signature "public void move(Game game)" inside ZombieToast.java and Mercenary.java.
    - The "random" and "runAway" case statements repeat the code that involves determining how the particular enemy type will move.

> ii. What Design Pattern could be used to improve the quality of the code and avoid repetition? Justify your choice by relating the scenario to the key characteristics of your chosen Design Pattern.

    - This is a great opportunity to implement the Strategy pattern by introducing movementStrategy into the Enemy class.
    - By implementing a RandomStrategy and a RunawayStrategy and using composition under a common super-interface called movementStrategy, we will not have to repeat this code inside both Mercenary and ZombieToast.java.
    - We can also add the spider's movement strategy to it so even the spider class can delegate movement logic to another class. This way we can allow the movementStrategy field to be a field of the superclass Enemy rather than only having it in some of its subclass enemy types.
    - The movement behaviour is a specific type of behaviour in which the enemy is trying to get from one tile to another tile. Since there a lot of different ways for the enemy to get from one tile to another (movement strategies), it makes sense to use the strategy pattern since that's what it excels at; accomplishing something specific in a lot of different ways.
    - Using the strategy pattern in this scenario also decouples the specific movement behaviour into a different class, which means that the code follows the open-closed principle more closely as when we need to add a different type of strategy, we do not have any specific enemy subclass, but rather we can simply create another class that implements the movementStrategy interface and implement our strategy in that.

> iii. Using your chosen Design Pattern, refactor the code to remove the repetition.

    - I created a MovementStrategy interface that has the method signature of the move function that previously existed in all the enemy type subclasses. 
    - I added a field to the Enemy class called MovementStrategy because every enemy has a movement strategy. 
    - The field would get initialised in Enemy from its specific enemy subclasses super calls.
    - This way I can simply call enemy.move() and remove the move method from all the enemy subclasses.
    - Places where strings were used to change the movement strategy type have been updated accordingly.

### b) Pattern Analysis

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/25T1/groups/T09A_MALTESE/assignment-ii/-/merge_requests/4)

> i. Identify one place where the State Pattern is present in the codebase. Do you think this is an appropriate use of the State Pattern?

    Yes it is appropriate, the State Pattern is used in the playerState package, where the Player entity manages different states such as BaseState, InvincibleState, and InvisibleState.This approach is an effective use of the State Pattern.

> ii. (Option 1) If you answered that it was an appropriate use of the State Pattern, explain why. In your answer, explain how the implementation relates to the purpose and the key characteristics of the State Pattern. Include relevant snippets of code to support your answer.

    The State Pattern is used to manage the different potion effects on the player such as invincibility or invisibility. The Player class delegates potion-related behavior to the current PlayerState Object, rather than using large conditional blocks.
    
    From Player.java
    public void changeState(PlayerState playerState) {
        state = playerState;
    }

    Each state class controls how the player transitions between states. For example
    from InvisibleState.java

    public void transitionInvisible() {
        Player player = getPlayer();
        player.changeState(new InvisibleState(player));
    }

    It encapsulates state-specific logic such as how enemies react to the player.
    It allows dynamic transitions between states during gameplay.
    It keeps the Player class free of repetitive conditional statements.
    It promotes Single Responsibility — each class does one thing well.
    it prevents duplication of logic across states.
    It makes it easier to add new states without changing existing logic.

    The pattern used improves the code quality and maintainability.

### c) Inheritance Design

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/25T1/groups/T09A_MALTESE/assignment-ii/-/merge_requests/6)

> i. List one design principle that is violated by collectable objects based on the description above. Briefly justify your answer.

    - The Liskov Substitution Principle (LSP) is violated. 
    - It states that objects of a superclass type are replaceable by any of their child classes without affecting functionality.
    - However, since items like wood and treasure don't have attributes like durability, this means that there will be functionality in the superclass type (collectable) which will not work or be used for the child class (Wood/Treasure).

> ii. Refactor the inheritance structure of the code, and in the process remove the design principle violation you identified.

    - Added a getDurability() method to the Usable interface
    - Created interfaces for Buffable and Buildable rather than having it is a class, considering
    buildable didn't actually have unique attributes that would be common to its subclasses.
    - Removed applyBuff and getDurability abstract methods from InventoryItem.

    - This merge request initially contains a new Buildable interface, but we later realised it was useless as it implemented the onOverlap method whereas buildable items went straight to inventory. This was fixed in a later merge request - provided here: [Fixed in this merge request](https://nw-syd-gitlab.cseunsw.tech/COMP2511/25T1/groups/T09A_MALTESE/assignment-ii/-/merge_requests/11)


### d) More Code Smells

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/25T1/groups/T09A_MALTESE/assignment-ii/-/merge_requests/5)

> i. What code smell is present in the above snippet?

    The code in Switch.java shows Feature Envy smell. 
    The Switch class reaches into each Bomb object to access its position and  its radius, and performs the activation logic itself. 
    This violates Encapsulation and the Single Responsibility Principle as the logic for what a bomb should do when activated is being handled by a different class.

    This also reduces cohesion in the Switch class and couples it tightly with Bomb.

> ii. Refactor the code to resolve the smell and underlying problem causing it.

    I moved the bomb activation logic into a new method activate(GameMap map) inside the Bomb class. 
    Now, instead of Switch calculating the area of effect and performing destruction directly, it delegates that responsibility to each bomb via b.activate(map). This places behavior where it logically belongs in the Bomb class. 
    The result is cleaner, modular, and easier-to-maintain.

### e) Open-Closed Goals

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/25T1/groups/T09A_MALTESE/assignment-ii/-/merge_requests/7)

> i. Do you think the design is of good quality here? Do you think it complies with the open-closed principle? Do you think the design should be changed?

    - The design is not of good quality here. 
    - This is because it does not apply with the open-closed principle. In the future, if a decision to add a new type of goal is made, then we have to open the whole Goal.java file and make modifications to the massive if statement. 
    - We would also have to make modifications to the if statement in GoalFactory.java when we are trying to read the goalType from .json files.
    - Ideally, goals should all have their own classes, so when a new type of goal is added, it is easy to extend the code without making many modifications to the existing code, if any at all.
    - This is why the design should be changed.

> ii. If you think the design is sufficient as it is, justify your decision. If you think the answer is no, pick a suitable Design Pattern that would improve the quality of the code and refactor the code accordingly.

    - The composite pattern is best suited for this code. Considering that a compound goal can have more compound goals as its subgoals, this means we can model this behaviour using a tree structure.
    - By representing goals in a tree-like structure, we can use recursion and hence the composite pattern is best suited for this problem.

    - Changes made:
        - Modified goal to be an interface with child subclasses BasicGoal and CompoundGoal. BasicGoals are singular goals like exitGoals, whereas CompoundGoal has 2 goals as fields.
        - Added relevant methods and changed GoalFactory.java to work with refactored code.

### f) Open Refactoring

[Merge Request 1](/put/links/here)

    I created a StaticEntity class that provides default implementation for methods like onOverlap, onMovedAway and onDestroy removing repeated empty methods for classes such such Exit, Wall, Door, Boulder, Switch, and InventoryItem which now extend StaticEntity.
    It improves the inheritance structure and enforcing cleaner design.

[Merge Request 2](/put/links/here)

    Fixed Deprecated methods
    The Deprecated Method Warning is a signal that a method is outdated, while they still work they, are no longer recomended and can break our files in the future. They can be replacaed by better alternatives that improve code maintainability.
    In this Request We refactored the deprecated translate() method to use setPosition() in GameMap.java and Bomb.java.
    By addressing the warnings, we ensure our application continues to work reliably.


[Merge Request 3](/put/links/here)

    All minor changes for LoD before a large change in GameMap.
    Made LoD improvments in ResponseBuilder with helper methods in Player, Game
    This was a better approach that prevents it from "knowing" the internal structure 
    Improved Encapsulation of BattleFacade by introducing helper methods instead of directly accessing player or enemy internals.
    Minor LoD improvement is Enemies same as above - Introducing helpers

Add all other changes you made in the same format here:

[Merge Request 4](https://nw-syd-gitlab.cseunsw.tech/COMP2511/25T1/groups/T09A_MALTESE/assignment-ii/-/merge_requests/11)

    - Removed Buildable.java: it contained only the onOverlap method which didn't make sense since built items go straight to inventory.
    - Introduced a new CollectableItem class extending InventoryItem, in order to reduce code duplication in the onOverlap method across collectable items.
    - fixed some code that answered 1c.

[Merge Request 5](https://nw-syd-gitlab.cseunsw.tech/COMP2511/25T1/groups/T09A_MALTESE/assignment-ii/-/merge_requests/12)

    - Removed onDestroy from Entity and instead created an interface - Destroyable. This interface is implemented by classes that need to clean up 
    once they are destroyed from the map, which right now are ZombieToastSpawners and Enemies.
    - Removed onMovedAway from Entity and instead only have it in switch. It does not make sense to have it in the entity class since only switch has some logic that is needed to be executed once an entity moves away from it.
    - Fixed minor LoD violations in entities/buildables
    - Added refactoring.txt - a file for planning further refactors.

[Merge Request 6](https://nw-syd-gitlab.cseunsw.tech/COMP2511/25T1/groups/T09A_MALTESE/assignment-ii/-/merge_requests/13)   

    - Fixed hardcoding in Inventory.java by moving construction logic into EntityFactory.java.
    - removed the remove boolean from the build method, and implemented an enum instead of boolean for keeping track of which buildable we're building.
## Task 2) Evolution of Requirements 👽

### a) Microevolution - Enemy Goal

[Links to your merge requests](/put/links/here)

**Assumptions**

    The current code does not track the number of defeated enemies. We assume that adding such a counter in the Game class is acceptable.

    Enemy spawners are defined as instances of the ZombieToastSpawner class. Or more if added in the future.

    The enemy goal is met only when two conditions are true:
        The player has defeated at least a target number of enemies as specified in the configuration.
        There are no remaining enemy spawners on the map.

    The goal description (returned via toString()) will show a short string (e.g., ":enemies") similar to those in TreasureGoal and ExitGoal if the goal has not yet been achieved.

**Design**

    Updates in the Game Class:
        New Field:
        Add an integer field defeatedEnemies to track how many enemies have been defeated.
        New Getters:
        Implement public int getDefeatedEnemies() to allow other classes to access this count.

        Battle Method Modification:
        Update the battle(Player player, Enemy enemy) method so that when an enemy’s health falls to or below zero, the defeatedEnemies counter is incremented.

    Implementation of the EnemyGoal Class:
        Create a new class named EnemyGoal (placed in dungeonmania.goals.GoalTypes), which extends BasicGoal.
        Field:
        A private int enemyTarget field is used to store the target number of enemy defeats.
        Methods:
            achieved(Game game):
            This method returns true only when:
                game.getDefeatedEnemies() >= enemyTarget
                The map’s entity list contains no instances of ZombieToastSpawner.
            toString(Game game):
            Returns a descriptive string (e.g., ":enemies") if the goal is not met, or an empty string if it is achieved.

    GoalFactory Update:
        In the GoalFactory.java file, add a new case to the switch statement to handle a goal type of "enemies". This case will use the configuration value for "enemy_goal" to create an EnemyGoal instance.
    
    Added a test to manually check if counter increases during game.


**Changes after review**

    No changes

**Test list**

    Possible test list
    Test 1: Verify that when the player has not defeated any enemy- EnemyGoal.achieved(game) returns false.

    Test 2: Simulate a battle where the enemy is defeated, incrementing the counter; if the number of defeated enemies is less than the target, the goal should still be false.

    Test 3: Ensure that if the required number of enemies are defeated but at least one enemy spawner (e.g., a ZombieToastSpawner) remains on the map, the goal remains unachieved.

    Test 4: Confirm that when conditions are met, the defeated enemy count is at the target count and all spawners have been cleared—the goal returns true.

**Other notes**

[Any other notes]

### Choice 1 (Task 2f - Logic switches)

[Links to your merge requests](/put/links/here)

**Assumptions**

    - A player cannot pick up the wire and light bulb and store them in their inventory 
    - Whether the player can stand on light bulbs is undefined
    - The behaviour of whether boulders can be pushed onto wires & lightbulbs is undefined
    - Whether sunstones can open switch doors is undefined

    - Any scenario where the order in which activated components perform their action is undefined.
    For example, this case where a logical bomb might activate and destroy parts of a circuit before other logical components are able to activate
    - Bombs exploding affecting a circuit is undefined

**Design**

- Split into 3 MRs:
    1. Writing test cases
    2. Introduction of logical entities and the OR logic
    3. AND, XOR and CO_AND logic

Initial Design considerations: 

1. Test cases - Already wrote basic test case scenarios in pair blog
2. Introduction of logical entities and the OR logic
    - Wires 
        - Inherit from the staticEntity class and contains methods for subscribing to a switch
        - subscribes to a particular switch if there is a valid conductive path to the switch
        - this way, if a particular switch is turned off, we simply check for each subscribing conductor whether
        it is subscribed to an on switch. If it is, then it stays on, and turns off otherwise.
        - Similarly, when a particular switch is turned on, then each subscribing conductor should be turned on
    - Logic door/lightbulb/logic bomb
        - We simply check the logic condition and check cardinally adjacent directions for 
        active current and turn on/off based on that
        - switch door inherits from door and logical bombs inherit from bombs and implement the logical interface
        - lightbulbs inherit from staticEntity and implement the logical interface
    - The logical interface
        - Contains a method that takes in a LogicCondition and gameMap as a parameter and returns
        true if the condition is satisfied, and false otherwise
    - The LogicCondition abstract class
        - All the concrete condition classes inherit from LogicCondition and have a method
        called isSatisfied which returns true if the logical condition is satisfied.
3. AND, XOR and CO_AND logic
    - Checking AND and XOR is simply a matter of checking adjacent tiles for active conductors
    - However, for CO_AND we will need to somehow need a global tick counter.
    - The current plan is to have a tickCount in the Game class, and then each conductor will 
    have a tickActivated field which will store the number of the first tick that activated 
    that conductor. If a wire is already on and a switch that would turn that wire on
    is switched on, then the tickCount should not be updated. 

After completion:
    - Added a conductor interface which wires and switches implement.
    - the logical interface (in initial design) is actually implemented as a 
    LogicalEntity interface which has a method switchState which takes in a map
    and checks the state of the logical entity.
    - The LogicCondition ended up being an interface which had a isSatisfied method
    returning true if the logic condition was satisfied. Every Entity that implemented
    LogicalEntity had a LogicCondition field. 
**Changes after review**

[Design review/Changes made]

**Test list**

- Conditional Logic tests:
    1. Check OR requirements met
    2. Check OR requirements not met
    3. Check AND requirements met
    4. check AND requirements not met
    5. check XOR requirements met
    6. check XOR requirements not met
    7. check CO_AND requirements met
    8. check CO_AND requirements not met (All adjacent conductors have current but not in same tick)
    9. check CO_AND requirements not met (Not all adjacent conductors have current)
    10. for CO_AND, check that another conductor does not change the start tick of an already active conductor 
    (and hence does not turn off the CO_AND entity)

- Logical Entity tests:
    1. Check that light bulb activates next to activated switch
    2. Check that logical bomb activates next to activated switch
    3. Check that Switch door activates next to activated switch
    4. Check that current does not propagate through inactive switches, i.e. 
        s -> is -> e, where s, is and e are switch, inactive switch and logical entity respectively,
        should not let e be activated.
    5. Check that Logical entities activate next to activated wire
    6. Check that wire propagates current when next to active switch
    7. Check that if there are multiple switches powering a logic entity, then turning
    one off does not deactivate the logic entity.

Some of the tests I have written target a specific point, but in doing so, they manage
to target some other points too if they pass
**Other notes**

I ended up doing only 2 MRs: 1 for the tests and 1 for the implementation

### Choice 2 (Insert choice)

[Links to your merge requests](/put/links/here)

**Assumptions**

[Any assumptions made]

**Design**

[Design]

**Changes after review**

[Design review/Changes made]

**Test list**

[Test List]

**Other notes**

[Any other notes]

### Choice 3 (Insert choice) (If you have a 3rd member)

[Links to your merge requests](/put/links/here)

**Assumptions**

[Any assumptions made]

**Design**

[Design]

**Changes after review**

[Design review/Changes made]

**Test list**

[Test List]

**Other notes**

[Any other notes]

## Task 3) Investigation Task ⁉️

[Merge Request 1](/put/links/here)

[Briefly explain what you did]

[Merge Request 2](/put/links/here)

[Briefly explain what you did]

Add all other changes you made in the same format here:
