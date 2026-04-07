package at.htl.flowstate.Skills;

public class Skill {
        private String name;
        private String description;
        private boolean isUnlocked;
        private SkillType type;


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
            if(!SkillList.getInstance().isSkillUnlocked(type)){
                SkillList.getInstance().unlockSkill(type);
            }
        }
        
        public void revoke() {
            isUnlocked = false;
        }
        public String getType() {
            return type.getName();
        }
}
