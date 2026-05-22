package at.htl.flowstate.Components.Player;

import at.htl.flowstate.Components.Player.Skills.MagicSkillComponent;
import at.htl.flowstate.Game;
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
    private double skillPoints = 0;

    private static final double HEAL_PERCENTAGE = 0.01; //healing per second
    private static final double MANA_PERCENTAGE = 0.05; //mana regen per second

    private static final int MAX_INVINCIBILITY_FRAMES = 60;
    private int currentInvincibilityFrames = 0;

    private Runnable onSkillPointsChanged = null;

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

    public void raiseMaxMana() {
        maxMana += 10;
        mana += 10;
    }

    public void raiseStrength() {
        strength += 5;
    }

    public void raiseDexterity() {
        strength += 5;
        entity.getComponent(PlayerMovementComponent.class).updateMaxMoveSpeed();
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

    public double getDexterity() {
        return dexterity;
    }

    public double getStrength() {
        return strength;
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

    public boolean takeSkillPoints(double s) {
        if(skillPoints - s < 0) return false;
        skillPoints -= s;
        fireSkillPointsChanged();
        return true;
    }

    public void addExperience() {
        experience++;
        if(experience == maxExperience) {
            skillPoints++;
            maxExperience++;
            experience = 0;
            fireSkillPointsChanged();
        }
    }

    public void addSkillPoint() {
        skillPoints++;
        fireSkillPointsChanged();
    }

    public double getSkillPoints() {
        return skillPoints;
    }

    public void setOnSkillPointsChanged(Runnable callback) {
        onSkillPointsChanged = callback;
    }

    private void fireSkillPointsChanged() {
        if (onSkillPointsChanged != null) onSkillPointsChanged.run();
    }
}
