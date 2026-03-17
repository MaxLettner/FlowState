package at.htl.flowstate.Menu.SkillTree.Components;

import at.htl.flowstate.Skills.SkillList;
import at.htl.flowstate.Skills.SkillType;


public class SkillTreeNode {
    private final SkillType skillType;
    private final SkillType parentSkillType;
    private final SkillTreeNode[] children;
    private final SkillList skillList;

    public SkillTreeNode(SkillType skillType, SkillType parentSkillType, SkillTreeNode[] children) {
        this.skillType = skillType;
        this.parentSkillType = parentSkillType;
        this.children = children;
        this.skillList = SkillList.getInstance();
    }

    public SkillType getSkillType() {
        return skillType;
    }

    public SkillTreeNode[] getChildren() {
        return children;
    }

    public boolean isUnlocked() {
        return skillList.isSkillUnlocked(skillType);
    }

    public void unlock() {
        if (!isUnlocked()) {
            skillList.unlockSkill(skillType);
        }
    }

    public boolean isLeafNode() {
        return children == null || children.length == 0;
    }
    public boolean canUnlock() {

        if (isUnlocked()){
            return false;
        }
        if(parentSkillType == null){
            return true;
        }
        if(skillList.isSkillUnlocked(parentSkillType)){
            return true;
        }
        return false;
    }
}
