package at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.MagicSubSkillTrees;

import at.htl.flowstate.Menu.SkillTreeParent;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.scene.control.Button;

public class ElementalSkillTree extends SkillTreeParent {
    public ElementalSkillTree() {
        super(MenuType.GAME_MENU, "Elemental Skill Tree");

        Button fireBallBtn = createSkillButton(SkillType.ELEMENTAL_FIRE_BALL);
        Button icecicleBtn = createSkillButton(SkillType.ELEMENTAL_ICECICLE);
        Button poisonDartsBtn = createSkillButton(SkillType.ELEMENTAL_POISON_DARTS);

        root.getChildren().addAll(fireBallBtn, icecicleBtn, poisonDartsBtn);
    }
}
