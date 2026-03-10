package at.htl.flowstate.Skills;

public class Skill {
        private String name;
        private String description;
        private int levelRequirement;
        private boolean isUnlocked;
        private SkillType type;

        public Skill(String name, String description, int levelRequirement, SkillType type) {
            this.name = name;
            this.description = description;
            this.levelRequirement = levelRequirement;
            this.isUnlocked = false;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getLevelRequirement() {
            return levelRequirement;
        }

        public boolean isUnlocked() {
            return isUnlocked;
        }

        public void unlock() {
            isUnlocked = true;
        }
        public void revoke() {
            isUnlocked = false;
        }
        public String getType() {
            return type.getName();
        }
}
