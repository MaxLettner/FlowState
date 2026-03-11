package at.htl.flowstate.Menu;

import at.htl.flowstate.Menu.SkillTrees.MagicSkillTree;
import at.htl.flowstate.Menu.SkillTrees.MeeleSkillTree;
import at.htl.flowstate.Menu.SkillTrees.RangedSkillTree;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class SkillTree extends SkillTreeParent {

    public SkillTree() {
        super(MenuType.GAME_MENU, "Skill Tree");
        Button magicSkilLTreeBTN = createButton(SkillType.MAGIC.getName());
        Button meeleSkillTreeBTN = createButton(SkillType.MEELE.getName());
        Button rangedSkillTreeBTN = createButton(SkillType.RANGED.getName());

        magicSkilLTreeBTN.setOnAction(e -> {
            getController().gotoPlay();
            FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new MagicSkillTree()), Duration.millis(50));
            skillList.unlockSkill(SkillType.MAGIC);
        });
        meeleSkillTreeBTN.setOnAction(e -> {
            getController().gotoPlay();
            FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new MeeleSkillTree()), Duration.millis(50));
            skillList.unlockSkill(SkillType.MEELE);
        });
        rangedSkillTreeBTN.setOnAction(e -> {
            getController().gotoPlay();
            FXGL.runOnce(() -> FXGL.getSceneService().pushSubScene(new RangedSkillTree()), Duration.millis(50));
            skillList.unlockSkill(SkillType.RANGED);
        });

        root.getChildren().addAll(magicSkilLTreeBTN, meeleSkillTreeBTN, rangedSkillTreeBTN);
    }

}