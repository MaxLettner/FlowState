# FlowState

A 2D platformer / action RPG built with **Java 25**, **FXGL 17.3**, and **Box2D physics**. The world generates infinitely to the right, enemies chase and attack the player, and a three-branch skill tree (Melee · Ranged · Magic) lets you shape how you fight.

---

## Tech stack

|Layer|Choice|
|---|---|
|Language|Java 25|
|Framework|FXGL 17.3|
|Physics|Box2D (via FXGL)|
|Build|Maven + JPMS (`module-info.java`)|
|Version|0.6.1|
|Package root|`at.htl.flowstate`|

---

## Getting started

### Prerequisites

- Java 25 JDK
- Maven 3.9+

### Run

```bash
mvn javafx:run
```

### Build fat jar

```bash
mvn package
java -jar target/flowstate.jar
```

---

## Controls

|Key / Button|Action|
|---|---|
|A / D|Move left / right|
|W|Jump (hold for higher jump; tap for coyote jump) / float up (levitation)|
|S|Descend (levitation mode only)|
|Left click|Attack / cast with currently selected skill|
|I|Open / close skill tree|
|Escape|Game menu (Resume / Exit)|

---

## Project structure

```
src/main/java/at/htl/flowstate/
├── Game.java                              # Entry point, input, HUD, camera, background
├── module-info.java
├── Components/
│   ├── Chests/
│   │   └── ChestComponent                 # Grants a skill point on player collision
│   ├── Enemies/
│   │   ├── EnemyBehaviourComponent        # Abstract base: chasing, step-up, jump-over
│   │   ├── MeleeEnemyBehaviourComponent   # Contact damage
│   │   ├── RangedEnemyBehaviourComponent  # Stops at range, fires ballistic spear
│   │   ├── EnemyStatsComponent            # HP, poison, stun, knockback
│   │   ├── EnemyProjectileComponent       # Damages player, despawns on terrain hit
│   │   └── BallisticProjectileComponent   # Manual gravity + rotation for projectiles
│   ├── Identifier/
│   │   ├── PlatformIdentifierComponent
│   │   └── EnemyIdentifierComponent
│   ├── Player/
│   │   ├── PlayerMovementComponent        # Movement, jump, coyote, step-up, levitation
│   │   ├── PlayerStatsComponent           # HP, mana, XP, level, skill points, invincibility
│   │   ├── PlayerRouterComponent          # Routes left-click to the active skill component
│   │   ├── Blasts/
│   │   │   ├── BlastComponent             # Abstract growing AOE
│   │   │   ├── ExplosionBlastComponent    # Damage AOE
│   │   │   ├── IceBlastComponent          # Stun AOE
│   │   │   └── KnockbackBlastComponent    # Directional knockback AOE
│   │   ├── Helpers/
│   │   │   ├── HomingProjectileComponent  # Steered homing movement
│   │   │   └── DeleteAfterTimeComponent   # Auto-removes entity after N seconds
│   │   ├── MagicProjectiles/
│   │   │   ├── PlayerProjectileComponent  # Base: hit detection, pierce, platform cleanup
│   │   │   ├── FireballProjectileComponent
│   │   │   ├── IcecicleProjectileComponent
│   │   │   └── PoisonDartProjectileComponent
│   │   ├── Melee/
│   │   │   ├── WeaponDamageComponent      # Crit, lifesteal, knockback, stun on hit
│   │   │   └── AttackAnimations/
│   │   │       ├── AnimationComponent     # Abstract base, handles weight system
│   │   │       └── SwordAnimationComponent # Arc swing with eased rotation
│   │   ├── Skills/
│   │   │   ├── SkillComponent             # Abstract base for all three branches
│   │   │   ├── MeleeSkillComponent        # Attack weight system, weapon builder
│   │   │   ├── RangedSkillComponent       # Trident builder, homing support
│   │   │   └── MagicSkillComponent        # Enchantments, levitation, shield, projectile builder
│   │   └── Tridents/
│   │       ├── TridentComponent           # Base: pierce enemies, stick in ground, recall
│   │       ├── RecallTridentComponent     # Activates homing on ground hit
│   │       ├── IceTridentComponent        # Ice AOE blast on ground hit
│   │       └── HeavyTridentComponent      # Knockback AOE blast on ground hit
│   └── SpriteComponents/
│       └── SpriteComponent                # Idle / walk / jump / land in both directions
├── Factories/
│   ├── LevelFactory                       # Spawns: platform, chest
│   └── EnemyFactory                       # Spawns: meleeEnemy, rangedEnemy
├── Generation/
│   ├── LevelGeneration                    # Infinite procedural terrain + segment orchestration
│   ├── StructureGeneration                # Chest rooms and challenge platform stacks
│   └── EnemyGeneration                    # Per-segment enemy spawning with gang chance
├── Menu/
│   ├── GameMenu                           # Custom pause menu (Resume / Exit)
│   └── SkillTree/
│       ├── SkillTree                      # Full skill tree UI with two-level navigation
│       ├── SkillTreeParent                # Shared base: open/close, button styling
│       └── Components/SkillTreeNode       # Node data: unlock state, parent check, click handler
└── Skills/
    ├── Skill                              # Name, description, locked/unlocked state
    ├── SkillList                          # Singleton registry; drives PlayerRouterComponent
    └── SkillType                          # Enum of all skill identifiers
```

