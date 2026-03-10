package at.htl.flowstate.Menu.SkillTrees;

import at.htl.flowstate.Menu.SkillTreeParent;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.scene.control.Button;

public class MagicSkillTree extends SkillTreeParent {
    public MagicSkillTree() {
        super(MenuType.GAME_MENU);

        Button magicSkilLTreeBTN = createButton("Magic");
        Button meeleSkillTreeBTN = createButton("Meele");

        magicSkilLTreeBTN.setOnAction(e -> {
            System.out.println("Opening Magic Skill Tree");
        });

        root.getChildren().addAll(magicSkilLTreeBTN, meeleSkillTreeBTN);

    }
}
