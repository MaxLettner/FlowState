package at.htl.flowstate.Skills;

public enum SkillType {
    MAGIC("Magic"),
    MEELE("Meele");

    private final String name;

    SkillType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
