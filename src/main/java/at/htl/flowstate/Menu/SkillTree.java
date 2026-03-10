package at.htl.flowstate.Menu;

import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.MenuType;

import javafx.scene.control.Button;

public class SkillTree extends SkillTreeParent {

    public SkillTree() {
        super(MenuType.GAME_MENU);
        Button magicSkilLTreeBTN = createButton(SkillType.MAGIC.getName());
        Button meeleSkillTreeBTN = createButton(SkillType.MEELE.getName());

        magicSkilLTreeBTN.setOnAction(e -> {
            System.out.println("Opening Magic Skill Tree");
        });

        root.getChildren().addAll(magicSkilLTreeBTN, meeleSkillTreeBTN);
    }

}