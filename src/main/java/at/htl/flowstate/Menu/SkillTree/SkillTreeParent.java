package at.htl.flowstate.Menu.SkillTree;

import at.htl.flowstate.Skills.Skill;
import at.htl.flowstate.Skills.SkillList;
import at.htl.flowstate.Skills.SkillType;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;

import com.almasb.fxgl.input.UserAction;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public abstract class SkillTreeParent extends FXGLMenu {

    protected final int nodeSize = 100;
    protected final VBox root;

    protected SkillList skillList;

    public SkillTreeParent(MenuType menuType, String titleText) {
        super(menuType);

        skillList = SkillList.getInstance();

        // Root container
        root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        // Title
        Text title = new Text(titleText);
        title.setFill(Color.WHITE);
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");
        
        root.getChildren().addAll(title);

        // Add to FXGL menu
        getContentRoot().getChildren().add(root);

        // Optional: simple animation example
        FXGL.animationBuilder()
                .duration(Duration.seconds(1))
                .autoReverse(true)
                .repeatInfinitely()
                .scale(root)
                .buildAndPlay();

        getInput().addAction(new UserAction("Close Skill Tree") {
            @Override
            protected void onActionBegin(){
                closeMenu();
            }
        }, KeyCode.I);
    }

    protected void closeMenu() {
        getController().gotoPlay();
    }




    private static final String STYLE_DEFAULT = "-fx-font-size: 18px; -fx-background-color: #444; -fx-text-fill: white;";
    private static final String STYLE_HOVER = "-fx-font-size: 18px; -fx-background-color: #666; -fx-text-fill: white;";
    private static final String STYLE_UNLOCKED = "-fx-font-size: 18px; -fx-background-color: #008000; -fx-text-fill: white;";
    private static final String STYLE_UNLOCKED_HOVER = "-fx-font-size: 18px; -fx-background-color: #00a000; -fx-text-fill: white;";


    protected Button createStyledButton(String name, SkillType skillType, boolean isSkillButton) {
        Button button = new Button(name);
        button.setPrefWidth(nodeSize * 2);
        button.setPrefHeight(nodeSize / 2);
        button.setStyle("-fx-font-family: Arial; -fx-font-size: 14;");

        boolean unlocked;
        if (isSkillButton) {
            Skill skill = skillList.getSkill(skillType);
            unlocked = skill != null && skill.isUnlocked();
        } else {
            unlocked = skillType != null && skillList.isSkillUnlocked(skillType);
        }

        // Store both the unlock state and original text for persistence
        button.setUserData(new ButtonState(unlocked, name));
        updateButtonStyle(button, unlocked);

        // Hover effect
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

    protected Button createSkillButton(SkillType skillType) {
        Button button = createStyledButton(skillType.getName(), skillType, true);

        button.setOnAction(e -> {
            // Unlock the skill
            skillList.unlockSkill(skillType);
            // Update button state WITHOUT changing text
            ButtonState state = (ButtonState) button.getUserData();
            state.isUnlocked = true;
            updateButtonStyle(button, true);
        });

        return button;
    }

    private void updateButtonStyle(Button button, boolean unlocked) {
        button.setStyle(unlocked ? STYLE_UNLOCKED : STYLE_DEFAULT);
    }

    protected void setUnlocked(Button button) {
        ButtonState state = (ButtonState) button.getUserData();
        if (state != null) {
            state.isUnlocked = true;
        } else {
            button.setUserData(new ButtonState(true, button.getText()));
        }
        updateButtonStyle(button, true);
    }

    /**
     * Inner class to store button state information
     */
    protected static class ButtonState {
        boolean isUnlocked;
        String originalText;

        ButtonState(boolean isUnlocked, String originalText) {
            this.isUnlocked = isUnlocked;
            this.originalText = originalText;
        }
    }

    private void debugOutputSkillList(){
        System.out.println("Current Skill List:");
        for (Skill skill : skillList.getSkills()) {
            System.out.println("- " + skill.getName() + ": " + (skill.isUnlocked() ? "Unlocked" : "Locked"));
        }
    }

    @Override
    public void onCreate() {
        // This is called when the menu is created
    }

    @Override
    public void onUpdate(double tpf) {
        // Called every frame if you want animations or dynamic updates
    }

}