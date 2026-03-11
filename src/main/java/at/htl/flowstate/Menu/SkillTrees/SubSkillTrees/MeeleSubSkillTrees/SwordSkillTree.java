package at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.MeeleSubSkillTrees;

import at.htl.flowstate.Menu.SkillTreeParent;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.scene.control.Button;

public class SwordSkillTree extends SkillTreeParent {
    public SwordSkillTree() {
        super(MenuType.GAME_MENU, "Swords Skill Tree");

        Button shortswordBtn = createSkillButton(SkillType.SWORDS_SHORTSWORD);
        Button dualWieldingBtn = createSkillButton(SkillType.SWORDS_DUAL_WIELDING);
        Button zweihaenderBtn = createSkillButton(SkillType.SWORDS_ZWEIHAENDER);

        root.getChildren().addAll(shortswordBtn, dualWieldingBtn, zweihaenderBtn);
    }
}
