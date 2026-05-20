package at.htl.flowstate.Skills;

import at.htl.flowstate.Components.Player.PlayerRouterComponent;
import at.htl.flowstate.Game;

import java.util.Arrays;

public class SkillList {
    private static SkillList instance;
    private final Skill[] skills;
    private int index_of_selected_skill;

    private SkillList() {
        index_of_selected_skill = 0;

        this.skills = new Skill[]{

                // Top level
                new Skill("Magic skill tree", SkillType.MAGIC, 1),
                new Skill("Melee skill tree", SkillType.MELEE, 1),
                new Skill("Ranged skill tree", SkillType.RANGED, 1),

                // Magic sub-trees
                new Skill("Arcane magic", SkillType.MAGIC_ARCANE, 2),
                new Skill("Enchanting magic", SkillType.MAGIC_ENCHANTING, 2),
                new Skill("Elemental magic", SkillType.MAGIC_ELEMENTAL, 2),

                // Arcane skills
                new Skill("Flying with continuous mana use", SkillType.ARCANE_LEVITATION, 3),
                new Skill("Homing Magic Missile, low DMG", SkillType.ARCANE_MAGIC_MISSILE, 3),
                new Skill("Reduces damage taken, continuous mana use", SkillType.ARCANE_MANA_SHIELD, 3),

                // Elemental skills
                new Skill("Straight flying missile, AOE damage over time", SkillType.ELEMENTAL_FIRE_BALL, 3),
                new Skill("Shoots icecicle, medium mana cost", SkillType.ELEMENTAL_ICECICLE, 3),
                new Skill("Stackable DMG over time, low mana cost", SkillType.ELEMENTAL_POISON_DARTS, 3),

                // Enchanting skills
                new Skill("Enhances Speed", SkillType.ENCHANTING_SPEED, 3),
                new Skill("Steals health based on outputted DMG", SkillType.ENCHANTING_LIFE_STEAL, 3),
                new Skill("Adds pierce to projectiles", SkillType.ENCHANTING_PIERCING, 3),

                // Melee sub-trees
                new Skill("Swords skill tree", SkillType.MELEE_SWORDS, 2),
                new Skill("Fisticuffs skill tree", SkillType.MELEE_FISTICUFFS, 2),
                new Skill("Blunt skill tree", SkillType.MELEE_BLUNT, 2),

                // Swords weapons
                new Skill("Short range, medium DMG, medium speed", SkillType.SWORDS_SHORTSWORD, 3),
                new Skill("2 katanas, high speed, medium range, low dmg", SkillType.SWORDS_DUAL_WIELDING, 3),
                new Skill("Low atk speed, high attack dmg, high range", SkillType.SWORDS_ZWEIHANDER, 3),

                // Fisticuffs weapons
                new Skill("High speed, low damage, 10% crit", SkillType.FISTICUFFS_LEATHER, 3),
                new Skill("Medium speed, medium damage, low range, 12% crit", SkillType.FISTICUFFS_METAL_GLOVES, 3),
                new Skill("Low speed, low range, high DMG, 15% crit", SkillType.FISTICUFFS_SPIKE_GLOVES, 3),

                // Blunt weapons
                new Skill("Slow attack, medium dmg, stuns on hit, high knockback", SkillType.BLUNT_HAMMER, 3),
                new Skill("High range, high knockback, slow attack speed", SkillType.BLUNT_MORNINGSTAR, 3),
                new Skill("High atk speed, very low dmg, very high knockback", SkillType.BLUNT_SPRING_HAMMER, 3),

                // Ranged sub-trees
                new Skill("Bow skill tree", SkillType.RANGED_BOW, 2),
                new Skill("Crossbow skill tree", SkillType.RANGED_CROSSBOW, 2),
                new Skill("Trident skill tree", SkillType.RANGED_TRIDENT, 2),

                // Bow weapons
                new Skill("Medium range, medium damage, low pierce", SkillType.BOW_BONE_BOW, 3),
                new Skill("High ATK speed, short range, low dmg, no pierce", SkillType.BOW_SHORTBOW, 3),
                new Skill("High damage, low speed, high range, high pierce", SkillType.BOW_WAR_BOW, 3),

                // Crossbow weapons
                new Skill("Shoots twice, low damage, high speed, very low pierce", SkillType.CROSSBOW_DUAL_CROSSBOW, 3),
                new Skill("Very low speed, very high damage, infinite pierce", SkillType.CROSSBOW_HEAVY_CROSSBOW, 3),
                new Skill("DOT stackable, low base dmg, medium pierce", SkillType.CROSSBOW_POISON_CROSSBOW, 3),

                // Trident weapons
                new Skill("Flies straight, infinite pierce, high damage", SkillType.TRIDENT_HEAVY_TRIDENT, 3),
                new Skill("Freezes enemies with AOE, slows on melee hit", SkillType.TRIDENT_ICE_TRIDENT, 3),
                new Skill("Medium range/close damage, no pierce, comes back", SkillType.TRIDENT_RECALL_TRIDENT ,3),

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

                skills[index_of_selected_skill].deselect();
                skill.select();
                index_of_selected_skill = Arrays.asList(skills).indexOf(skill);
                updateSelected();

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

    private void updateSelected(){
        Game.getPlayer().getComponent(PlayerRouterComponent.class).setCurrentSelection(skills[index_of_selected_skill].getType());
    }

}