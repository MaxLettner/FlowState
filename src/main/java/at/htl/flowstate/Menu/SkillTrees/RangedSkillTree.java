package at.htl.flowstate.Menu.SkillTrees;

import at.htl.flowstate.Menu.SkillTreeParent;
import at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.RangedSubSkillTrees.BowSkillTree;
import at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.RangedSubSkillTrees.CrossBowSkillTree;
import at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.RangedSubSkillTrees.TridentSkillTree;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class RangedSkillTree extends SkillTreeParent {
    public RangedSkillTree() {
        super(MenuType.GAME_MENU, "Ranged Skill Tree");

        Button bowSkillTree = createButton(SkillType.RANGED_BOW.getName());
        Button crossBowSkillTree = createButton(SkillType.RANGED_CROSSBOW.getName());
        Button tridentSkillTree = createButton(SkillType.RANGED_TRIDENT.getName());

        bowSkillTree.setOnAction(event -> {
            getController().gotoPlay();
            FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new BowSkillTree()), Duration.millis(50));
        });
        crossBowSkillTree.setOnAction(event -> {
            getController().gotoPlay();
            FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new CrossBowSkillTree()), Duration.millis(50));
        });
        tridentSkillTree.setOnAction(event -> {
            getController().gotoPlay();
            FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new TridentSkillTree()), Duration.millis(50));
        });

        root.getChildren().addAll(bowSkillTree, crossBowSkillTree, tridentSkillTree);
    }
}
