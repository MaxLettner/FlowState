package at.htl.flowstate.Components;

import com.almasb.fxgl.entity.component.Component;

public class PlayerStatsComponent extends Component {
    private double health = 100;
    private double maxHealth = 100;
    private double mana = 100;
    private double maxMana = 100;
    private double strength = 100;
    private double dexterity = 100;
    private double level = 0;
    private double skillPoints = 0;

    private final double HEAL_PERCENTAGE = 0.01; //healing per second
    private final double MANA_PERCENTAGE = 0.05; //mana regen per second

    private static final int MAX_INVINCIBILITY_FRAMES = 60;
    private int currentInvincibilityFrames = 0;

    //value to determine whether the player can attack;
    //different weapons use different values;
    //eg swords use 10 because only one at a time
    //fisticuffs use 5 because 2 at a time
    //value gets subtracted in respective SkillComponent and added back by the animation ending
    private int attackWeight = 10;
    private static final int MAX_ATTACK_WEIGHT = 10;

    @Override
    public void onUpdate(double tpf) {
        regenerate(tpf);
    }

    private void regenerate(double tpf) {
        if(mana < maxMana) {
            mana += maxMana * (MANA_PERCENTAGE * tpf);
        } else if (mana > maxMana) {
            mana = maxMana;
        }
        if(health < maxHealth) {
            health += maxHealth * (HEAL_PERCENTAGE * tpf);
        } else if (health > maxHealth) {
            health = maxHealth;
        }
        if(currentInvincibilityFrames > 0) {
            currentInvincibilityFrames--;
        }
    }

    //-----Damage-----
    public void takeDamage(double amount) {
        if(currentInvincibilityFrames == 0) {
            currentInvincibilityFrames = MAX_INVINCIBILITY_FRAMES;
            health -= amount;
        }
    }

    //-----Getters-----
    public double getMana() {
        return mana;
    }

    public double getMaxMana() {
        return maxMana;
    }

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    //-----Attack Checkers-----
    public void addAttackWeight(int v) {
        attackWeight += v;
        if(attackWeight > MAX_ATTACK_WEIGHT) attackWeight = MAX_ATTACK_WEIGHT;
    }

    public boolean takeAttackWeight(int v) {
        if(attackWeight - v < 0) return false;
        attackWeight -= v;
        return true;
    }

    public boolean takeMana(double m) {
        if(mana - m < 0) return false;
        mana -= m;
        return true;
    }
}
