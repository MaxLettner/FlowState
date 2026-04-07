package at.htl.flowstate.Skills;

public class Skill {
        private String name;
        private String description;
        private boolean isUnlocked;
        private SkillType type;
        private boolean isSelected;


        public Skill(String description, SkillType type) {
            this.name = type.getName();
            this.description = description;
            this.isUnlocked = false;
            this.type = type;
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
}
