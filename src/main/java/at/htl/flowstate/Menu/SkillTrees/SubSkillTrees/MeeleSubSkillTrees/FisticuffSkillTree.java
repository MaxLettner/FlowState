package at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.MeeleSubSkillTrees;

import at.htl.flowstate.Menu.SkillTreeParent;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.scene.control.Button;

public class FisticuffSkillTree extends SkillTreeParent {
    public FisticuffSkillTree() {
        super(MenuType.GAME_MENU, "Fisticuffs Skill Tree");

        Button leatherBtn = createSkillButton(SkillType.FISTICUFFS_LEATHER);
        Button metalGlovesBtn = createSkillButton(SkillType.FISTICUFFS_METAL_GLOVES);
        Button spikeGlovesBtn = createSkillButton(SkillType.FISTICUFFS_SPIKE_GLOVES);

        root.getChildren().addAll(leatherBtn, metalGlovesBtn, spikeGlovesBtn);
    }
}
