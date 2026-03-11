package at.htl.flowstate.Menu.SkillTrees;

import at.htl.flowstate.Menu.SkillTreeParent;
import at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.MeeleSubSkillTrees.SwordSkillTree;
import at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.MeeleSubSkillTrees.FisticuffSkillTree;
import at.htl.flowstate.Menu.SkillTrees.SubSkillTrees.MeeleSubSkillTrees.BluntSkillTree;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class MeeleSkillTree extends SkillTreeParent {
    public MeeleSkillTree() {
        super(MenuType.GAME_MENU, "Meele Skill Tree");

        Button swordsSkillTree = createButton(SkillType.MEELE_SWORDS.getName());
        Button fisticuffsSkillTree = createButton(SkillType.MEELE_FISTICUFFS.getName());
        Button bluntSkillTree = createButton(SkillType.MEELE_BLUNT.getName());

        swordsSkillTree.setOnAction(event -> {
            getController().gotoPlay();
            FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new SwordSkillTree()), Duration.millis(50));
        });
        fisticuffsSkillTree.setOnAction(event -> {
            getController().gotoPlay();
            FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new FisticuffSkillTree()), Duration.millis(50));
        });
        bluntSkillTree.setOnAction(event -> {
            getController().gotoPlay();
            FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new BluntSkillTree()), Duration.millis(50));
        });

        root.getChildren().addAll(swordsSkillTree, fisticuffsSkillTree, bluntSkillTree);
    }
}
