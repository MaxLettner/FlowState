package at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.RangedSubSkillTrees;

import at.htl.flowstate.Menu.SkillTreeParent;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.scene.control.Button;

public class CrossBowSkillTree extends SkillTreeParent {
    public CrossBowSkillTree() {
        super(MenuType.GAME_MENU, "Crossbow Skill Tree");

        Button dualCrossbowBtn = createSkillButton(SkillType.CROSSBOW_DUAL_CROSSBOW);
        Button heavyCrossbowBtn = createSkillButton(SkillType.CROSSBOW_HEAVY_CROSSBOW);
        Button poisonCrossbowBtn = createSkillButton(SkillType.CROSSBOW_POISON_CROSSBOW);

        root.getChildren().addAll(dualCrossbowBtn, heavyCrossbowBtn, poisonCrossbowBtn);
    }
}
