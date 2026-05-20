package at.htl.flowstate.Skills;

public class Skill {
        private final String name;
        private final String description;
        private boolean isUnlocked;
        private final SkillType type;
        private final int cost;
        private boolean isSelected;


        public Skill(String description, SkillType type, int cost) {
            this.name = type.getName();
            this.description = description;
            this.isUnlocked = false;
            this.type = type;
            this.cost = cost;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public boolean isUnlocked() {
            return isUnlocked;
        }

        public void unlock() {
            isUnlocked = true;
        }

        public void select(){
            isSelected = true;
        }

        public void deselect(){
            isSelected = false;
        }
        
        public void revoke() {
            isUnlocked = false;
        }

        public SkillType getType() {
            return type;
        }

        public int getCost() {
            return cost;
        }
}
