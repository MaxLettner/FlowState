package at.htl.flowstate.Skills;

public enum SkillType {
    START("Rusty Sword"),

    // Top level
    MAGIC("Magic"),
    MEELE("Meele"),
    RANGED("Ranged"),

    // Magic sub-trees
    MAGIC_ARCANE("Arcane"),
    MAGIC_ENCHANTING("Enchanting"),
    MAGIC_ELEMENTAL("Elemental"),

    // Arcane skills
    ARCANE_LEVITATION("Levitation"),
    ARCANE_MAGIC_MISSILE("Magic Missile"),
    ARCANE_MANA_SHIELD("Mana Shield"),

    // Elemental skills
    ELEMENTAL_FIRE_BALL("Fire Ball"),
    ELEMENTAL_ICECICLE("Icecicle"),
    ELEMENTAL_POISON_DARTS("Poison Darts"),

    // Enchanting skills
    ENCHANTING_LIFE_STEAL("Life Steal"),
    ENCHANTING_PIERCING("Piercing"),
    ENCHANTING_SPEED("Speed"),

    // Meele sub-trees
    MEELE_SWORDS("Swords"),
    MEELE_FISTICUFFS("Fisticuffs"),
    MEELE_BLUNT("Blunt"),

    // Swords weapons
    SWORDS_SHORTSWORD("Shortsword"),
    SWORDS_DUAL_WIELDING("Dual Wielding"),
    SWORDS_ZWEIHANDER("Zweihander"),

    // Fisticuffs weapons
    FISTICUFFS_LEATHER("Leather"),
    FISTICUFFS_METAL_GLOVES("Metal Gloves"),
    FISTICUFFS_SPIKE_GLOVES("Spike Gloves"),

    // Blunt weapons
    BLUNT_HAMMER("Hammer"),
    BLUNT_MORNINGSTAR("Morningstar"),
    BLUNT_SPRING_HAMMER("Spring Hammer"),

    // Ranged sub-trees
    RANGED_BOW("Bow"),
    RANGED_CROSSBOW("Crossbow"),
    RANGED_TRIDENT("Trident"),

    // Bow weapons
    BOW_BONE_BOW("Bone Bow"),
    BOW_SHORTBOW("Shortbow"),
    BOW_WAR_BOW("War Bow"),

    // Crossbow weapons
    CROSSBOW_DUAL_CROSSBOW("Dual Crossbow"),
    CROSSBOW_HEAVY_CROSSBOW("Heavy Crossbow"),
    CROSSBOW_POISON_CROSSBOW("Poison Crossbow"),

    // Trident weapons
    TRIDENT_HEAVY_TRIDENT("Heavy Trident"),
    TRIDENT_ICE_TRIDENT("Ice Trident"),
    TRIDENT_RECALL_TRIDENT("Recall Trident");


    private final String name;

    SkillType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
