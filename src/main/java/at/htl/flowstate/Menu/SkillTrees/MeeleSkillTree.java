package at.htl.flowstate.Menu.SkillTrees;

import at.htl.flowstate.Menu.SkillTreeParent;
import at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.MeeleSubSkillTrees.OneHandedSkillTree;
import at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.MeeleSubSkillTrees.SpearSkillTree;
import at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.MeeleSubSkillTrees.TwoHandedSkillTree;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class MeeleSkillTree extends SkillTreeParent {
    public MeeleSkillTree() {
        super(MenuType.GAME_MENU, "Meele Skill Tree");

        Button oneHandedSkillTree = createButton(SkillType.MEELE_ONE_HANDED.getName());
        Button twoHandedSkillTree = createButton(SkillType.MEELE_TWO_HANDED.getName());
        Button spearSkillTree = createButton(SkillType.MEELE_SPEAR.getName());

        oneHandedSkillTree.setOnAction(event -> {
            getController().gotoPlay();
            FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new OneHandedSkillTree()), Duration.millis(50));
        });
        twoHandedSkillTree.setOnAction(event -> {
            getController().gotoPlay();
            FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new TwoHandedSkillTree()), Duration.millis(50));
        });
        spearSkillTree.setOnAction(event -> {
            getController().gotoPlay();
            FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new SpearSkillTree()), Duration.millis(50));
        });

        root.getChildren().addAll(oneHandedSkillTree, twoHandedSkillTree, spearSkillTree);
    }
}
