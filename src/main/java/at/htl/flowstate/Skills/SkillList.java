package at.htl.flowstate.Skills;

import at.htl.flowstate.Components.Player.PlayerRouterComponent;
import at.htl.flowstate.Game;
import com.almasb.fxgl.entity.Entity;

import java.util.Arrays;

public class SkillList {
    private static SkillList instance;
    private final Skill[] skills;
    private int index_of_selected_skill;
    private final Entity player;

    private SkillList() {
        player = Game.getPlayer();

        index_of_selected_skill = 0;

        this.skills = new Skill[]{

                // Top level
                new Skill(SkillType.MAGIC, 1),
                new Skill(SkillType.MELEE, 1),
                new Skill(SkillType.RANGED, 1),

                // Magic sub-trees
                new Skill(SkillType.MAGIC_ARCANE, 2),
                new Skill(SkillType.MAGIC_ENCHANTING, 2),
                new Skill(SkillType.MAGIC_ELEMENTAL, 2),

                // Arcane skills
                new Skill(SkillType.ARCANE_LEVITATION, 3),
                new Skill(SkillType.ARCANE_MAGIC_MISSILE, 3),
                new Skill(SkillType.ARCANE_MANA_SHIELD, 3),

                // Elemental skills
                new Skill(SkillType.ELEMENTAL_FIRE_BALL, 3),
                new Skill(SkillType.ELEMENTAL_ICICLE, 3),
                new Skill(SkillType.ELEMENTAL_POISON_DARTS, 3),

                // Enchanting skills
                new Skill(SkillType.ENCHANTING_SPEED, 3),
                new Skill(SkillType.ENCHANTING_LIFE_STEAL, 3),
                new Skill(SkillType.ENCHANTING_PIERCING, 3),

                // Melee sub-trees
                new Skill(SkillType.MELEE_SWORDS, 2),
                new Skill(SkillType.MELEE_FISTICUFFS, 2),
                new Skill(SkillType.MELEE_BLUNT, 2),

                // Swords weapons
                new Skill(SkillType.SWORDS_SHORTSWORD, 3),
                new Skill(SkillType.SWORDS_DUAL_WIELDING, 3),
                new Skill(SkillType.SWORDS_ZWEIHANDER, 3),

                // Fisticuffs weapons
                new Skill(SkillType.FISTICUFFS_LEATHER, 3),
                new Skill(SkillType.FISTICUFFS_METAL_GLOVES, 3),
                new Skill(SkillType.FISTICUFFS_SPIKE_GLOVES, 3),

                // Blunt weapons
                new Skill(SkillType.BLUNT_HAMMER, 3),
                new Skill(SkillType.BLUNT_MORNINGSTAR, 3),
                new Skill(SkillType.BLUNT_SPRING_HAMMER, 3),

                // Ranged sub-trees
                new Skill(SkillType.RANGED_BOW, 2),
                new Skill(SkillType.RANGED_CROSSBOW, 2),
                new Skill(SkillType.RANGED_TRIDENT, 2),

                // Bow weapons
                new Skill(SkillType.BOW_BONE_BOW, 3),
                new Skill(SkillType.BOW_SHORTBOW, 3),
                new Skill(SkillType.BOW_WAR_BOW, 3),

                // Crossbow weapons
                new Skill(SkillType.CROSSBOW_DUAL_CROSSBOW, 3),
                new Skill(SkillType.CROSSBOW_HEAVY_CROSSBOW, 3),
                new Skill(SkillType.CROSSBOW_POISON_CROSSBOW, 3),

                // Trident weapons
                new Skill(SkillType.TRIDENT_HEAVY_TRIDENT, 3),
                new Skill(SkillType.TRIDENT_ICE_TRIDENT, 3),
                new Skill(SkillType.TRIDENT_RECALL_TRIDENT ,3),

        };
    }

    public static SkillList getInstance() {
        if (instance == null) {
            instance = new SkillList();
        }
        return instance;
    }

    public Skill[] getSkills() {
        return skills;
    }

    public Skill getSkill(SkillType skillType) {
        for (Skill skill : skills) {
            if (skill.getType().equals(skillType)) {
                return skill;
            }
        }
        return null;
    }

    public void unlockSkill(SkillType skillType) {
        for (Skill skill : skills) {
            if (skill.getType().equals(skillType)) {
                skill.unlock();

                index_of_selected_skill = Arrays.asList(skills).indexOf(skill);
                updateRouterSelection();

                break;
            }
        }
    }
    public boolean isSkillUnlocked(SkillType skillType) {
        boolean isunlocked = false;
        for (Skill skill : skills) {
            if (skill.getType().equals(skillType)) {
                isunlocked = skill.isUnlocked();
                break;
            }
        }
        return isunlocked;
    }

    private void updateRouterSelection(){
        player.getComponent(PlayerRouterComponent.class).setCurrentSelection(skills[index_of_selected_skill].getType());
    }

}