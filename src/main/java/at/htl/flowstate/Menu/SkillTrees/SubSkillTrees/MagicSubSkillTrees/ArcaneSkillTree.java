package at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.MagicSubSkillTrees;

import at.htl.flowstate.Menu.SkillTreeParent;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.scene.control.Button;

public class ArcaneSkillTree extends SkillTreeParent {
    public ArcaneSkillTree() {
        super(MenuType.GAME_MENU, "Arcane Skill Tree");

        Button levitationBtn = createSkillButton(SkillType.ARCANE_LEVITATION);
        Button magicMissileBtn = createSkillButton(SkillType.ARCANE_MAGIC_MISSILE);
        Button manaShieldBtn = createSkillButton(SkillType.ARCANE_MANA_SHIELD);



        root.getChildren().addAll(levitationBtn, magicMissileBtn, manaShieldBtn);
    }
}
