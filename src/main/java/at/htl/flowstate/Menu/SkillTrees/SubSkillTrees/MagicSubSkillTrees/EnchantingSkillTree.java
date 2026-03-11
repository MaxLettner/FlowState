package at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.MagicSubSkillTrees;

import at.htl.flowstate.Menu.SkillTreeParent;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.scene.control.Button;

public class EnchantingSkillTree extends SkillTreeParent {
    public EnchantingSkillTree() {
        super(MenuType.GAME_MENU, "Enchanting Skill Tree");

        Button fireAspectBtn = createSkillButton(SkillType.ENCHANTING_FIRE_ASPECT);
        Button lifeStealBtn = createSkillButton(SkillType.ENCHANTING_LIFE_STEAL);
        Button piercingBtn = createSkillButton(SkillType.ENCHANTING_PIERCING);

        root.getChildren().addAll(fireAspectBtn, lifeStealBtn, piercingBtn);
    }
}
