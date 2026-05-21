package at.htl.flowstate.Menu.SkillTree.Components;

import at.htl.flowstate.Components.Player.PlayerStatsComponent;
import at.htl.flowstate.Game;
import at.htl.flowstate.Skills.SkillList;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.entity.Entity;

import static at.htl.flowstate.Skills.SubTreeType.*;

public class SkillTreeNode {
    private final SkillType skillType;
    private final SkillTreeNode[] children;
    private final SkillList skillList;
    private final Entity player;

    public SkillTreeNode(SkillType skillType, SkillTreeNode[] children) {
        this.skillType = skillType;
        this.children = children;
        this.skillList = SkillList.getInstance();
        player = Game.getPlayer();
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

    public void onClick() {
        if (!isUnlocked()) {
            if (player.getComponent(PlayerStatsComponent.class).takeSkillPoints(skillList.getSkill(skillType).getCost())) {
                skillList.unlockSkill(skillType);
                switch (skillList.getSkill(skillType).getSubTreeType()) {
                    case MAGIC: player.getComponent(PlayerStatsComponent.class).raiseMaxMana(); break;
                    case MELEE: player.getComponent(PlayerStatsComponent.class).raiseStrength(); break;
                    case RANGED: player.getComponent(PlayerStatsComponent.class).raiseDexterity(); break;
                }
            }
        } else {
            skillList.selectSkill(skillType);
        }
    }
}