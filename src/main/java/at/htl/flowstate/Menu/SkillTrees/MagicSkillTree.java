package at.htl.flowstate.Menu.SkillTrees;

import at.htl.flowstate.Menu.SkillTreeParent;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.scene.control.Button;

public class MagicSkillTree extends SkillTreeParent {
    public MagicSkillTree() {
        super(MenuType.GAME_MENU);

        Button arcaneSkillTree = createButton("Arcane");
        Button enchantingSkillTree = createButton("Enchanting");
        Button elementalSkillTree = createButton("Elemental");

        

        root.getChildren().addAll(arcaneSkillTree, enchantingSkillTree, elementalSkillTree);

    }
}
