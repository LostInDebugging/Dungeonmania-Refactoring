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

[Links to your merge requests](/put/links/here)

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

[Links to your merge requests](/put/links/here)

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

[Links to your merge requests](/put/links/here)

> i. List one design principle that is violated by collectable objects based on the description above. Briefly justify your answer.

[Answer]

> ii. Refactor the inheritance structure of the code, and in the process remove the design principle violation you identified.

[Briefly explain what you did]

### d) More Code Smells

[Links to your merge requests](/put/links/here)

> i. What code smell is present in the above snippet?

[Answer]

> ii. Refactor the code to resolve the smell and underlying problem causing it.

[Briefly explain what you did]

### e) Open-Closed Goals

[Links to your merge requests](/put/links/here)

> i. Do you think the design is of good quality here? Do you think it complies with the open-closed principle? Do you think the design should be changed?

[Answer]

> ii. If you think the design is sufficient as it is, justify your decision. If you think the answer is no, pick a suitable Design Pattern that would improve the quality of the code and refactor the code accordingly.

[Briefly explain what you did]

### f) Open Refactoring

[Merge Request 1](/put/links/here)

[Briefly explain what you did]

[Merge Request 2](/put/links/here)

[Briefly explain what you did]

Add all other changes you made in the same format here:

## Task 2) Evolution of Requirements 👽

### a) Microevolution - Enemy Goal

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

### Choice 1 (Insert choice)

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
