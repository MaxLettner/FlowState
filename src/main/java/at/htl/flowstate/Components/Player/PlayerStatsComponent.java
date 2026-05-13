package at.htl.flowstate.Components.Player;

import at.htl.flowstate.Components.Player.Skills.MagicSkillComponent;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.component.Component;

public class PlayerStatsComponent extends Component {
    private double health = 100;
    private double maxHealth = 100;
    private double mana = 100;
    private double maxMana = 100;
    private double strength = 100;
    private double experience = 0;
    private double maxExperience = 3;
    private double dexterity = 100;
    private double level = 0;
    private double skillPoints = 0;

    private final double HEAL_PERCENTAGE = 0.01; //healing per second
    private final double MANA_PERCENTAGE = 0.05; //mana regen per second

    private static final int MAX_INVINCIBILITY_FRAMES = 60;
    private int currentInvincibilityFrames = 0;

    @Override
    public void onUpdate(double tpf) {
        regenerate(tpf);
    }

    private void regenerate(double tpf) {
        if(entity.getComponent(MagicSkillComponent.class).isManaRegenEnabled()) {
            if(mana < maxMana) {
                mana += maxMana * (MANA_PERCENTAGE * tpf);
            } else if (mana > maxMana) {
                mana = maxMana;
            }
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

    //-----Health-----
    public void takeDamage(double amount) {
        if(currentInvincibilityFrames == 0) {
            currentInvincibilityFrames = MAX_INVINCIBILITY_FRAMES;
            health -= amount;
        }
    }

    public void heal(double amount) {
        health += amount;
        if(health > maxHealth) {
            health = maxHealth;
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

    public double getExperience() {
        return experience;
    }

    public double getMaxExperience() {
        return maxExperience;
    }

    public boolean takeMana(double m) {
        if(mana - m < 0) return false;
        mana -= m;
        return true;
    }

    public void addExperience() {
        experience++;
        if(experience == maxExperience) {
            level++;
            skillPoints++;
            maxExperience = Math.ceil(maxExperience * 1.5);
            experience = 0;
        }
    }

    public void addSkillPoints() {
        skillPoints++;
    }
}
