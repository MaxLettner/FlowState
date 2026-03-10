package at.htl.flowstate.Skills;

public enum SkillType {
    MAGIC("Magic"),
    MEELE("Meele"),
    RANGED("Ranged"),
    MAGIC_ARCANE("Arcane"),
    MAGIC_ENCHANTING("Enchanting"),
    MAGIC_ELEMENTAL("Elemental"),
    MEELE_ONE_HANDED("One Handed"),
    MEELE_TWO_HANDED("Two Handed"),
    MEELE_SPEAR("Spear"),
    RANGED_BOW("Bow"),
    RANGED_CROSSBOW("CrossBow"),
    RANGED_TRIDENT("Trident");


    private final String name;

    SkillType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