---

## Core systems

### Procedural world generation

`LevelGeneration` generates terrain ahead of the player on demand. A momentum-based **terrain drift** system produces smooth hills and valleys — 70 % of the time terrain is nearly flat, 30 % produces a real slope or hill/valley. Hard clamps keep the surface between Y 800 and Y 1000.

Segment types:

|Type|Chance|Details|
|---|---|---|
|Pit|6 %|Gap of 160–280 px; fall in and the game exits|
|Structure|3 %|Chest room or challenge tower (see below)|
|Ground segment|~91 %|Variable width 180–420 px; extends to Y 1500|

**Chest structure** — a 100 px flat segment with a chest on top that grants a skill point on contact.

**Challenge structure** — four floating platforms in a zigzag stack up to 800 px tall, chest at the summit.

**Enemy generation** — every normal ground segment has a 20 % chance to spawn enemies: 80 % chance of melee (with a 5 % gang-of-five roll), 20 % chance of a ranged enemy.

Entities more than 3500 px behind the viewport are removed each frame to keep Box2D stable.

### Physics & collision layers

Three Box2D category/mask layers prevent unwanted collisions:

|Layer|Bits|Collides with|
|---|---|---|
|`CATEGORY_TERRAIN`|`0x0001`|everything|
|`CATEGORY_PLAYER`|`0x0002`|terrain only|
|`CATEGORY_ENEMY`|`0x0004`|terrain only|

Player–enemy and enemy–enemy collisions are intentionally disabled at the physics layer. Damage is handled manually via `isColliding()` checks each frame.

### Player movement

`PlayerMovementComponent` handles all locomotion:

|Mechanic|Detail|
|---|---|
|Acceleration ramp|Speed builds from 60 % to 100 % over 80 frames; decelerates in the air|
|Coyote time|5-frame window to jump after walking off a ledge|
|Variable jump height|Releasing W early multiplies upward velocity by 0.45|
|Step-up|Automatically climbs ledges ≤ 20 px high; forward nudge prevents feedback loop|
|Sprite states|Idle · Walk · Jump · Land (with a 12-frame landing hold) for both directions|
|Levitation|Gravity fully overridden; W floats up, S floats down, neither hovers; drains mana|

### Enemy AI

Both enemy types share the `EnemyBehaviourComponent` base:

- Chase the player along the X axis at a randomised speed (±10 % variance)
- Automatically step up ledges ≤ 20 px
- Jump over obstacles between 20 px and 300 px tall
- Freeze all movement while stunned; knockback velocity decays via friction (600 units/s²)
- Sprite states mirror the player's (idle / walk / jump)

`MeleeEnemyBehaviourComponent` deals contact damage on every overlapping frame.

`RangedEnemyBehaviourComponent` stops within 500 px, faces the player, and fires a spear on a 2-second cooldown. The launch velocity is solved analytically from a ballistic trajectory equation (discriminant check, minimum-time solution). **Do not modify the math in `calculateBallisticVelocity` unless you fully understand it.**

### HUD

Three `ProgressBar` widgets in the upper-right corner:

|Bar|Colour|Tracks|
|---|---|---|
|Health|`#921616` red|Current HP / max HP|
|Mana|`#2300d5` blue|Current mana / max mana|
|Experience|Dark goldenrod|XP toward next level (resets on level-up)|

Active skill icons appear to the right at Y 145. Right-clicking any icon deactivates that skill.

