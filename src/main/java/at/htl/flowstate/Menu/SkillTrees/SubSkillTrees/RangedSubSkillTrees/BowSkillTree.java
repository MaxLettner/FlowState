package at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.RangedSubSkillTrees;

import at.htl.flowstate.Menu.SkillTreeParent;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.scene.control.Button;

public class BowSkillTree extends SkillTreeParent {
    public BowSkillTree() {
        super(MenuType.GAME_MENU, "Bow Skill Tree");

        Button boneBowBtn = createSkillButton(SkillType.BOW_BONE_BOW);
        Button shortbowBtn = createSkillButton(SkillType.BOW_SHORTBOW);
        Button warBowBtn = createSkillButton(SkillType.BOW_WAR_BOW);

        root.getChildren().addAll(boneBowBtn, shortbowBtn, warBowBtn);
    }
}
