package at.htl.flowstate.Skills;

import java.util.Arrays;

public class SkillList {
    private static SkillList instance;
    private final Skill[] skills;

    private SkillList() {
        this.skills = new Skill[]{

                // Top level
                new Skill("Magic skill tree", SkillType.MAGIC),
                new Skill("Meele skill tree", SkillType.MEELE),
                new Skill("Ranged skill tree", SkillType.RANGED),

                // Magic sub-trees
                new Skill("Arcane magic", SkillType.MAGIC_ARCANE),
                new Skill("Enchanting magic", SkillType.MAGIC_ENCHANTING),
                new Skill("Elemental magic", SkillType.MAGIC_ELEMENTAL),

                // Arcane skills
                new Skill("Flying with continuous mana use", SkillType.ARCANE_LEVITATION),
                new Skill("Homing Magic Missile, low DMG", SkillType.ARCANE_MAGIC_MISSILE),
                new Skill("Reduces damage taken, continuous mana use", SkillType.ARCANE_MANA_SHIELD),

                // Elemental skills
                new Skill("Straight flying missile, AOE damage over time", SkillType.ELEMENTAL_FIRE_BALL),
                new Skill("Shoots icecicle, medium mana cost", SkillType.ELEMENTAL_ICECICLE),
                new Skill("Stackable DMG over time, low mana cost", SkillType.ELEMENTAL_POISON_DARTS),

                // Enchanting skills
                new Skill("Fire Aspect enchantment", SkillType.ENCHANTING_FIRE_ASPECT),
                new Skill("Steals health based on outputted DMG", SkillType.ENCHANTING_LIFE_STEAL),
                new Skill("Adds pierce to projectiles", SkillType.ENCHANTING_PIERCING),

                // Meele sub-trees
                new Skill("Swords skill tree", SkillType.MEELE_SWORDS),
                new Skill("Fisticuffs skill tree", SkillType.MEELE_FISTICUFFS),
                new Skill("Blunt skill tree", SkillType.MEELE_BLUNT),

                // Swords weapons
                new Skill("Short range, medium DMG, medium speed", SkillType.SWORDS_SHORTSWORD),
                new Skill("2 katanas, high speed, medium range, low dmg", SkillType.SWORDS_DUAL_WIELDING),
                new Skill("Low atk speed, high attack dmg, high range", SkillType.SWORDS_ZWEIHAENDER),

                // Fisticuffs weapons
                new Skill("High speed, low damage, 10% crit", SkillType.FISTICUFFS_LEATHER),
                new Skill("Medium speed, medium damage, low range, 12% crit", SkillType.FISTICUFFS_METAL_GLOVES),
                new Skill("Low speed, low range, high DMG, 15% crit", SkillType.FISTICUFFS_SPIKE_GLOVES),

                // Blunt weapons
                new Skill("Slow attack, medium dmg, stuns on hit, high knockback", SkillType.BLUNT_HAMMER),
                new Skill("High range, high knockback, slow attack speed", SkillType.BLUNT_MORNINGSTAR),
                new Skill("High atk speed, very low dmg, very high knockback", SkillType.BLUNT_SPRING_HAMMER),

                // Ranged sub-trees
                new Skill("Bow skill tree", SkillType.RANGED_BOW),
                new Skill("Crossbow skill tree", SkillType.RANGED_CROSSBOW),
                new Skill("Trident skill tree", SkillType.RANGED_TRIDENT),

                // Bow weapons
                new Skill("Medium range, medium damage, low pierce", SkillType.BOW_BONE_BOW),
                new Skill("High ATK speed, short range, low dmg, no pierce", SkillType.BOW_SHORTBOW),
                new Skill("High damage, low speed, high range, high pierce", SkillType.BOW_WAR_BOW),

                // Crossbow weapons
                new Skill("Shoots twice, low damage, high speed, very low pierce", SkillType.CROSSBOW_DUAL_CROSSBOW),
                new Skill("Very low speed, very high damage, infinite pierce", SkillType.CROSSBOW_HEAVY_CROSSBOW),
                new Skill("DOT stackable, low base dmg, medium pierce", SkillType.CROSSBOW_POISON_CROSSBOW),

                // Trident weapons
                new Skill("Flies straight, infinite pierce, high damage", SkillType.TRIDENT_HEAVY_TRIDENT),
                new Skill("Freezes enemies with AOE, slows on meele hit", SkillType.TRIDENT_ICE_TRIDENT),
                new Skill("Medium range/close damage, no pierce, comes back", SkillType.TRIDENT_RECALL_TRIDENT),

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
            if (skill.getType().equals(skillType.getName())) {
                return skill;
            }
        }
        return null;
    }

    public void unlockSkill(SkillType skillType) {
        for (Skill skill : skills) {
            if (skill.getType().equals(skillType.getName())) {
                skill.unlock();
                break;
            }
        }
    }
    public boolean isSkillUnlocked(SkillType skillType) {
        boolean isunlocked = false;
        for (Skill skill : skills) {
            if (skill.getType().equals(skillType.getName())) {
                isunlocked = skill.isUnlocked();
                break;
            }
        }
        return isunlocked;
    }

}