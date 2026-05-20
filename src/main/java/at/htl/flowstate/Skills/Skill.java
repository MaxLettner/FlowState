package at.htl.flowstate.Skills;

public class Skill {
        private final String name;
        private boolean isUnlocked;
        private final SkillType type;
        private final int cost;


        public Skill(SkillType type, int cost) {
            this.name = type.getName();
            this.isUnlocked = false;
            this.type = type;
            this.cost = cost;
        }

        public String getName() {
            return name;
        }

        public boolean isUnlocked() {
            return isUnlocked;
        }

        public void unlock() {
            isUnlocked = true;
        }

        public SkillType getType() {
            return type;
        }

        public int getCost() {
            return cost;
        }
}
