package at.htl.flowstate.Menu.SkillTree;

import at.htl.flowstate.Skills.Skill;
import at.htl.flowstate.Skills.SkillList;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public abstract class SkillTreeParent {
    protected final int nodeSize = 100;
    protected final VBox root;
    protected SkillList skillList;
    private boolean isOpen = false;

    public SkillTreeParent(String titleText) {
        skillList = SkillList.getInstance();

        root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: rgba(0,0,0,0.85); -fx-padding: 20;");
        root.setPrefWidth(700);

        Text title = new Text(titleText);
        title.setFill(Color.WHITE);
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
        root.getChildren().add(title);
    }

    public void open() {
        isOpen = true;
        FXGL.getGameScene().addUINode(root);
    }

    public void close() {
        isOpen = false;
        FXGL.getGameScene().removeUINode(root);
    }

    public boolean isOpen() {
        return isOpen;
    }

    private static final String STYLE_DEFAULT = "-fx-font-size: 18px; -fx-background-color: #444; -fx-text-fill: white;";
    private static final String STYLE_HOVER = "-fx-font-size: 18px; -fx-background-color: #666; -fx-text-fill: white;";
    private static final String STYLE_UNLOCKED = "-fx-font-size: 18px; -fx-background-color: #008000; -fx-text-fill: white;";
    private static final String STYLE_UNLOCKED_HOVER = "-fx-font-size: 18px; -fx-background-color: #00a000; -fx-text-fill: white;";

    protected Button createStyledButton(String name, SkillType skillType, boolean isSkillButton) {
        Button button = new Button(name);
        button.setPrefWidth(nodeSize * 2);
        button.setPrefHeight(nodeSize / 2);

        boolean unlocked;
        if (isSkillButton) {
            Skill skill = skillList.getSkill(skillType);
            unlocked = skill != null && skill.isUnlocked();
        } else {
            unlocked = skillType != null && skillList.isSkillUnlocked(skillType);
        }

        button.setUserData(new ButtonState(unlocked, name));
        updateButtonStyle(button, unlocked);

        button.setOnMouseEntered(e -> {
            ButtonState state = (ButtonState) button.getUserData();
            button.setStyle(state.isUnlocked ? STYLE_UNLOCKED_HOVER : STYLE_HOVER);
        });

        button.setOnMouseExited(e -> {
            ButtonState state = (ButtonState) button.getUserData();
            updateButtonStyle(button, state.isUnlocked);
        });

        return button;
    }

    protected Button createButton(String name, SkillType skillType) {
        return createStyledButton(name, skillType, false);
    }

    private void updateButtonStyle(Button button, boolean unlocked) {
        button.setStyle(unlocked ? STYLE_UNLOCKED : STYLE_DEFAULT);
    }

    protected static class ButtonState {
        boolean isUnlocked;
        String originalText;

        ButtonState(boolean isUnlocked, String originalText) {
            this.isUnlocked = isUnlocked;
            this.originalText = originalText;
        }
    }
}