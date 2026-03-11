package at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.MeeleSubSkillTrees;

import at.htl.flowstate.Menu.SkillTreeParent;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.scene.control.Button;

public class BluntSkillTree extends SkillTreeParent {
    public BluntSkillTree() {
        super(MenuType.GAME_MENU, "Blunt Skill Tree");

        Button hammerBtn = createSkillButton(SkillType.BLUNT_HAMMER);
        Button morningstarBtn = createSkillButton(SkillType.BLUNT_MORNINGSTAR);
        Button springHammerBtn = createSkillButton(SkillType.BLUNT_SPRING_HAMMER);

        root.getChildren().addAll(hammerBtn, morningstarBtn, springHammerBtn);
    }
}
