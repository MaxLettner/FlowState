package at.htl.flowstate.Menu.SkillTrees;

import at.htl.flowstate.Menu.SkillTreeParent;
import at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.MagicSubSkillTrees.ArcaneSkillTree;
import at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.MagicSubSkillTrees.ElementalSkillTree;
import at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.MagicSubSkillTrees.EnchantingSkillTree;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class MagicSkillTree extends SkillTreeParent {
    public MagicSkillTree() {
        super(MenuType.GAME_MENU, "Magic Skill Tree");

        Button arcaneSkillTree = createButton(SkillType.MAGIC_ARCANE.getName());
        Button enchantingSkillTree = createButton(SkillType.MAGIC_ENCHANTING.getName());
        Button elementalSkillTree = createButton(SkillType.MAGIC_ELEMENTAL.getName());

        arcaneSkillTree.setOnAction(event -> {
            getController().gotoPlay();
            FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new ArcaneSkillTree()), Duration.millis(50));
        });
        enchantingSkillTree.setOnAction(event -> {
            getController().gotoPlay();
            FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new EnchantingSkillTree()), Duration.millis(50));
        });
        elementalSkillTree.setOnAction(event -> {
            getController().gotoPlay();
            FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new ElementalSkillTree()), Duration.millis(50));
        });

        

        root.getChildren().addAll(arcaneSkillTree, enchantingSkillTree, elementalSkillTree);

    }
}
