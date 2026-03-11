package at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.RangedSubSkillTrees;

import at.htl.flowstate.Menu.SkillTreeParent;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.scene.control.Button;

public class TridentSkillTree extends SkillTreeParent {
    public TridentSkillTree() {
        super(MenuType.GAME_MENU, "Trident Skill Tree");

        Button heavyTrident = createSkillButton(SkillType.TRIDENT_HEAVY_TRIDENT);
        Button recallTrident = createSkillButton(SkillType.TRIDENT_RECALL_TRIDENT);
        Button iceTrident = createSkillButton(SkillType.TRIDENT_ICE_TRIDENT);

        root.getChildren().addAll(heavyTrident, recallTrident, iceTrident);
    }

}