### Parallax background

Two tiling `ImageView` instances (`bg1`, `bg2`) scroll at 5 % of the player's world X position, producing a subtle depth illusion without an additional physics body.

---

## Skill tree

### How it works

Pressing **I** opens a `SkillTree` overlay that sits directly on the game scene (no sub-scene pause). The UI has two levels:

1. **Top level** — three category buttons (Melee, Magic, Ranged). The first click on a category **unlocks** it and turns the button green. The second click **opens** the full tree for that category.
2. **Full tree** — shows the three sub-categories side by side, each with their three leaf skills beneath. Locked nodes are greyed out; unlockable nodes are orange; unlocked nodes are green. A "Go Back" button returns to the top level.

Unlocking a skill also **selects** it. `SkillList.updateSelected()` immediately pushes the new `SkillType` into `PlayerRouterComponent`, so the next left click uses the new skill — no extra confirmation step.

### Skill tree map

```
START
└── Rusty Sword (default weapon)

MELEE
├── Swords
│   ├── Shortsword        medium speed, medium damage
│   ├── Dual Wielding     2 katanas, high speed, lower damage
│   └── Zweihander        slow, very high damage, high range
├── Fisticuffs
│   ├── Leather           high speed, low damage, 10 % crit
│   ├── Metal Gloves      medium speed/damage, 12 % crit
│   └── Spike Gloves      low speed/range, high damage, 15 % crit
└── Blunt
    ├── Hammer            slow, stuns on hit, high knockback
    ├── Morningstar       high range, high knockback
    └── Spring Hammer     high speed, minimal damage, extreme knockback

RANGED
├── Bow
│   ├── Shortbow          high attack speed, low damage, no pierce
│   ├── Bone Bow          medium all-round
│   └── War Bow           slow, high damage, high pierce
├── Crossbow
│   ├── Dual Crossbow     fires twice, very low pierce
│   ├── Heavy Crossbow    very slow, very high damage, infinite pierce
│   └── Poison Crossbow   stackable DOT, medium pierce
└── Trident
    ├── Basic Trident     sticks in ground, player walks over to recall
    ├── Recall Trident    homing return after hitting ground
    ├── Ice Trident       AOE ice blast on ground hit, slows on melee contact
    └── Heavy Trident     AOE knockback blast on ground hit

MAGIC
├── Arcane
│   ├── Magic Missile     mouse-aimed projectile
│   ├── Mana Shield       toggleable; reduces damage taken, drains mana
│   └── Levitation        toggleable; W/S to fly, drains mana
├── Elemental
│   ├── Fireball          AOE explosion on impact
│   ├── Icicle            pierces 5 targets, briefly stuns each
│   └── Poison Darts      3-dart spread (rotation matrix), stackable DOT
└── Enchanting
    ├── Life Steal        heals 5 % of all damage dealt
    ├── Piercing          adds pierce to weapon projectiles
    └── Speed             multiplies movement speed by 1.3×
```

### Enchantments & active skills

The following can run simultaneously in any combination, each draining mana continuously and suppressing mana regeneration while active. Only **one enchantment** can be active at a time; activating a new one automatically deactivates the previous.

|Skill|Icon|Drain|Effect|
|---|---|---|---|
|Enchantment — Crit|CRT (orange)|2 mana/s|Doubles crit chance; crits deal 5× damage|
|Enchantment — Speed|SPD (yellow)|2 mana/s|Movement speed ×1.3|
|Enchantment — Life Steal|LST (crimson)|2 mana/s|Heal 5 % of damage dealt|
|Enchantment — Piercing|PRC (cyan)|2 mana/s|Projectiles pierce additional targets|
|Levitation|LEV (blue)|5 mana/s|Full flight mode|
|Mana Shield|SLD (dark blue)|2 mana/s|Damage reduction|

---

## Attack weight system

Both `MeleeSkillComponent` and `RangedSkillComponent` use an **attack weight** pool (max 10) to prevent spamming. Each weapon costs a number of weight points on use; the points are returned when the animation entity is removed from the world. Heavy weapons (e.g. Zweihander at 10) lock the player out until the full swing completes; lighter weapons (e.g. dual-wield at 5) allow two simultaneous attacks.

---

## Repository

GitHub: [MaxLettner/FlowState](https://github.com/MaxLettner/FlowState)

Feature branches are developed independently and merged via pull request.
